-- Passive tree helpers for the lab. Load after harness.lua:  dofile(os.getenv("LAB").."/tools/treelib.lua")
-- T.steiner(spec, ids)        exact cheapest set of nodes that connects the class start to every node in ids
-- T.order(spec, set, order)   turns that set into a point-by-point order that visits the notables in the order given
-- T.apply(spec, ids, n, attr, extra) puts the first n points of an ordered route on the character
--                             (attr: 1 Str, 2 Dex, 3 Int; extra: ascendancy node ids, always allocated)
T = {}
local bit = require("bit")
local band = bit.band

function T.root(spec)
  for _, node in pairs(spec.nodes) do
    if node.type == "ClassStart" and node.alloc then return node end
  end
end

-- Same rules PoB2 uses for pathing: no ascendancy nodes, no other class starts, no nodes locked behind other nodes.
function T.usable(node, root)
  if node == root then return true end
  if node.ascendancyName then return false end
  if node.type == "ClassStart" or node.type == "AscendClassStart" or node.type == "OnlyImage" or node.type == "Mastery" then return false end
  if node.unlockConstraint then return false end
  return true
end

function T.graph(spec)
  local root = T.root(spec)
  local list, index = {}, {}
  for id, node in pairs(spec.nodes) do
    if T.usable(node, root) then list[#list + 1] = node end
  end
  table.sort(list, function(a, b) return a.id < b.id end)
  for i, node in ipairs(list) do index[node.id] = i end
  local adj = {}
  for i, node in ipairs(list) do
    local a = {}
    for _, other in ipairs(node.linked) do
      local j = index[other.id]
      if j and j ~= i then a[#a + 1] = j end
    end
    adj[i] = a
  end
  return { root = root, list = list, index = index, adj = adj }
end

-- Exact node-weighted Steiner tree (Dreyfus-Wagner). Every node costs 1 point, the class start costs 0.
-- bonus(node) in [0,1] only breaks ties between routes of equal point cost (higher bonus preferred).
function T.steiner(spec, ids, bonus)
  local g = T.graph(spec)
  local n, K = #g.list, #ids
  local EPS = 1e-5
  local w = {}
  for i, node in ipairs(g.list) do
    w[i] = (node == g.root) and 0 or (1 - EPS * (bonus and bonus(node) or 0))
  end
  local term = {}
  for t, id in ipairs(ids) do
    term[t] = assert(g.index[id], "node " .. tostring(id) .. " is not reachable on the normal tree")
  end
  local full = 2 ^ K - 1
  local INF = math.huge
  local dp, bp = {}, {}
  for mask = 1, full do
    local d, b = {}, {}
    for v = 1, n do d[v] = INF end
    for t = 1, K do
      if mask == 2 ^ (t - 1) then d[term[t]] = w[term[t]]; b[term[t]] = { "base" } end
    end
    -- merge two smaller trees that meet at v
    local sub = band(mask - 1, mask)
    while sub > 0 do
      local other = mask - sub
      if sub < other then
        local da, db = dp[sub], dp[other]
        for v = 1, n do
          local c = da[v] + db[v] - w[v]
          if c < d[v] - 1e-12 then d[v] = c; b[v] = { "merge", sub, other } end
        end
      end
      sub = band(sub - 1, mask)
    end
    -- grow along edges (Dijkstra with a binary heap)
    local heap, hn = {}, 0
    local function push(c, v)
      hn = hn + 1; heap[hn] = { c, v }
      local i = hn
      while i > 1 do
        local p = math.floor(i / 2)
        if heap[p][1] <= heap[i][1] then break end
        heap[p], heap[i] = heap[i], heap[p]; i = p
      end
    end
    local function pop()
      local top = heap[1]
      heap[1] = heap[hn]; heap[hn] = nil; hn = hn - 1
      local i = 1
      while true do
        local l, r, s = i * 2, i * 2 + 1, i
        if l <= hn and heap[l][1] < heap[s][1] then s = l end
        if r <= hn and heap[r][1] < heap[s][1] then s = r end
        if s == i then break end
        heap[s], heap[i] = heap[i], heap[s]; i = s
      end
      return top[1], top[2]
    end
    for v = 1, n do if d[v] < INF then push(d[v], v) end end
    while hn > 0 do
      local c, v = pop()
      if c <= d[v] + 1e-12 then
        for _, u in ipairs(g.adj[v]) do
          local nc = c + w[u]
          if nc < d[u] - 1e-12 then d[u] = nc; b[u] = { "grow", v }; push(nc, u) end
        end
      end
    end
    dp[mask], bp[mask] = d, b
  end
  local rootIdx = g.index[g.root.id]
  if dp[full][rootIdx] == INF then return nil, INF, INF end -- some node cannot be reached at all
  local set = {}
  local function collect(mask, v)
    set[g.list[v].id] = true
    local b = bp[mask][v]
    if b[1] == "grow" then collect(mask, b[2])
    elseif b[1] == "merge" then collect(b[2], v); collect(b[3], v) end
  end
  collect(full, rootIdx)
  local count = 0
  for id in pairs(set) do if id ~= g.root.id then count = count + 1 end end
  return set, count, dp[full][rootIdx]
end

-- Walk the chosen set of nodes, taking the notables in the order given. Returns the node ids point by point.
function T.order(spec, set, order)
  local root = T.root(spec)
  local have = { [root.id] = true }
  local out = {}
  local function take(targetId)
    if have[targetId] then return end
    -- breadth-first search inside the set, from everything already taken
    local prev, queue, qi = {}, {}, 1
    for id in pairs(have) do queue[#queue + 1] = id; prev[id] = false end
    table.sort(queue)
    while queue[qi] do
      local id = queue[qi]; qi = qi + 1
      if id == targetId then break end
      local linked = {}
      for _, other in ipairs(spec.nodes[id].linked) do linked[#linked + 1] = other.id end
      table.sort(linked)
      for _, oid in ipairs(linked) do
        if set[oid] and prev[oid] == nil then prev[oid] = id; queue[#queue + 1] = oid end
      end
    end
    assert(prev[targetId] ~= nil, "cannot reach node " .. targetId .. " inside the chosen set")
    local path, cur = {}, targetId
    while cur and not have[cur] do table.insert(path, 1, cur); cur = prev[cur] end
    for _, id in ipairs(path) do have[id] = true; out[#out + 1] = id end
  end
  for _, id in ipairs(order) do take(id) end
  local rest = {}
  for id in pairs(set) do if not have[id] then rest[#rest + 1] = id end end
  table.sort(rest)
  for _, id in ipairs(rest) do take(id) end
  return out
end

-- Put the first n points of an ordered route on the character. attr: 1 Strength, 2 Dexterity, 3 Intelligence,
-- or a function(nodeId, position) returning 1/2/3 for each attribute node.
function T.apply(spec, ids, n, attr, extra)
  spec:ResetNodes()
  for id in pairs(spec.hashOverrides) do spec.hashOverrides[id] = nil end
  n = math.min(n or #ids, #ids)
  for i = 1, n do
    local node = spec.nodes[ids[i]]
    node.alloc = true; node.allocMode = 0
    spec.allocNodes[node.id] = node
  end
  for _, id in ipairs(extra or {}) do -- ascendancy nodes: always on, they do not cost passive points
    local node = spec.nodes[id]
    node.alloc = true; node.allocMode = 0
    spec.allocNodes[node.id] = node
  end
  for i = 1, n do
    local node = spec.nodes[ids[i]]
    if node.isAttribute then
      local a = type(attr) == "function" and attr(node.id, i) or attr or 3
      spec:SwitchAttributeNode(node.id, a)
    end
  end
  spec:BuildAllDependsAndPaths()
  H.recalc()
  return n
end

function T.describe(spec, id)
  local node = spec.nodes[id]
  return string.format("%s [%d] %s", node.dn, id, table.concat(node.sd or {}, " / "))
end

#!/usr/bin/env bash
# Build the Path of Building 2 lab in this folder (Linux or WSL). Safe to run again: it skips what is already there.
# Needs: git, gcc, make, and access to github.com. Takes about 2 minutes and 850 MB.
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")"

# 1. Path of Building 2 (the calculation engine)
[ -d pob2 ] || git clone --depth 1 https://github.com/PathOfBuildingCommunity/PathOfBuilding-PoE2 pob2

# 2. LuaJIT at the commit PoB2 pins in its Dockerfile (newer LuaJIT rejects PoB2's "#@" first lines)
PIN="$(grep -o 'git checkout [0-9a-f]\{40\}' pob2/Dockerfile | awk '{print $3}' | head -1 || true)"
PIN="${PIN:-2460b3ff93a1c955de3d62cfc825de7d68dc272e}"
if [ ! -x luajit/src/luajit ]; then
  [ -d luajit ] || git clone https://github.com/LuaJIT/LuaJIT luajit
  (cd luajit && git checkout -q "$PIN" && make -j4 >/dev/null)
fi

# 3. The UTF-8 library PoB2 needs
[ -d luautf8 ] || git clone --depth 1 https://github.com/starwing/luautf8
mkdir -p lib
[ -f lib/lua-utf8.so ] || gcc -O2 -fPIC -shared -I luajit/src luautf8/lutf8lib.c -o lib/lua-utf8.so

# 4. Smoke test
echo "PoB2 commit: $(git -C pob2 log -1 --format='%h %cs')   LuaJIT: $PIN"
./run.sh tests/smoke.lua

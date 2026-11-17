#!/usr/bin/env bash
# Run a Lua script inside the headless Path of Building 2 engine.
# Usage: ./run.sh tests/smoke.lua [args...]
set -euo pipefail
LAB="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SCRIPT="$(realpath "$1")"; shift
export LAB
cd "$LAB/pob2/src"
LUA_PATH="../runtime/lua/?.lua;../runtime/lua/?/init.lua;;" \
LUA_CPATH="$LAB/lib/?.so;;" \
exec "$LAB/luajit/src/luajit" "$SCRIPT" "$@"

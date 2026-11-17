#!/usr/bin/env python3
"""Fetch a poe2db.tw page (or any URL) and print it as plain text.

Usage:
  python3 -I tools/poe2db.py Minion_Instability          # -> https://poe2db.tw/us/Minion_Instability
  python3 -I tools/poe2db.py https://example.com/page    # any URL
  python3 -I tools/poe2db.py Comet --find "Cast Time" --around 300   # only text near a phrase

The raw HTML is cached under .cache/web/ so repeat reads are free. Add --fresh to re-download.
"""
import argparse, hashlib, html, os, re, subprocess, sys

UA = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0 Safari/537.36"
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
CACHE = os.path.join(ROOT, ".cache", "web")


def fetch(url, fresh=False):
    os.makedirs(CACHE, exist_ok=True)
    path = os.path.join(CACHE, hashlib.sha1(url.encode()).hexdigest() + ".html")
    if fresh or not os.path.exists(path) or os.path.getsize(path) == 0:
        r = subprocess.run(["curl", "-sS", "-L", "--max-time", "60", "-A", UA, "-o", path, "-w", "%{http_code}", url],
                           capture_output=True, text=True)
        code = r.stdout.strip()
        if code != "200":
            sys.stderr.write(f"HTTP {code} for {url} {r.stderr.strip()}\n")
    with open(path, encoding="utf8", errors="ignore") as f:
        return f.read()


def to_text(raw):
    t = re.sub(r"<script.*?</script>|<style.*?</style>", "", raw, flags=re.S)
    t = re.sub(r"<(br|/tr|/p|/div|/li|/h\d)[^>]*>", "\n", t)
    t = re.sub(r"<[^>]+>", " ", t)
    t = html.unescape(t)
    t = re.sub(r"[ \t\r\f\v]+", " ", t)
    t = re.sub(r"\n\s*\n+", "\n", t)
    return t.strip()


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("page")
    ap.add_argument("--find", help="only print text around this phrase (case-insensitive)")
    ap.add_argument("--around", type=int, default=400)
    ap.add_argument("--fresh", action="store_true")
    ap.add_argument("--max", type=int, default=0, help="print at most this many characters")
    a = ap.parse_args()
    url = a.page if a.page.startswith("http") else "https://poe2db.tw/us/" + a.page
    text = to_text(fetch(url, a.fresh))
    if a.find:
        out = []
        for m in re.finditer(re.escape(a.find), text, flags=re.I):
            out.append(text[max(0, m.start() - a.around): m.end() + a.around])
        text = "\n-----\n".join(out) if out else f"(phrase not found: {a.find})"
    if a.max:
        text = text[: a.max]
    print(url)
    print(text)


if __name__ == "__main__":
    main()

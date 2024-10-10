import sys

from itertools import permutations

nums = []

for line in sys.stdin:
    nums.append(line.strip())

max_n = -1
max_s = ''

for p in permutations(nums):
    s = ''.join(p)
    n = int(s)

    if n > max_n:
        max_n = n
        max_s = s

print(max_s)

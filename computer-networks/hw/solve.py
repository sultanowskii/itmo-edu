N = 7

nums = [0] * N + [int(c) for c in '110100011100000011010000']

for i in range(N, len(nums)):
    nums[i] = nums[i] ^ nums[i - (N - 2)] ^ nums[i - N]

for i in range(N, len(nums)):
    print(nums[i], end='')

print()

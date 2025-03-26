def f(reg: list[int]) -> tuple[list[int], int]:
    n = len(reg)
    result_value = reg[n - 1]

    func_result = reg[n - 6] ^ reg[n - 5] ^ reg[n - 2]

    return [func_result] + reg[0:n-1], result_value


INITIAL_REG = [0,1,0,1,1,1]
reg = INITIAL_REG.copy()

period = 0
output = []

print(reg)

while True:
    reg, v = f(reg)
    period += 1
    output.append(v)

    print(reg, v)

    if reg == INITIAL_REG:
        break

print('period:', period)
print('output:', ','.join(reversed([str(n) for n in output])))

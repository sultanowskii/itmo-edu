def rounded(n):
    return round(n, 6)


def read_data(filename):
    with open(filename, 'r') as f:
        data = f.read().strip().replace(',', '.').split('\n')

    rows = len(data)
    columns = data[0].count(' ') + 1

    data = [float(n) for line in data for n in line.split(' ')]

    return data, rows, columns


def write_data(filename, data, rows, columns):
    with open(filename, 'w') as f:
        s = ''
        for i in range(rows):
            line = data[i * columns:(i + 1) * columns]
            s += ' '.join([f'{n:.3f}' for n in line]).replace('.', ',') + '\n'
        f.write(s.strip())

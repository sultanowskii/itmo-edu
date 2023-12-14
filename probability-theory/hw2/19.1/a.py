from common import *

data, rows, columns = read_data('input.csv')

print('Input:', data)

sorted_data = sorted(data)

print('Sorted:', sorted_data)

with open('a.csv', 'w') as f:
    s = ''
    for i in range(rows):
        line = sorted_data[i * columns:(i + 1) * columns]
        s += ' '.join([f'{n:.3f}' for n in line]).replace('.', ',') + '\n'
    f.write(s.strip())
            
write_data('a.csv', sorted_data, rows, columns)
#include <iostream>

using namespace std;

int main() {
  int n;
  cin >> n;

  // Если растений меньше 3, то условие всегда выполняется
  // И весь участок будет искомым
  if (n < 3) {
    cout << 1 << ' ' << n << endl;
    return 0;
  }

  int preprev;
  int prev;
  int current;

  int start_of_longest = 0;
  int end_of_longest = 1;

  int start = 0;

  cin >> preprev >> prev;

  // Начинаем i = 2, т.к. мы уже считали 0-ой и 1-ый.
  for (int i = 2; i < n; i++) {
    cin >> current;

    bool is_last = i == n - 1;

    if (current == prev && prev == preprev) {
      // Используем предыдущий, т.к. если считать с текущим, то диапазон будет
      // невалидным.
      int prev_i = i - 1;

      // +1 необходим для правильного подсчета кол-ва элементов в диапазоне,
      // т.к. конец включительно.
      int current_length = (prev_i - start) + 1;
      int longest = (end_of_longest - start_of_longest) + 1;

      if (current_length > longest) {
        start_of_longest = start;
        end_of_longest = prev_i;
      }

      // Начало рассматриваемого отрезка сдвигаем к i - 1 (предыдущему)
      // элементу. Обоснование:
      // - (Если сдвинуть в <= i-2): Текущий отрезок все равно упрется в
      // совпадающую тройку (сюда),
      //   при том будет явно короче, чем текущий
      // - (Если сдвинуть в >=i): Возможен случай, когда следующий (i + 1)
      // элемент уже не будет равен текущему (i),
      //   в таком случае i - 1 и i могут спокойно войти в этот отрезок.
      //   Пример-пояснение:
      //   2 0 4 4 4 2 8 7
      //           i
      //   Начинаем с i-1 (второй четверки),
      //   И действительно, i-1 и i в конечном итоге вошли
      //   в наибольший отрезок:
      //   2 0 4[4 4 2 8 7]
      //   Нельзя не рассмотреть этот случай.
      start = prev_i;
    } else if (is_last) {
      // В конце:
      // - если последние 3 одинаковы, то это рассматривается в ветке выше
      // - если последние 3 не одинаковы, необходимо рассмотреть диапазон
      // [start; i] включительно (иначе мы упустим этот случай)

      // +1 необходим для правильного подсчета кол-ва элементов в диапазоне,
      // т.к. конец включительно.
      int current_length = (i - start) + 1;
      int longest = (end_of_longest - start_of_longest) + 1;

      if (current_length > longest) {
        start_of_longest = start;
        end_of_longest = i;
      }
    }

    preprev = prev;
    prev = current;
  }

  // Выводим с +1, т.к. по условию нумерация начинается с 1.
  cout << start_of_longest + 1 << ' ' << end_of_longest + 1 << endl;

  return 0;
}

#include <algorithm>
#include <iostream>
#include <set>
#include <vector>

using namespace std;

int main() {
  int n, k;

  cin >> n >> k;

  // Альтернативно можно использовать map, но с multiset
  // удобнее получать минимальный элемент.
  multiset<int> window;
  vector<int> numbers(n, 0);

  for (int i = 0; i < n; i++) {
    int tmp;
    cin >> tmp;
    numbers[i] = tmp;
  }

  // Заполняем изначальное окно
  for (int i = 0; i < k; i++) {
    window.insert(numbers[i]);
  }

  // Как минимум один раз мы выведем.
  cout << *window.begin() << ' ';

  for (int i = 0; i < n - k; i++) {
    int index_to_remove = i;
    int index_to_add = k + i;

    int to_remove = numbers[index_to_remove];
    int to_add = numbers[index_to_add];

    // Обновляем окно: удаляем элемент из начала окна (буквально самый младший индекс)
    // и добавляем элемент сразу после окна
    // Длина остается той же
    window.erase(window.find(to_remove));
    window.insert(to_add);

    // В multiset элементы отсортированны - можно удобно брать минимальный.
    cout << *window.begin() << ' ';
  }

  return 0;
}

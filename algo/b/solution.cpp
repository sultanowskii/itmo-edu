#include <iostream>
#include <stack>
#include <vector>

using namespace std;

bool is_animal(char c) { return c >= 'a'; }

int main() {
  // Стек пар ТИП+ИНДЕКС
  // Ожидают ловушку/зверя
  stack<pair<char, int>> q;

  string s;
  cin >> s;

  int n = s.length();

  int animal_cntr = 0;
  int trap_cntr = 0;

  // Индекс - индекс ловушки
  // Значеие - индекс животного
  vector<int> animal_in_traps_indexes(n / 2, -1);

  for (int i = 0; i < n; i++) {
    char c = s[i];

    int current_index = -1;
    if (is_animal(c)) {
      current_index = animal_cntr++;
    } else {
      current_index = trap_cntr++;
    }

    if (q.empty()) {
      q.push({c, current_index});
      continue;
    }

    auto top = q.top();
    char top_c = top.first;
    int top_number = top.second;

    // Проверка, что один - ловушка, другой - зверь
    if ((top_c != c) && (tolower(c) == tolower(top_c))) {
      // Обновляем массив индексов пойманных зверей
      if (is_animal(c)) {
        animal_in_traps_indexes[top_number] = current_index;
      } else if (is_animal(top_c)) {
        animal_in_traps_indexes[current_index] = top_number;
      }
      q.pop();
    } else {
      // В противном случае добавляем еще одну "ожидающую" клетку
      q.push({c, current_index});
    }
  }

  if (q.empty()) {
    cout << "Possible" << endl;
    for (int i = 0; i < n / 2; i++) {
      int animal_number = animal_in_traps_indexes[i];
      cout << animal_number + 1 << ' ';
    }
    cout << endl;
  } else {
    cout << "Impossible" << endl;
  }

  return 0;
}

#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>

using namespace std;

bool compare_letters(pair<char, int> a, pair<char, int> b) {
  return a.second > b.second;
}

int main() {
  // Веса букв
  vector<int> weighs(26, -1);
  // Кол-во букв в строке
  vector<int> counts(26, 0);
  // Буквы с парой (максимум - 26). Длину обозначим за q
  vector<pair<char, int>> paired_letters;
  // Все остальные буквы (символы), у которых нет пары или пара уже учтена.
  // Длина массива равна n - q * 2
  vector<char> remaining_letters;

  string s;
  cin >> s;
  int n = s.size();

  for (char c : s) {
    counts[c - 'a']++;
  }

  int weigh;
  int i = 0;
  while (cin >> weigh) {
    weighs[i++] = weigh;
  }

  // Заполнение списка букв с парами и оставшихся
  for (char c = 'a'; c <= 'z'; c++) {
    int idx = c - 'a';
    int count = counts[idx];
    // Если буква не одинока, то она идет в пары.
    // Значит, в "остальные" пойдут count-2 букв
    if (count >= 2) {
      paired_letters.push_back({c, weighs[idx]});
      count -= 2;
    }
    // Добавляем в "остальные" count-2 букв этого типа.
    for (int j = 0; j < count; j++) {
      remaining_letters.push_back(c);
    }
  }

  // Достигаем того, чтобы буквы с наибольшим весом имели наибольшее расстояние.
  sort(paired_letters.begin(), paired_letters.end(), compare_letters);

  // Идем с двух сторон, заполняя строку парами
  i = 0;
  int l = 0;
  int r = n - 1;
  bool left_turn = true;
  while (i < paired_letters.size()) {
    s[l++] = paired_letters[i].first;
    s[r--] = paired_letters[i].first;
    i++;
  }

  // Заполняем оставшееся пространство "оставшимися" буквами.
  i = 0;
  while (l <= r) {
    s[l++] = remaining_letters[i++];
  }

  cout << s;

  return 0;
}

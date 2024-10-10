#include <algorithm>
#include <iostream>
#include <set>
#include <vector>
#include <unordered_map>

using namespace std;

enum Affiliation {
  Nobody = 0,
  First,
  Second,
};

vector<vector<int>> graph;
vector<Affiliation> colors;

// Возвращает противопложный цвет.
inline Affiliation flipped(Affiliation a) {
  return a == Affiliation::First ? Affiliation::Second : Affiliation::First;
}

bool traverse(int n, Affiliation a) {
  colors[n] = a;

  // покрасим (или ожидаем) соседей в противоположный цвет
  auto expected_neighbour_color = flipped(a);

  // проходимся по всем соседям
  for (auto neighbour : graph[n]) {
    if (colors[neighbour] == Affiliation::Nobody) {
      // если сосед еще никому не принадлежит, то запишем его в другую от текущей группу
      if (!traverse(neighbour, expected_neighbour_color)) {
        // если "в глубине" было найдено противорчие, то вся компонента является невалидной.
        return false;
      }
    } else if (colors[neighbour] != expected_neighbour_color) {
      // Если сосед находится в той же группе, что и мы, то получилось замыкание -
      // а значит, разделиться на 2 команды по условию невозможно
      return false;
    }
  }

  return true;
}

int main() {
  int n, m;
  cin >> n >> m;

  colors = vector<Affiliation>(n, Affiliation::Nobody);

  graph = vector<vector<int>>(n);

  for (int i = 0; i < m; i++) {
    int a, b;

    cin >> a >> b;
    a--;
    b--;

    graph[a].push_back(b);
    graph[b].push_back(a);
  }

  bool valid = true;

  for (int i = 0; i < n; i++) {
    // Проходимся по всем компонентам связанности
    if (colors[i] == Affiliation::Nobody) {
      // Если хотя бы одна из компонент не является валидной, то невалиден весь граф (по условию)
      if (!traverse(i, Affiliation::First)) {
        valid = false;
        break;
      }
    }
  }

  if (valid) {
    cout << "YES" << endl;
  } else {
    cout << "NO" << endl;
  }

  return 0;
}

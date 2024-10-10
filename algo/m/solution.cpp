#include <algorithm>
#include <iostream>
#include <set>
#include <vector>
#include <unordered_map>

#define INF 1000000000

using namespace std;

int get_cost(char c) {
  if (c == '.') {
    return 1;
  } else if (c == 'W') {
    return 2;
  }
  return INF;
}

int main() {
  int n, m;

  cin >> n >> m;

  int start_x, start_y;
  cin >> start_y >> start_x;
  start_x--;
  start_y--;

  int end_x, end_y;
  cin >> end_y >> end_x;
  end_x--;
  end_y--;

  // поле
  string field[n];
  // расстояния от конечной точки до клетки
  vector<vector<int>> path_lengths(n, vector<int>(m, INF - 1));
  char source_directions[n][m];

  // идем от конца к началу, так будет проще собирать путь
  source_directions[end_y][end_x] = '.';
  path_lengths[end_y][end_x] = 0;

  for (int i = 0; i < n; i++) {
    cin >> field[i];
  }

  // для удобного быстрого доступа к наименьшей клетке (при выборе в начале итерации)
  set<pair<int, pair<int, int>>> to_visit;
  to_visit.insert({0, {end_y, end_x}});

  // направления движения
  pair<char, pair<int, int>> ways[4] = {
    {'S', {-1, 0}},
    {'N', {+1, 0}},
    {'E', {0, -1}},
    {'W', {0, +1}},
  };

  // пока есть, откуда начинать
  while (!to_visit.empty()) {
    auto c = *to_visit.begin();
    auto coords = c.second;
    to_visit.erase(to_visit.begin());

    int y = coords.first;
    int x = coords.second;

    // проверка на то, в какие стороны мы можем пойти
    bool can_go[4] = {
      (y > 0 && field[y - 1][x] != '#'),
      (y < n - 1 && field[y + 1][x] != '#'),
      (x > 0 && field[y][x - 1] != '#'),
      (x < m - 1 && field[y][x + 1] != '#')
    };

    for (int i = 0; i < 4; i++) {
      if (!can_go[i]) {
        continue;
      }
      int to_y = y + ways[i].second.first;
      int to_x = x + ways[i].second.second;

      int current_value = path_lengths[to_y][to_x];
      int offer = path_lengths[y][x] + get_cost(field[to_y][to_x]);

      // если "новый" путь до рассматриваемой клетки короче текущего, то выбираем его
      // не забываем обновить букву-направление, расстояние до вершины
      // и обновить "очередь" клеток для посещения.
      if (offer < current_value) {
        source_directions[to_y][to_x] = ways[i].first;
        path_lengths[to_y][to_x] = offer;
        to_visit.erase({current_value, {to_y, to_x}});
        to_visit.insert({offer, {to_y, to_x}});
      }
    }
  }

  // если значение равно изначальному, то мы не смогли дойти
  if (path_lengths[start_y][start_x] == INF - 1) {
    cout << -1 << endl;
    return 0;
  }

  cout << path_lengths[start_y][start_x] << endl;


  // идем от начала к концу, используя буквы в клетках для навигации.
  int y = start_y;
  int x = start_x;
  char current = '/';
  while (current != '.') {
    current = source_directions[y][x];
    if (current == '.') {
      break;
    }
    cout << current;
    if (current == 'S') {
      y++;
    } else if (current == 'N') {
      y--;
    } else if (current == 'E') {
      x++;
    } else if (current == 'W') {
      x--;
    }
  }
  cout << endl;

  return 0;
}

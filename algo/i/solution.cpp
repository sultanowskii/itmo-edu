#include <algorithm>
#include <iostream>
#include <unordered_set>
#include <vector>
#include <set>
#include <unordered_map>
#include <queue>

using namespace std;

int main() {
  int n, k, p;
  cin >> n >> k >> p;

  // Множество машинок на полу
  unordered_set<int> floor;
  // Список запросов машинок
  vector<int> requests(p, 0);
  // Для каждой машинки храним индексы запросов, в которых она потребуется (по возрастанию)
  unordered_map<int, set<int>> cars_request_indexes;
  // Список машинок для удаления (<factor, car>)
  // Далее в алгоритме идея такова:
  // Сначала удаляем те машинки, что более не будут использоваться
  // После удаляем те, что стоят дальше от текущей
  // Таким образом нужно поддерживать список машинок для удаления по убыванию индекса
  // Для более "не нужных" машинок выставляем индекс в очень большое число
  priority_queue<pair<int, int>> cars_to_be_removed;

  for (int i = 0; i < p; i++) {
    int car;
    cin >> car;
    requests[i] = car;
    cars_request_indexes[car].insert(i);
  }

  int actions = 0;

  for (int i = 0; i < p; i++) {
    int car = requests[i];
    // Для текущей машинки удаляем текущий индекс из ее списка индексов
    cars_request_indexes[car].erase(cars_request_indexes[car].begin());

    // Если машинка уже есть на полу, то пропускаем это дело
    if (floor.find(car) == floor.end()) {
      // Если пол заполнен машинками не полностью, то нет смысла что-либо убирать
      if (floor.size() == k) {
        // Машинка для удаления берется из очереди
        // Это будет машинка, которая либо более не потребуется вообще,
        // либо потребуется максимально позже - таким образом
        // придется сделать минимум перестановок
        int car_to_be_removed = cars_to_be_removed.top().second;

        // Убираем машинку с пола и очереди
        floor.erase(car_to_be_removed);
        cars_to_be_removed.pop();
      }

      // Добавляем текущую машинку на пол
      floor.insert(car);

      // Действие совершено - мы положили машинку на пол и (возможно) убрали другую
      actions++;
    }

    // Если машинка более не потребуется (более нет индексов запроса), то при необходимости
    // ее стоит убрать с пола в первую очередь.
    // В противном случае опираемся на следующий индекс машинки.
    int factor = 1e9;
    if (!cars_request_indexes[car].empty()) {
      factor = *cars_request_indexes[car].begin();
    }
    cars_to_be_removed.push({factor, car});
  }

  cout << actions;

  return 0;
}

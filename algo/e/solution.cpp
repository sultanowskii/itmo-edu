#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

// Проверка на то, что все коровы "влезут", если мы захотим расставить
// их с минимальным расстоянием `min_distance`
bool is_min_distance_valid(vector<int> &fields, int min_distance,
                           int cow_count) {
  int last_field_with_cow = fields[0];
  int occupied_field_count = 1;

  for (int i = 1; i < fields.size(); i++) {
    int current_field = fields[i];
    if (current_field - last_field_with_cow >= min_distance) {
      last_field_with_cow = current_field;
      occupied_field_count++;
    }
  }

  return occupied_field_count >= cow_count;
}

int main() {
  int n, k;
  cin >> n >> k;

  vector<int> fields(n);

  for (int i = 0; i < n; i++) {
    cin >> fields[i];
  }

  int l = 1;
  int r = fields[n - 1];

  // Бинпоиск по минимальному расстоянию (максимизируем)
  while (l < r - 1) {
    int mid = (l + r) / 2;

    if (is_min_distance_valid(fields, mid, k)) {
      l = mid;
    } else {
      r = mid;
    }
  }

  // При поиске arr[mid] <= t, (последний) ответ лежит в left
  cout << l;

  return 0;
}

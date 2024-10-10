#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>

using namespace std;

int main() {
  int n, k;

  cin >> n >> k;

  vector<int> prices(n);

  for (int i = 0; i < n; i++) {
    cin >> prices[i];
  }

  // Для удобства сортируем по убыванию
  sort(prices.begin(), prices.end(), greater<int>());

  int sum = 0;

  for (int i = 0; i < n; i++) {
    int no = i + 1;
    // Т.к. массив отсортирован по убыванию, выгодно "не брать"
    // каждый k-ый элемент - так как он наибольший. Почему не другие:
    // - Брать "левее" - невыгодно, т.к. мы берем меньше k элементов, тем
    //   самым не получаем скидку в этом чеке
    // - Брать "правее" - в таком случае получим бесплатно предмет с меньшей
    //   ценой.
    if (no % k == 0) {
      continue;
    }
    sum += prices[i];
  }

  cout << sum;

  return 0;
}

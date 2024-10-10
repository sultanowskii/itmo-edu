#include <iostream>
#include <vector>

using namespace std;

#define ull unsigned long long

int a, b, c, d;
ull k;

inline int calc_w(int prev_w) { return min(d, max(0, prev_w * b - c)); }

int main() {
  cin >> a >> b >> c >> d >> k;

  int prev_w = a;
  int w = calc_w(prev_w);

  // Идем до k, так как после идти смысла нет. До k явно не дойдем,
  // т.к. гораздо раньше случится одно из двух событий:
  // 1. Бактерий станет 0 (после все будут равны 0)
  // 2. Число бактерий в конце дней будет оставаться одним и тем же.
  //    Обусловлено тем, что b>=1 всегда дает неубывание числа бактерий,
  //    а d - ограничивает число "сохраняющихся"
  for (int i = 0; i < k; i++) {
    w = calc_w(prev_w);

    // Если бактерии закончились, то все значения
    // после будут равны 0.
    if (w == 0) {
      w = 0;
      break;
    }

    // Если f(x) == x, то все значения после
    // тоже будут равны x.
    if (prev_w == w) {
      break;
    }

    prev_w = w;
  }

  cout << w;
  return 0;
}
#include <algorithm>
#include <iostream>
#include <queue>

using namespace std;

int main() {
  int n;
  cin >> n;

  // Идея в том, чтобы разделять очередь напополам, тем самым
  // всегда иметь доступ к середине без необходимости сдвига
  // части массива/очереди.
  // При нечетности длины приоритет отдается левой половине по условию
  deque<int> left, right;

  for (int i = 0; i < n; i++) {
    char op;
    int num;
    cin >> op;

    switch (op) {
      case '-':
        // Берем из начала левой половины = начала очереди
        cout << left.front() << '\n';
        left.pop_front();
        break;
      case '+':
        // Кладем в конец правой половины = конец очереди
        cin >> num;
        right.push_back(num);
        break;
      case '*':
        // Кладем в начало правой половины = середина очереди
        // (случай нечетности обрабатыватся)
        cin >> num;
        right.push_front(num);
        break;
    }

    // Нормализация половин: неоходимо привести их к равному состоянию.
    // при том в случае нечетности длины очереди приоритет отдается
    // левой половине - чтобы в начале правой половины находилась середина
    // по условию
    while (left.size() < right.size()) {
      left.push_back(right.front());
      right.pop_front();
    }
  }

  return 0;
}

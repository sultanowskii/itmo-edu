#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

// Сравнить как числа мы не можем из-за размера
// Но зато можем сравнить их конкатенации лексикографически
// Работает, т.к. сумма строк в обоих порядках имеет одинаковую длину
// Таким образом можно легко определить, в каком порядке должны
// идти 2 числа, чтобы получилось максимальное.
bool comp(string &s1, string &s2) {
  string first_second = s1 + s2;
  string second_first = s2 + s1;

  return first_second > second_first;
}

int main() {
  vector<string> nums;

  string line;
  while (cin >> line) {
    nums.push_back(line);
  }

  sort(nums.begin(), nums.end(), comp);

  for (auto &num : nums) {
    cout << num;
  }
  cout << '\n';

  return 0;
}

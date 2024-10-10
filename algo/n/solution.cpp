#include <algorithm>
#include <iostream>
#include <set>
#include <vector>
#include <unordered_map>

using namespace std;

vector<vector<int>> graph;
vector<bool> used;

void traverse(int n) {
  used[n] = true;

  for (auto next : graph[n]) {
    if (!used[next]) {
      traverse(next);
    }
  }
}

int main() {
  int n;
  cin >> n;

  used = vector<bool>(n, false);

  graph = vector<vector<int>>(n);

  for (int i = 0; i < n; i++) {
    int src, dst;

    cin >> src;
    src--;
    dst = i;

    graph[src].push_back(dst);
    graph[dst].push_back(src);
  }

  int cntr = 0;

  for (int i = 0; i < n; i++) {
    if (!used[i]) {
      traverse(i);
      cntr++;
    }
  }

  cout << cntr;

  return 0;
}

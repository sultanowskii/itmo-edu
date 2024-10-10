#include <iostream>
#include <stack>
#include <unordered_map>
#include <unordered_set>
#include <vector>

using namespace std;

bool is_number(string s) {
  bool is_negative = s[0] == '-';

  int start_index = is_negative ? 1 : 0;

  for (int i = start_index; i < s.length(); i++) {
    if (!isdigit(s[i])) {
      return false;
    }
  }
  return true;
}

int main() {
  // История значений переменных
  unordered_map<string, stack<int>> variables;
  // Области определений переменных на стеке - чтобы знать,
  // какие переменные "подтирать" при выходе из области
  stack<unordered_set<string>> define_scopes;

  define_scopes.push({});

  string line;
  while (cin >> line) {
    if (line == "{") {
      // Погружаемся в новую область
      define_scopes.push({});
    } else if (line == "}") {
      // При выходе из области подтираем из истории значения
      // Переменных, определенных в этой области
      // + Удаляем саму область
      //
      // Если переменная была впервые определена в этой области,
      // Полностью удаляем ее из истории
      for (string var : define_scopes.top()) {
        variables[var].pop();
        if (variables[var].empty()) {
          variables.erase(variables.find(var));
        }
      }
      define_scopes.pop();
    } else {
      // Распил на токены
      int sep_index = line.find("=");
      string var1 = line.substr(0, sep_index);
      string source = line.substr(sep_index + 1);

      // Если переменная новая, то создаем для нее "историю"
      if (variables.find(var1) == variables.end()) {
        variables[var1] = {};
      }

      auto &define_scope = define_scopes.top();
      // Если в этой области переменная определяется впервые, ...
      if (define_scope.find(var1) == define_scope.end()) {
        // ... то нужно уточнить, что она была здесь определена
        define_scope.insert(var1);
        int current_var1_value = 0;
        // И временно дать ей значение из области видимости повыше, если таковой
        // есть (для случая var1=var1) Иначе - просто 0.
        if (!variables[var1].empty()) {
          current_var1_value = variables[var1].top();
        }
        variables[var1].push(current_var1_value);
      }

      int value = 0;
      if (is_number(source)) {
        // Приравнивание константе выполняется тривиально
        value = stoi(source);
      } else {
        // Если переменная-источник определена, то используем ее актуальное
        // значение Если нет - 0 (см.выше)
        if (variables.find(source) != variables.end()) {
          value = variables[source].top();
        }
        cout << value << endl;
      }

      variables[var1].pop();
      variables[var1].push(value);
    }
  }

  return 0;
}

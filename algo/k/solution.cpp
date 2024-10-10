#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct chunk {
  size_t    start;
  size_t    size;
  bool      occupied = false;
  chunk*    prev = NULL;
  chunk*    next = NULL;
};

void split_chunk_down_to_request(chunk *c, int request) {
  if (c->size <= request) {
    return;
  }

  chunk *new_chunk = new chunk;
  new_chunk->start = c->start + request;
  new_chunk->size = c->size - request;
  new_chunk->occupied = false;
  new_chunk->prev = c;
  new_chunk->next = c->next;

  if (c->next != NULL) {
    c->next->prev = new_chunk;
  }

  c->next = new_chunk;
  c->size = request;
}

chunk *find_fitting_chunk(chunk *head, int request) {
  auto c = head;
  while (c != NULL) {
    if (c->size >= request && !c->occupied) {
      split_chunk_down_to_request(c, request);
      return c;
    }
    c = c->next;
  }

  return NULL;
}

bool chunks_go_after_each_other(chunk *a, chunk *b) {
  return a->start + a->size == b->start;
}

bool chunks_consolidateable(chunk *a, chunk *b) {
  return !a->occupied && !b->occupied && chunks_go_after_each_other(a, b);
}

bool try_consolidate_chunks(chunk *c) {
  if (c == NULL || c->next == NULL) {
    return false;
  }

  auto next = c->next;
  if (!chunks_consolidateable(c, next)) {
    return false;
  }

  c->size += next->size;
  c->next = next->next;

  return true;
}


void occupy_chunk(chunk *c) {
  c->occupied = true;
}

void free_chunk(chunk *c) {
  c->occupied = false;
}

int main() {
  int n, m;
  cin >> n >> m;

  vector<chunk *> requests(m, NULL);
  int request_cntr = 0;

  chunk *head = new chunk;
  head->start = 0;
  head->size = n;

  for (int i = 0; i < m; i++) {
    int raw;
    cin >> raw;

    if (raw > 0) {
      int k = raw;

      auto fitting_chunk = find_fitting_chunk(head, k);
      if (fitting_chunk == NULL) {
        cout << -1 << endl;
      } else {
        occupy_chunk(fitting_chunk);
        cout << fitting_chunk->start + 1 << endl;
      }
      requests[request_cntr] = fitting_chunk;
    } else {
      int t = (-raw) - 1;
      auto chunk = requests[t];
      if (chunk != NULL) {
        free_chunk(chunk);
        bool consolidated = true;
        while (chunk != NULL && consolidated) {
          consolidated = try_consolidate_chunks(chunk);
          chunk = chunk->prev;
        }
      }
    }
    request_cntr++;
  }

  return 0;
}

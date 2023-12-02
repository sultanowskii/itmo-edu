/* heap-1.c */

#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

#define HEAP_BLOCKS 16
#define BLOCK_CAPACITY 1024

enum block_status { BLK_FREE = 0, BLK_ONE, BLK_FIRST, BLK_CONT, BLK_LAST };

struct heap {
  struct block {
    char contents[BLOCK_CAPACITY];
  } blocks[HEAP_BLOCKS];
  enum block_status status[HEAP_BLOCKS];
} global_heap = {0};

struct block_id {
  size_t       value;
  bool         valid;
  struct heap* heap;
};

struct block_id block_id_new(size_t value, struct heap* from) {
  return (struct block_id){.valid = true, .value = value, .heap = from};
}
struct block_id block_id_invalid() {
  return (struct block_id){.valid = false};
}

bool block_id_is_valid(struct block_id bid) {
  return bid.valid && bid.value < HEAP_BLOCKS;
}

/* Find block */

bool block_is_free(struct block_id bid) {
  if (!block_id_is_valid(bid))
    return false;
  return bid.heap->status[bid.value] == BLK_FREE;
}

void __block_occupy(struct heap *heap, size_t i_start, size_t i_end) {
  size_t current_size = i_end - i_start + 1;

  if (current_size == 1) {
    heap->status[i_start] = BLK_ONE; 
    return;       
  }

  heap->status[i_start] = BLK_FIRST;
  for (size_t i = i_start + 1; i < i_end; i++) {
    heap->status[i] = BLK_CONT;
  }
  heap->status[i_end] = BLK_LAST;
}

/* Allocate */
//? ? ?
struct block_id block_allocate(struct heap* heap, size_t size) {
  size_t i_start = 0;
  size_t i_end = 0;

  if (size == 0) {
    goto _INVALID;
  }

  for (size_t i = 0; i < HEAP_BLOCKS; i++) {
    if (heap->status[i] == BLK_FREE) {
      i_end = i;
    } else {
      i_start = i + 1;
      continue;
    }

    size_t current_size = i_end - i_start + 1;

    if (current_size == size) {
      __block_occupy(heap, i_start, i_end);
      return block_id_new(i_start, heap);
    }
  }

_INVALID:
  return block_id_invalid();
}

void block_free(struct block_id bid) {
  if (!bid.valid) {
    return;
  }

  struct heap *heap = bid.heap;
  size_t i = bid.value;
  enum block_status block_is_one = heap->status[i] == BLK_ONE;
  enum block_status block_is_cont = heap->status[i] == BLK_CONT;

  if (block_is_cont) {
    return;
  }

  bid.valid = false;
  heap->status[i] = BLK_FREE;

  if (block_is_one) {
    return;
  }

  for (i; (i < HEAP_BLOCKS) && (heap->status[i] != BLK_FIRST); i++) {
    heap->status[i] = BLK_FREE;
  }
}

/* Printer */
const char* block_repr(struct block_id b) {
  static const char* const repr[] = {[BLK_FREE] = " .",
                                     [BLK_ONE] = " *",
                                     [BLK_FIRST] = "[=",
                                     [BLK_LAST] = "=]",
                                     [BLK_CONT] = " ="};
  if (b.valid)
    return repr[b.heap->status[b.value]];
  else
    return "INVALID";
}

void block_debug_info(struct block_id b, FILE* f) {
  fprintf(f, "%s", block_repr(b));
}

void block_foreach_printer(struct heap* h, size_t count,
                           void printer(struct block_id, FILE* f), FILE* f) {
  for (size_t c = 0; c < count; c++)
    printer(block_id_new(c, h), f);
}

void heap_debug_info(struct heap* h, FILE* f) {
  block_foreach_printer(h, HEAP_BLOCKS, block_debug_info, f);
  fprintf(f, "\n");
}
/*  -------- */

int main() {
  heap_debug_info(&global_heap, stdout);
  struct block_id bid1 = block_allocate(&global_heap, 3);
  heap_debug_info(&global_heap, stdout);
  struct block_id bid2 = block_allocate(&global_heap, 4);
  heap_debug_info(&global_heap, stdout);
  struct block_id bid3 = block_allocate(&global_heap, 10);
  heap_debug_info(&global_heap, stdout);
  struct block_id bid4 = block_allocate(&global_heap, 1);
  heap_debug_info(&global_heap, stdout);
  block_free(bid3);
  heap_debug_info(&global_heap, stdout);
  block_free(bid1);
  heap_debug_info(&global_heap, stdout);
  block_free(bid2);
  heap_debug_info(&global_heap, stdout);
  block_free(bid4);
  heap_debug_info(&global_heap, stdout);
  return 0;
}

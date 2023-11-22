#include <inttypes.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

void error(const char *s) {
  fprintf(stderr, "%s", s);
  abort();
}

#define print(x)                                                        \
  _Generic((x),                                                         \
           int64_t : int64_t_print(x),                                  \
           double : double_print(x),                                    \
           default : error("Unsupported operation"))

void int64_t_print(int64_t i) { printf("%" PRId64, i); }
void double_print(double d) { printf("%lf", d); }
void newline_print() { puts(""); }

#define DEFINE_LIST(type)                                 \
  struct list_##type {                                    \
    type value;                                           \
    struct list_##type* next;                             \
  };                                                      \
                                                          \
  struct list_##type *list_##type##_create(type value) { \
    struct list_##type *node = malloc(sizeof(struct list_##type)); \
    node->value = value; \
    node->next = NULL; \
    return node; \
  } \
  \
  void list_##type##_push(struct list_##type **list, type value) { \
    struct list_##type *new_node = list_##type##_create(value); \
    \
    if (list == NULL) { \
        *list = new_node; \
        return; \
    } \
    \
    struct list_##type *node = *list; \
    while (node->next != NULL) { \
        node = node->next; \
    } \
    node->next = new_node; \
  } \
  \
  void list_##type##_print(struct list_##type *list) { \
    struct list_##type *node = list; \
    while (node != NULL) { \
        print(node->value); \
        newline_print(); \
        node = node->next; \
    } \
  } \
  void list_##type##_delete(struct list_##type *list) { \
    struct list_##type *node = list; \
    while (node != NULL) { \
        struct list_##type *next_node = node->next; \
        node->value = 0; \
        node->next = NULL; \
        free(node); \
        node = next_node; \
    } \
  } \

DEFINE_LIST(int64_t)
DEFINE_LIST(double)

#define list_push(list, value)                                                        \
  _Generic((list),                                                             \
           struct list_int64_t ** : list_int64_t_push(list, value),                                  \
           struct list_double ** : list_double_push(list, value),                                    \
           default : error("Unsupported operation"))

#define list_print(list)                                                        \
  _Generic((list),                                                             \
           struct list_int64_t * : list_int64_t_print(list),                                  \
           struct list_double * : list_double_print(list),                                    \
           default : error("Unsupported operation"))

#define list_delete(list)                                                        \
  _Generic((list),                                                             \
           struct list_int64_t * : list_int64_t_delete(list),                                  \
           struct list_double * : list_double_delete(list),                                    \
           default : error("Unsupported operation"))

int main() {
  struct list_int64_t *l1 = list_int64_t_create(1);
  list_push(&l1, 2);
  list_push(&l1, 3);

  list_print(l1);
  puts("");

  struct list_int64_t *l2 = list_int64_t_create(1000);
  list_push(&l2, 1337);
  list_push(&l2, 42069);

  list_print(l2);
  puts("");

  struct list_double *l3 = list_double_create(3.14);
  list_push(&l3, 2.81);
  list_push(&l3, 90.90);

  list_print(l3);
  puts("");

  list_delete(l1);
  list_delete(l2);
  list_delete(l3);

  return 0;
}
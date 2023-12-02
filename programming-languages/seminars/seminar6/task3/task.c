#include "vec.h"

#include <stdio.h>
#include <inttypes.h>
#include <stdbool.h>

int main() {
    struct vec *vec = vec_init();
    vec_print(vec, stdout);

    vec_push_back(vec, 123);
    vec_print(vec, stdout);

    vec_push_back(vec, 456);
    vec_print(vec, stdout);

    vec_push_back(vec, 789);
    vec_print(vec, stdout);

    vec_set_at(vec, 1, 420);
    vec_print(vec, stdout);

    printf("vec[%zu]: %" PRId64 "\n", 1, vec_get_at(vec, 1));

    vec_change_length(vec, 1);
    vec_print(vec, stdout);

    vec_change_length(vec, 100);
    vec_print(vec, stdout);

    printf("actual size: %zu\n", vec_get_actual_size(vec));

    vec_change_length(vec, 5);
    vec_print(vec, stdout);

    struct vec *vec2 = vec_init();
    vec_push_back(vec2, -90);
    vec_push_back(vec2, -678);
    vec_push_back(vec2, -1000);
    vec_print(vec2, stdout);

    vec_extend(vec, vec2);
    vec_print(vec, stdout);

    vec_destroy(vec);
    vec_destroy(vec2);
    return 0;
}

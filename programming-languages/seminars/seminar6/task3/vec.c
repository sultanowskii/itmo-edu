#include "vec.h"

#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <stdio.h>
#include <inttypes.h>

#define DEFAULT_VEC_CAPACITY 64
#define MAX(a,b) ((a) > (b) ? (a) : (b))
#define MIN(a,b) ((a) < (b) ? (a) : (b))

struct vec {
    size_t capacity;
    size_t length;
    int64_t *data;
};

struct vec *vec_init(void) {
    struct vec *vec = malloc(sizeof(struct vec));

    vec->capacity = DEFAULT_VEC_CAPACITY;
    vec->length = 0;
    vec->data = malloc(DEFAULT_VEC_CAPACITY * sizeof(int64_t));

    return vec;
}

void vec_destroy(struct vec *vec) {
    vec->capacity = 0;
    vec->length = 0;
    free(vec->data);
    free(vec);
}

size_t vec_get_capacity(struct vec *vec) {
    return vec->capacity;
}

size_t vec_get_length(struct vec *vec) {
    return vec->length;
}

size_t vec_get_actual_size(struct vec *vec) {
    return vec->capacity * sizeof(int64_t);
}

int64_t vec_get_at(struct vec *vec, size_t idx) {
    if (idx >= vec->capacity) {
        return 0;
    }

    return vec->data[idx];
}

bool vec_set_at(struct vec *vec, size_t idx, int64_t value) {
    if (idx >= vec->capacity) {
        return false;
    }

    vec->data[idx] = value;

    return true;
}

void _vec_expand_up_to(struct vec *vec, size_t required_capacity) {
    while (vec->capacity < required_capacity) {
        vec->capacity *= 2;
    }
    vec->data = realloc(vec->data, vec->capacity * sizeof(int64_t));
}

void _vec_shrink_exactly_to(struct vec *vec, size_t required_capacity) {
    vec->data = realloc(vec->data, required_capacity * sizeof(int64_t));
    vec->capacity = required_capacity;
    vec->length = MIN(vec->length, required_capacity);
}

void vec_push_back(struct vec *vec, int64_t value) {
    size_t idx = vec->length++;

    if (idx >= vec->capacity) {
        _vec_expand_up_to(vec, idx + 1);
    }

    vec->data[idx] = value;
}

void vec_extend(struct vec *vec, struct vec *other) {
    size_t min_required_capacity = vec->length + other->length;

    size_t vec_end_idx = vec->capacity;

    if (min_required_capacity > vec->capacity) {
        _vec_expand_up_to(vec, min_required_capacity);
    }
    vec->length = min_required_capacity;

    for (size_t i = 0; i < other->length; i++) {
        vec->data[i + vec_end_idx] = other->data[i];
    }
}

void _vec_reduce_length(struct vec *vec, size_t new_length) {
    _vec_shrink_exactly_to(vec, new_length);
}

void _vec_increase_length(struct vec *vec, size_t new_length) {
    if (new_length > vec->capacity) {
        _vec_expand_up_to(vec, new_length);
    }

    vec->length = new_length;
}

void vec_change_length(struct vec *vec, size_t new_length) {
    if (vec->length == new_length) {
        return;
    }

    if (vec->length > new_length) {
        _vec_reduce_length(vec, new_length);
    } else {
        _vec_increase_length(vec, new_length);
    }
}

void vec_print(struct vec *vec, FILE *f) {
    fprintf(f, "<Vec cap: %zu, length: %zu, data: [", vec->capacity, vec->length);
    for (size_t i = 0; i < vec->length; i++) {
        fprintf(f, " %" PRId64 ",", vec->data[i]);
    }
    fprintf(f, "]>\n");
}

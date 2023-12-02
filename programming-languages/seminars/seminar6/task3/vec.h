#pragma once

#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <stdio.h>

struct vec;

struct vec *vec_init(void);

void vec_destroy(struct vec *vec);

size_t vec_get_capacity(struct vec *vec);

size_t vec_get_length(struct vec *vec);

size_t vec_get_actual_size(struct vec *vec);

int64_t vec_get_at(struct vec *vec, size_t idx);

bool vec_set_at(struct vec *vec, size_t idx, int64_t value);

void vec_push_back(struct vec *vec, int64_t value);

void vec_extend(struct vec *vec, struct vec *other);

void vec_change_length(struct vec *vec, size_t new_length);

void vec_print(struct vec *vec, FILE *f);

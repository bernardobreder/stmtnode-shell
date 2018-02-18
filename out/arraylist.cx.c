#include <stdio.h>
#include <stdlib.h>

struct arraylist_t {
	data void**;
	size int;
	max int;
};
struct arraylist_t* arraylist_new() {
	struct arraylist_t* self = ((struct arraylist_t*)malloc(sizeof(struct arraylist_t)));
	if (!self) {
		return 0;
	}
	void* data = self->data = ((void**)malloc(sizeof(void*) * self->max));
	if (!data) {
		free(self);
		return 0;
	}
	return self;
	free(self);
}
void arraylist_free(struct arraylist_t* self) {
	free(self->data);
	free(self);
}

#include <stdio.h>
#include <string.h>
#include "duktape.h"




duk_ret_t foo(duk_context *ctx) {
    if((int) duk_is_constructor_call(ctx))  printf("Foo() as constructor\n");
    return 0;
}

duk_ret_t add(duk_context *ctx) {
    double i = duk_require_number(ctx, -1);
    duk_pop(ctx);
    double j = duk_require_number(ctx, -1);
    duk_pop(ctx);

    duk_push_number(ctx, i + j);
    
    return 1;               
}


int main(int argc, const char *argv[])
{
    duk_context *ctx = NULL;

    if (argc != 2) {
        printf("Usage: %s [testfile].js\n", argv[0]);
        exit(1);
    }

    FILE *file = fopen( argv[1], "r" );

    if(!file) {
        printf("File >%s< does not exist!\n", argv[1]);
        exit(1);
    }

    ctx = duk_create_heap_default();
    if (!ctx) {
        printf("Failed to create a Duktape heap.\n");
        exit(1);
    }

    duk_push_global_object(ctx);
    duk_push_c_function(ctx, foo, 0);
    duk_push_object(ctx);
    duk_push_string(ctx, "Foo V0.1");
    duk_put_prop_string(ctx, -2, "version");
    duk_push_c_function(ctx, add, 2);
    duk_put_prop_string(ctx, -2, "add");
    duk_put_prop_string(ctx, -2, "prototype");
    duk_put_prop_string(ctx, -2, "Foo");
    duk_pop(ctx);


    if (duk_peval_file(ctx, argv[1]) != 0) {
        printf("Error: %s\n", duk_safe_to_string(ctx, -1));
        exit(1);
    }
    duk_pop(ctx); /* ignore result */


    duk_destroy_heap(ctx);
    if(file) fclose(file);
    return 0;
}

#include "../FileBot/header.h"
#include "assert_test.c"
#include <stdbool.h>

int worker_children;
int **pipes;       // Array of pipes, one for each child process
int *child_status; // Array to keep track of whether each child is busy
int num_ids;
int fd[2];
int *child_pids;
int processed_ids[] = {1, 2};
int num_ids = 2;

void test_id_exists()
{
    assert_test(id_exists(1) == true, "Test 1");
    assert_test(id_exists(2) == true, "Test 2");
    assert_test(id_exists(3) == false, "Test 3");
    assert_test(id_exists(4) == false, "Test 4");
}

int main()
{
    test_id_exists();
    return 0;
}
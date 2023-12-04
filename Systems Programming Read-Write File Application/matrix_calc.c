#include <stdio.h>
#include <stdlib.h>
#include "matrix_math.h"

//function prototypes
struct matrix* readMatrix(char* filename, Matrix* m);
int deleteMatrix(struct matrix *A);



int main(int argc, char* argv[]){
    //top and bottom matricies (this was how I kept them straight): [0, 1, 2, 3]   up
    //                                                            + [0, 1, 2, 3]   down
    //                                                            = [0, 2, 4, 6]   matrix after operation

    //put the data from the files into the matricies
    Matrix m1;
    Matrix m2;

    Matrix* up;
    up = readMatrix(argv[1], &m1);
    Matrix* down;
    down = readMatrix(argv[2], &m2);

    //if the matricies aren't the same size
    if(up->size != down->size){
        printf("This math cannot be performed.\n");
        return 0;
    }

    //add
    char option = *argv[3];
    if(option == 'a'){
        addition(up, down);
        return 0;
    }

    //subtract
    else if(option == 's'){
        subtraction(up, down);
        return 0;
    }

    //free the matricies
    deleteMatrix(up);
    deleteMatrix(down);

    return 0;
}


//takes in the name of the file to read from, reads the data in the file, and returns a pointer
//to the Matrix with the data stored
struct matrix* readMatrix(char* filename, Matrix* m){
    //initialize the Matrix, two variables for traversing the document,
    //and the file being read
    int integer;
    float decimal;
    FILE* f1 = fopen(filename, "r");
    
    //get the size of the Matrix
    fscanf(f1, "%d", &integer);
    m->size = integer;

    //prepare an array for the data
    float* data = malloc(integer*sizeof(float));
    float* init = data;

    //grab the data
    for(int i = 0; i < m->size; i++){
        fscanf(f1, "%f", &decimal);
        data[i] = decimal;
    }

    //store the pointer to the data
    m->data = init;

    //send it back
    return m;
}

int deleteMatrix(struct matrix *A){
    free(A->data);
    return 0;
}
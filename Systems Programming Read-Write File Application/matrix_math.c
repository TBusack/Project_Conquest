//matrix_math.c
#include <stdio.h>
#include "matrix_math.h"



//takes in two matricies and prints the sum of each element in the matrix
void addition(Matrix* alpha, Matrix* bravo){
    //intialize variables
    float result;
    Matrix a = *alpha;
    Matrix b = *bravo;

    //print header
    printf("A + B =\n");

    //print values
    for(int i = 0; i < a.size; i++){
        result = a.data[i] + b.data[i];
        printf("%6.2f ", result);
    }
}

//takes in two matricies and prints the difference between each element in the matrix
void subtraction(Matrix* alpha, Matrix* bravo){
    //initialize variables
    float result;
    Matrix a = *alpha;
    Matrix b = *bravo;

    //print header
    printf("A - B =\n");

    //print values
    for(int i = 0; i < a.size; i++){
        result = a.data[i] - b.data[i];
        printf("%7.2f ", result);
    }
}
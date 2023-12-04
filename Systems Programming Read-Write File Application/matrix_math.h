//matrix_math.h

//struct definition for the matrix
struct matrix{
    int size;
    float *data;
};

//new keyword for matricies
typedef struct matrix Matrix;

void addition(Matrix* alpha, Matrix* bravo);
void subtraction(Matrix* alpha, Matrix* bravo);
#include <iostream>
#include <string>
#include <iomanip>

#include "Matrix.h"

Matrix::Matrix(int l1){
    length = l1;
    data = new float[length];
    
}


Matrix::Matrix(){
    length = 0;
    data = NULL;
    
}



void Matrix::readMatrix(string fileName) {
    ifstream input;
    input.open(fileName);
    
    for (int i = 0; i < length; i++){
        input >> data[i];
    }
    input.close();
}




/*******************************************************************************
* void print(Matrix& A){
*
* Output:
*   Prints A to the screen
********************************************************************************/

void Matrix::print(){

   // print values to screen
    for (int i = 0; i < length; i++){
       cout << std::setw(8) << data[i];  
    }
    printf("\n");

}

/*******************************************************************************
* Matrix::~Matrix()
*
* Deconstructor for Matrix object
********************************************************************************/
Matrix::~Matrix(){
    delete data;
}


int Matrix::getLength(){
    return length;
 }


float* Matrix::getData(){
    return data;
}

Matrix Matrix::operator+(Matrix &B){
    Matrix C(length);
    for (int i = 0; i < length; i ++){
        C.data[i] = data[i] + B.data[i];
    }
    return C;
}

Matrix Matrix::operator+(float f){
    //create a dummy matrix
    Matrix C(length);

    //set each element equal to this object's data plus f
    for (int i = 0; i < length; i ++){
        C.data[i] = data[i] + f;
    }
    return C;
}

Matrix operator+(float f, const Matrix& A){
    //create a dummy matrix
    Matrix C(A.length);

    //set each element equal to A's element with f added to it
    for (int i = 0; i < A.length; i ++){
        C.data[i] = A.data[i] + f;
    }
    return C;
}

ostream& operator<<(ostream& os, const Matrix& A){
    //run through data in Matrix A and add it (along with a single space) to the output stream
    for(int i = 0; i < A.length; i++){
        os << A.data[i] << " ";
    }
    return os;
}
Full: matrix_calc.o matrix_math.o
	gcc -Wall matrix_calc.c matrix_math.c -o matrixCalc

matrix_math.exe: matrix_math.h matrix_math.c
	gcc -g -Wall -c matrix_math.c

matrix_calc.exe: matrix_math.h matrix_calc.c
	gcc -g -Wall -c matrix_calc.c

clean:
	del -f matrix_calc.o matrix_math.o matrixCalc.exe Full

all: Full

#include "Cat.h"

Cat::Cat(string tp, string c):Animal(c){

    type = tp;
}
Cat::Cat():Animal("black"){
    type = "unk";
}
void Cat::setType(string tp) {
    type = tp;
}

void Cat::displayInfo(string c) {
    cout << "I am a " << type << endl;
    cout << "My color is " << c << endl;
}

void Cat::meow() {
    cout << "I can speak! Meow meow!!" << endl;
}

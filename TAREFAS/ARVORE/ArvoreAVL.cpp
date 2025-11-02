#include <iostream>
#include "ArvoreAVL.h"

using namespace std;

int main(){
    ArvoreAVL *arvore = new ArvoreAVL();
    arvore->menu();
    delete arvore;
    return 0;
}
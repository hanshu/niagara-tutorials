#include <string.h>
#include <stdio.h>
#include <iostream>
#include "DllLoader.h"

using namespace std;

JNIEXPORT jstring JNICALL Java_com_tridiumap_niagaraDll_DllLoader_getMessage
  (JNIEnv *env, jobject instance){
    return env->NewStringUTF("World from static register method!");
}
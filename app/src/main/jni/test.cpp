//
// Created by 王光杰 on 16/10/19.
//
#include<stdio.h>
 #include<stdlib.h>
 #include<jni.h>

 jstring java_com_org_wangguangjie_application5_Application5Start_getString(JNIEnv* env,jobject object)
 {
    char str*="test!";
    jstring s=(*env)->NewStringUTF(env,str);
    return s;
 }


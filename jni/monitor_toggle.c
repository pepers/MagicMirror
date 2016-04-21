#include <windows.h>
#include "C:\Program Files\Java\jdk1.8.0_74\include\jni.h"

JNIEXPORT void JNICALL Java_main_java_MagicMirror_cMonitorOn
  (JNIEnv * env, jobject jobj)
{
  SendMessage(HWND_BROADCAST, WM_SYSCOMMAND, SC_MONITORPOWER, (LPARAM) -1);
}


JNIEXPORT void JNICALL Java_main_java_MagicMirror_cMonitorOff
  (JNIEnv * env, jobject jobj)
{
  SendMessage(HWND_BROADCAST, WM_SYSCOMMAND, SC_MONITORPOWER, (LPARAM) 2);
}

# MagicMirror
Java app to run on Raspberry Pi, behind a mirror, to display weather info   

[![license](https://img.shields.io/badge/license-GPL3-blue.svg?style=flat)](https://github.com/pepers/MagicMirror/blob/master/LICENSE)

## Table of Contents:
1. [About](README.md#about)
2. [Dependencies](README.md#dependencies)
3. [Notes](README.md#notes)

---
### About:
The plan is for this app to be loaded on a Raspberry Pi (with wifi capabilities, so probably Rasberry Pi 3 model B), with a PIR (Passive Infrared) sensor and a screen/display connected.  The screen will be placed behind the mirror, so that the display can be seen through the mirror.  When motion is detected in front of the mirror, the screen will turn on and info will be displayed to the user through the mirror.  Information to display includes: weather conditions, current temperature, relative temperature, and what types of clothes to wear for the current weather (pants or shorts, sweater or tshirt, type of jacket, etc.). 

---
### Dependencies:
- [gson](https://github.com/google/gson) - [/src/resources/gson-2.6.2.jar](https://github.com/pepers/MagicMirror/tree/master/src/main/resources/gson-2.6.2.jar)
- [weather-icons](https://github.com/erikflowers/weather-icons) - [/src/resources/weathericons-regular-webfont.ttf](https://github.com/pepers/MagicMirror/tree/master/src/main/resources/weathericons-regular-webfont.ttf)
- [pi4j](http://www.pi4j.com) - TODO: install for PIR motion detection support

---
### Notes:
- while in main directory (/MagicMirror); used `javah -classpath bin -jni -d jni main.java.MagicMirror` to generate C header file for two methods to turn monitor on/off
  - compile dlls in windows with [mingw](http://www.mingw.org/) (use [mingw-w64](https://mingw-w64.org/) for 64bit Windows) (use `.so` instead of `.dll` when recompiling under Linux for Rasberry Pi)
    - Windows - 32bit Java - `gcc -o monitor_toggle32.dll -shared "C:\Program Files\Java\jdk1.8.0_74\include\jni.h" monitor_toggle.c`
    - Windows - 64bit Java - `gcc -m64 -o monitor_toggle64.dll -shared "C:\Program Files\Java\jdk1.8.0_74\include\jni.h" monitor_toggle.c`

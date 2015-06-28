#!/bin/bash

#Assembles and runs the demo specified by the command line argument.
#The command line argument should be the name of the file with the main in it.

gradle assemble
cd build/classes/main
java com.demo.$1

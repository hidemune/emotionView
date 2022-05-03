#!/bin/bash

CURDIR=`dirname $0`
echo $CURDIR

cd $CURDIR

PATH=/home/user/jdk1.8.0_111/bin/:$PATH

java -jar dist/emotion.jar
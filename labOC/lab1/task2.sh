#!/bin/bash
str=""
cur=""
while [[ $cur != "q" ]]
do
str=$str$cur
read cur
done
echo $str

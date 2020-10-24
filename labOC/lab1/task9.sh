#!/bin/bash
res=0
for file in $(sudo find /var/log -type f -name "*.log")
do
res=$(($res+$(sudo wc -l $file | cut -f1 -d ' ')))
done
echo $res

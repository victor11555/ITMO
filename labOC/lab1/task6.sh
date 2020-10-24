#!/bin/bash
sudo grep -E "[\[INFO\]]" /var/log/anaconda/X.log | sort -r -k3 -t "[" | awk '{if($6=="[INFO]") $6="[Information]"; else if($6=="[WARN]") $6="[Warning]"; print $0}' > full.log

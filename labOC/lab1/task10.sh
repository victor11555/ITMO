#!/bin/bash

#echo $(tr '[]' '[\n*]' < $(man bash) | uniq -i -c)
#echo $(man bash | uniq -i -c | sort -r | head -n 3)
echo $(man bash | tr [:cntrl:] " " | tr [:punct:] " " | tr [:space:] '\n' | sort | uniq -c | sort -nr | head -n 4 | tail -n 3)

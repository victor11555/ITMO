#!/bin/bash
menu="1 - start nano\n2 - start vi\n3 - start links\n4 - exit"
choice=0
while [[ $choice -ne 4 ]]
do
echo -e $menu
read choice
case $choice in
1) /usr/bin/nano;;
2) /usr/bin/vi;;
3) /usr/bin/links;;
esac
done

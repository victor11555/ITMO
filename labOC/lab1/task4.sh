#!/bin.bash
if [[ $HOME == $PWD ]]
then
echo $HOME
exit 0
fi

echo "Error: not Home directory"
exit 1

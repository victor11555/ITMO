#!/bin/bash
grep -Eohsir "\w+@\w+\.\w{2,6}" /etc | sort | uniq > emails.lst

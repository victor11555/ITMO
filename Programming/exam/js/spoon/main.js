"use strict";
const readline = require("readline-sync");
// spoon

function FillMap(map) {
    map.set("1", "+");
    map.set("000", "-");
    map.set("010", ">");
    map.set("011", "<");
    map.set("00100", "[");
    map.set("0011", "]");
    map.set("0010110", ",");
    map.set("001010", ".");
}

function main() {
    let map = new Map();
    FillMap(map);
    let s = process.argv[2];
    let result = new Array(30000).fill(0);
    let checkOperation = 100000;
    let arr = [];
    let tmparr = [];

    let j = 0;
    for (let i = 0; i < s.length; i++) {
        let key = s.substring(j, i + 1);
        if (map.has(key)) {
            s = s.replace(key, map.get(key));
            i -= i - j;
            j = i + 1;
        }
    }
    let k = 0;
    for (let i = 0; i < s.length; i++) {
        switch (s.charAt(i)) {
            case '>':
                if (k >= 30000) {
                    console.log("Error, restriction on memory");
                    break;
                }
                k++;
                break;

            case '<':
                if (k < 0) {
                    console.log("Error, restriction on memory");
                    break;
                }
                k--;
                break;

            case '+':
                if (checkOperation <= 0) {
                    console.log("Error, restriction on actions");
                    break;
                } else {
                    checkOperation--;
                }
                result[k]++;
                break;

            case '-':
                result[k]--;
                break;

            case '[':
                tmparr.push(result[k]);
                arr.push('[');
                break;

            case ']':
                if (tmparr[tmparr.length - 1] !== 1) {
                    while (s.charAt(i) !== '[') {
                        i--;
                    }
                    tmparr[tmparr.length - 1]--;
                } else {
                    tmparr.pop();
                    arr.pop();
                }
                break;

            case '.':
                console.log(String.fromCharCode(result[k]));
                break;
        }
    }
}

main();
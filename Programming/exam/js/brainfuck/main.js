"use strict";
const readline = require("readline-sync");
// brainfuck

function main() {
    let s = process.argv[2];
    //let s = readline.question();//считывание нашей строки и по ней идти посимвольно
    let result  = new Array(30000).fill(0);
    let checkOperation = 100000;
    let arr = [];
    let tmparr = [];
    let k = 0;
    for (let i = 0; i < s.length; i++) {
        switch (s.charAt(i)) {
            case '>':
                if(k >= 30000) {
                    console.log("Error, restriction on memory");
                    break;
                }
                k++;
                break;

            case '<':
                if(k < 0) {
                    console.log("Error, restriction on memory");
                    break;
                }
                k--;
                break;

            case '+':
                if (checkOperation <= 0) {
                    console.log("Error, restriction on actions");
                    break;
                }else{
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
                    tmparr[tmparr.length - 1] --;
                } else {
                    tmparr.pop();
                    arr.pop();
                }
                break;

            case '.':
                console.log(String.fromCharCode(result[k]));
                //console.log(result[k]);
                break;
        }
    }
}
main();
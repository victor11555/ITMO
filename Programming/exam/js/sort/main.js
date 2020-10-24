//сортировка
"use strict"

const readline = require('readline');
const fs = require('fs');
let file = process.argv[process.argv.length-1];
let data = [];
let options = new Map([["--ignore-leading-blanks", false],
        ["--dictionary-order", false],
        ["--ignore-case", false],
        ["--general-numeric-sort", false],
        ["--numeric-sort", false],
        ["--ignore-nonprinting", false],
        ["--reverse", false]
    ]);

for(let i = 2; i < process.argv.length-1; i++){
    let cur_op = process.argv[i];
    if(!options.has(cur_op)){
        console.log("Некоректная опция: ", cur_op);
        process.exit();
    }
    options.set(cur_op, true);
}

new Promise(function (resolve, reject) {
    fs.access(file, function (error) {
        if (error) {
            let temp = file.split(" ");
            temp.forEach(function (line, index, arr) {
                if(options.get("--ignore-leading-blanks") && line === "\n"){
                    return;
                }
                data.push(line)
            });
        } else {
            let rl = readline.createInterface({
                input: fs.createReadStream(file),
                output: process.stdout,
                terminal: false
            });
            rl.on('line', function (line) {
                data.push(line);
            });
            rl.on("close", function (){
                resolve();
            })
        }
    })
}).then(function (){
    let sort_func = function (a, b){
        let a_tmp = a;
        let b_tmp = b;
        let sign = 1;
        if(options.get("--dictionary-order")){
            a_tmp = a_tmp.replace(/[^0-9a-zA-Z\s]/g, "");
            b_tmp = b_tmp.replace(/[^0-9a-zA-Z\s]/g, "");
        }
        if(options.get("--ignore-case")){
            a_tmp = a_tmp.toLowerCase();
            b_tmp = b_tmp.toLowerCase();
        }
        if(options.get("--general-numeric-sort")){
            a_tmp = parseFloat(a_tmp.replace(/[^0-9\.]/g, ""));
            b_tmp = parseFloat(b_tmp.replace(/[^0-9\.]/g, ""));
            console.log(a_tmp, b_tmp);
        }
        if(options.get("--numeric-sort")){
            a_tmp = parseInt(a_tmp.replace(/[^0-9]/g, ""));
            b_tmp = parseInt(b_tmp.replace(/[^0-9]/g, ""));
        }
        if(options.get("--ignore-nonprinting")){
            a_tmp = a_tmp.replace(/[\n\r\t\s]+/g, "");
            b_tmp = b_tmp.replace(/[\n\r\t\s]+/g, "");
            console.log(a_tmp, b_tmp);
        }
        if(options.get("--reverse")){
            sign = -1;
        }
        if(a_tmp > b_tmp){
            return sign;
        }
        else if(a_tmp < b_tmp){
            return -sign;
        }
        else{
            return 0;
        }
    }
    console.log(data.sort(sort_func));
})


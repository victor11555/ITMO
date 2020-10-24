//grep
"use strict"

const readline = require('readline');
const fs = require('fs');
let template = process.argv[2];
let file = process.argv[3];
let ic = process.argv[4];

function process_line(text){
    let template_list = template.split("|");
    let flag = "";
    if(ic === "--ignore-case"){
        flag = "i";
    }
    for(let cur of template_list) {
        if (new RegExp(cur, flag).test(text)) {
            console.log(text);
        }
    }
}

fs.access(file, function (error){
    if(error){
        let temp = file.split("\n");
        temp.forEach(function (line, index, arr) {process_line(line)});
    }
    else {
        let rl = readline.createInterface({
            input: fs.createReadStream(file),
            output: process.stdout,
            terminal: false
        });
        rl.on('line', function (line) {
            process_line(line);
        });
    }
})


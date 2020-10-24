//stabsort
"use strict"

const readline = require('readline');
const fs = require('fs');
let file = process.argv[2];
let data = new Map();

function Add(){
    let index = parseInt(arguments[0][0]);
    let str = arguments[0][1];
    if(!data.has(index)){
        data.set(index, []);
    }
    data.get(index).push(str);
}

function Remove(){
    let index = arguments[0][0];
    data.delete(parseInt(index));
}

function Print(){
    let output = arguments[0][0];
    let keys = data.keys();
    keys = Array.from(keys);
    let result = "";
    keys.sort((a,b)=>{return a-b}).forEach(function (key){
        data.get(key).forEach(function (str){
            result += key + " " + str + "\n";
        })
    })
    fs.writeFileSync(output, result);
}

let funcs = {
    "add": Add,
    "remove": Remove,
    "print": Print
};

function process_line(text){
    let command = text.split(" ");
    funcs[command[0]](command.slice(1));
}

fs.access(file, function (error){
    if(error){
        console.log("Файла со скриптом не существует");
        process.exit();
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


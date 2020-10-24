//csv
"use strict"

const readline = require('readline');
const fs = require('fs');
function LineCounter (){
    this.counter = 0;
    this.next = function (){
        return this.counter ++;
    }
}

let files = [];
let format_map = new Map([
    ["csv", [",", "\""]],
    ["ssv", [";", "\'"]],
    ["tsv", ["\t", ""]],
    ]
)

let column_names = [];
let columns = new Map();

for(let i = 2; i < process.argv.length; i++){
    files.push(process.argv[i]);
    column_names.push([]);
}

function process_line(text, format, isfirst, file_number){
    if(!format_map.has(format)){console.log("Неверное расширение");}
    let splitter = format_map.get(format)[0];
    let quot = format_map.get(format)[1];
    let line_data = [];
    let pref_split_pos = -1;
    let was_quot = false;
    Array.from(text).forEach(function (symbol, index){
        if(symbol === quot && !was_quot){
            was_quot = true;
            pref_split_pos = index;
        }
        else if (symbol === quot && was_quot){
            line_data.push(text.substring(pref_split_pos + 1, index));
            pref_split_pos = index + 1;
            was_quot = false;
        }
        else if (symbol === splitter && text[index - 1] === quot){
            return ;
        }
        else if(symbol === splitter && !was_quot){
            line_data.push(text.substring(pref_split_pos + 1, index));
            pref_split_pos = index;
        }
    });

    for(let value_index in line_data) {
        let value = line_data[value_index];
        if (isfirst) {
            column_names[file_number].push(value);
            if (!columns.has(value)) {
                columns.set(value, new Map());
            }
        }
        else {
            let column_name = column_names[file_number][value_index];
            let column_values = columns.get(column_name);
            if(!column_values.has(value)){
                column_values.set(value, 0);
            }
            column_values.set(value, column_values.get(value) + 1);
        }
    }
    
    //в конце мэп с названиями колонок где каждое значение это мэп с индексами в виде значений колонок,
    //а их значениями в виде кол-ва раз которое встретились

}
let files_process = [];

for(let i = 0; i < files.length; i++) {
    files_process.push(new Promise(function (resolve, reject){
        fs.access(files[i], function (error) {
            if (error) {
                console.log("Не существует файла с названием:", files[i]);
            } else {
                let format = files[i].substring(files[i].length - 3);

                let rl = readline.createInterface({
                    input: fs.createReadStream(files[i]),
                    output: process.stdout,
                    terminal: false
                });
                rl.line_counter = new LineCounter();
                rl.on('line', function (line) {
                    let num = rl.line_counter.next()
                    process_line(line, format, num === 0, i);
                });
                rl.on('close', function(){
                    resolve();
                })
            }
        })
    }));
}
function own_sort(a, b){
    return b[1] - a[1];
}

Promise.all(files_process).then(function (){
    for(let column_data of columns){
        let file_name = column_data[0] + ".csv";
        let data = column_data[1];
        data = Array.from(data).sort(own_sort);
        let text = column_data[0] + ";Частота\n";
        data.forEach(function (pair){
            text += "\"" + pair[0] + "\";" +  pair[1] + "\n";
        })
        fs.writeFileSync(file_name, text);
    }
})
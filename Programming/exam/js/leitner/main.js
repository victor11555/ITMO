//система Лейтнера
"use strict"

let readline_useranswer = require("readline-sync");
const readline_file = require('readline');
const fs = require('fs');
let translate_file = process.argv[2];
let translate_text ="";
let word_map = new Map();
let boxes = [];
for (let i = 0; i < 10; i++) {
    boxes.push([]);
}
let box_num = 0;
new Promise(function (resolve, reject) {
    fs.access("save.txt", function (error) {
        if (error) {
            fs.access(translate_file, function (error) {
                if (error) {
                    console.log("Файл словаря не существует");
                    reject();
                    process.exit();
                } else {
                    translate_text = fs.readFileSync(translate_file, "UTF-8");
                    let box_index = 0;
                    translate_text.split("\n").forEach(function (value) {
                        let word = get_word(value);
                        boxes[box_index].push(word);
                        ++box_index;
                        if (box_index > 9) {
                            box_index = 0;
                        }
                    });
                    resolve();
                }
            })
        } else {
            let rl = readline_file.createInterface({
                input: fs.createReadStream("save.txt"),
                output: process.stdout,
                terminal: false
            });
            rl.on('line', function (line) {
                if (line === "" ) {
                    ++box_num;
                } else if(line === "*"){
                    return ;
                } else {
                    let word = get_word(line);
                    boxes[box_num].push(word);
                }
            });
            rl.on('close', function (){
                resolve();
            })
        }
    })
}).then(function (){
    game();
})

function get_word(value){
    let translate_parts = value.split("-");
    let word = translate_parts[0].trim();
    translate_parts.splice(0, 1);
    let translate = translate_parts.join("").trim();
    word_map.set(word, translate);
    return word;
}

function game(){
    let probability = [];
    probability[0] = 1.5;
    for (let n = 2; n <= 10; n++){
        probability.push(Math.pow(1.5, n) + probability[n-2])
    }
    let max = probability[9];
    let min = 0;
    console.log("Для отсановки программы нажмите ввидите пустую строку");
    let answer = "";
    do{
        box_num = null;
        while (box_num === null || boxes[box_num].length === 0) {
            let interval_value = Math.random() * (max - min) + min;
            for (let i = 0; i < 10; i++) {
                if (interval_value < probability [i]) {
                    box_num = i;
                    break;
                }
            }
        }
        let word_num = Math.floor(Math.random() * boxes[box_num].length);
        let card = boxes[box_num].pop(word_num);
        answer = readline_useranswer.question(card + ": ");
        if (answer === word_map.get(card)) {
            console.log("Ответ правильный");
            boxes[0].push(card);
        } else if(answer !== ""){
            console.log("Ответ не верен. Правильный ответ: " + word_map.get(card));
            if(box_num < 9){
                boxes[box_num+1].push(card);
            }
        }
    }while (answer !== "")
    let save_text = "";
    save_text = boxes.reduce(function (a,b){
        if(Array.isArray(a)){
            a = a.reduce(function (word1, word2){
                if(word1.indexOf("-") === -1){
                    word1 = word1 + " - " + word_map.get(word1);
                }
                return word1 + "\n" + word2 + " - " + word_map.get(word2);
            });
        }
        if(b.length === 1){
            b = b[0] + " - " + word_map.get(b[0]);
        }
        else if(b.length === 0){
            b = "*";
        }
        else {
            b = b.reduce(function (word1, word2){
                if(word1.indexOf("-") === -1){
                    word1 = word1 + " - " + word_map.get(word1);
                }
                return word1 + "\n" + word2 + " - " + word_map.get(word2);
            });
        }
        return a + "\n\n" + b;
    })
    fs.writeFileSync("save.txt", save_text);
}
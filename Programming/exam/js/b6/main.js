"use strict";
const readline = require("readline-sync");
//крестики нолики

function BoardConfiguration (rows = 3, columns = 3) {
    this.rows = rows;
    this.columns = columns;
    let count = rows*columns;
    if((rows > 5) || (columns > 5)){
        console.log("Превышены допустимые размеры доски");
        return;
    }
    if((rows < 3) || (columns < 3)){
        console.log("Меньше допустимых размеров доски");
        return;
    }
    this._board = new Array(rows);
    for(let i = 0; i < rows; i++){
        this._board[i] = new Array(columns);
        for(let j = 0; j < columns; j++){
            this._board[i][j] = ".";
        }
    }
    this.print = function(){
        let board_str = "";
        for(let i = 0; i < rows; i++){
            board_str += this._board[i].join(" ");
            board_str += "\n";
        }
        console.log(board_str);
    }
    this.finish = null;
    this.makeMove = function (marker, row, column){
        if(row >= this.rows || column >= this.columns || this._board[row][column] !== "."){
            return false;
        }
        this._board[row][column] = marker;
        count--;
        this.print();
        let iswin = true;
        for(let i = 0; i < rows; i++){
            if(this._board[i][column] !== marker){
                iswin = false;
                break;
            }
        }
        if(!iswin){
            iswin = true;
            for(let i = 0; i < columns; i++){
                if(this._board[row][i] !== marker){
                    iswin = false;
                    break;
                }
            }
        }
        if(!iswin) {
            iswin = true;
            for (let i = 0; i < rows; i++) {
                if (this._board[i][i] !== marker) {
                    iswin = false;
                    break;
                }
            }
        }
        if (!iswin) {
            iswin = true;
            for (let i = 0; i < rows; i++) {
                if (this._board[i][columns-1-i] !== marker) {
                    iswin = false;
                    break;
                }
                if (!iswin) {
                    break;
                }
            }
        }
        if(iswin) {
            this.finish = marker;
        }
        else if(count === 0) {
            this.finish = ".";
        }
        return true;
    }
}

function Player (){
    this.set_game = function (marker, board){
        this._marker = marker;
        this._board = board;
    }
}

function HumanPlayer (){
    Player.apply(this, arguments);
    let player = this;
    this.move =  function () {
        return  new Promise(function (resolve, reject) {
            let isMoved = false;
            while (!isMoved) {
                console.log("Введите строку и столбец через пробел ");
                let coords = readline.question().split(" ");
                isMoved = player._board.makeMove(player._marker, parseInt(coords[0]), parseInt(coords[1]));
                if(!isMoved){
                    console.log("Нельзя поставить в эту ячейку");
                }
            }
            resolve();
        })
    }
}

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min; //Максимум не включается, минимум включается
}

function RandomPlayer (){
    Player.apply(this, arguments);
    let player = this;
    this.move = function (){
        return new Promise(function (resolve, reject) {
            let isMoved = false;
            while (!isMoved) {
                isMoved = player._board.makeMove(player._marker, getRandomInt(0, player._board.rows), getRandomInt(0, player._board.columns));
            }
            resolve();
        })
    };
}

function SnakePlayer (){
    Player.apply(this, arguments);
    let player = this;
    this.move = function (){
        return new Promise(function (resolve, reject){
            let isMoved = false;
            for(let r = 0; r < player._board.rows; r++) {
                for (let c = 0; c < player._board.columns; c++) {
                    isMoved = player._board.makeMove(player._marker, r, c);
                    if(isMoved){
                        break;
                    }
                }
                if(isMoved){
                    break;
                }
            }
            resolve();
        })
    };
}

async function GameServer(player1, player2, size){
    let active_player = player1;
    let board = new BoardConfiguration(size, size);
    player1.set_game("X", board);
    player2.set_game("0", board);
    while (true){
        await active_player.move();
        if(board.finish){
            if(board.finish === "."){
                console.log("Draw");
                return null;
            }
            else {
                console.log("Выйграл", board.finish);
                return active_player;
            }
        }
        if(active_player === player1){
            active_player = player2;
        }
        else {
            active_player = player1;
        }
    }
}

async function Tournament(laps, players, size){
    let points = new Array(laps+1);
    for(let i = 0; i <= laps; i++) {
        points[i] = new Array(players.length);
        for(let j = 0; j <players.length; j++){
            points[i][j] = 0;
        }
    }
    let lap = 1;
    while (lap <= laps) {
        for (let i = 0; i < players.length - 1; i++) {
            for (let j = i + 1; j < players.length; j++) {
                console.log("Lap: " + lap + ". " + "Играют: игрок " + i + " и игрок " + j + "\n");
                let winner = await GameServer(players[i], players[j], size);
                if(!winner){
                    points[lap-1][i]++;
                    points[lap-1][j]++;
                }
                else points[lap-1][players.indexOf(winner)] += 3;
            }
        }
        lap++;
    }

    let max_points = 0;
    let winner_num = -1;
    for(let j = 0; j < players.length; j++) {
        for(let i = 0; i < laps; i++) {
            points[laps][j] += points[i][j];
        }
        if(points[laps][j] > max_points){
            max_points = points[laps][j];
            winner_num = j;
        }
        else if(points[laps][j] === max_points){
            winner_num = -1;
        }
    }
    let caption = "Круг\t\t";
    for(let i = 0; i < players.length; i++) {
        caption += "Игрок " + i + "\t\t";
    }
    console.log(caption);
    for(let i = 0; i < points.length-1; i++) {
        console.log((i+1) + "\t\t" + points[i].join("\t\t"));
    }
    let result = "";
    for(let i = 0; i < points[laps].length; i++) {
        result+= points[laps][i]+"\t\t";
    }
    console.log("Result\t\t"+ result)
    if( winner_num === -1){
        console.log("Нет абсолютного победителя")
    }
    else{
        console.log("Абсолютный победитель Игрок", winner_num, "с результатом", max_points);
    }
    process.exit(0);
}

function Main(){
    console.log("1: HumanPlayer \n2: RandomPlayer \n3: SnakePlayer");
    console.log("Введите строку из номеров игроков через пробел ");
    let players_classes = [HumanPlayer, RandomPlayer, SnakePlayer];
    let players = [];
    let laps = 1;
    new Promise (function (resolve, reject){
        let are_not_players = true;
        let players_numb;
        while (are_not_players){
            are_not_players = false;
            players_numb = readline.question();
            players = [];
            if(players_numb.split(" ").length < 2){
                are_not_players = true;
                console.log("Недопустимые номера игроков, введите заново");
                continue;
            }
            for(let i of players_numb.split(" ")){
                i = parseInt(i);
                if(!i){
                    are_not_players = true;
                    console.log("Недопустимые номера игроков, введите заново");
                    break;
                }
                players.push(new players_classes[i - 1]())
            }
        }
        resolve();
    }).then(function (){
        console.log("Введите количество кругов");
        return new Promise(function (resolve, reject){
            let is_not_cycles = true;
            while(is_not_cycles){
                is_not_cycles = false;
                laps = parseInt(readline.question());
                if(!laps){
                    is_not_cycles = true;
                    console.log("Недопустимое количество кругов");
                }
            }
            resolve();
        })

    }).then(function () {
        console.log("Введите размерность доски");
        return new Promise(function (resolve, reject){
            let is_not_size = true;
            let size;
            while(is_not_size){
                is_not_size = false;
                size = parseInt(readline.question());
                if(!size || size < 3 || size > 5){
                    is_not_size = true;
                    console.log("Недопустимая размерность доски");
                }
            }
            resolve(size);
        })
    }).then(function (size) {
        Tournament(laps, players, size)
    });
}

Main();


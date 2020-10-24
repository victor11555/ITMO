//палочки
"use strict";
const readline = require("readline-sync");

function BoardConfiguration (rows = 2, columns = 2) {
    this.rows = rows*3-(rows-1);
    this.columns = columns*3-(columns-1);
    this.count = rows*columns;
    if((rows < 2) || (columns < 2)){
        console.log("Меньше допустимых размеров доски");
        return;
    }
    this._board = new Array(this.rows);
    for(let i = 0; i < this.rows; i++){
        this._board[i] = new Array(this.columns);
        if(i % 2 === 1) {
            for(let j = 0; j < this.columns; j++){
                this._board[i][j] = " ";
            }
            continue;
        }
        for(let j = 0; j < this.columns; j++){
            if(j % 2 === 0) {
                this._board[i][j] = "*";
            }
            else {
                this._board[i][j] = " ";
            }
        }
    }
    this.print = function(){
        let board_str = "";
        for(let i = 0; i < this.rows; i++){
            board_str += this._board[i].join("");
            board_str += "\n";
        }
        console.log(board_str);
    }
    this.finish = null;
    this.check_square = function (x, y){
        return this._board[x+1][y] === "—" && this._board[x-1][y] === "—" && this._board[x][y+1] === "|"
            && this._board[x][y-1] === "|";
    }
    this.makeMove = function (marker, row, column){
        if(row >= this.rows || column >= this.columns || this._board[row][column] !== " " ||
            row % 2 === 0 && column % 2 === 0 || row % 2 === 1 && column % 2 === 1){
            return null;
        }
        let points = 0;
        if(row % 2 === 0){
            this._board[row][column] = "—";
            if(row > 0 && this.check_square(row-1, column)){
                this._board[row-1][column] = marker;
                this.count--;
                points++;
            }
            if(row < this.rows-1 && this.check_square(row+1, column)){
                this._board[row+1][column] = marker;
                this.count--;
                points++;
            }
        }
        else {
            this._board[row][column] = "|"
            if(column > 0 && this.check_square(row, column-1)){
                this._board[row][column-1] = marker;
                this.count--;
                points++;
            }
            if(column < this.columns-1 && this.check_square(row, column+1)){
                this._board[row][column+1] = marker;
                this.count--;
                points++;
            }
        }
        this.print();
        return points;
    }
}

function Player (){
    this.set_game = function (marker, board){
        this.points = 0;
        this._marker = marker;
        this._board = board;
    }
    this.move =  function () {
        let player = this;
        return new Promise(function (resolve, reject) {
            let points = undefined;
            do {
                let coords = player.get_coords(points !== undefined);
                points = player._board.makeMove(player._marker, parseInt(coords[0]), parseInt(coords[1]));
            } while (points === null)
            player.points += points;
            resolve();
        })
    }
}

function HumanPlayer (){
    Player.apply(this, arguments);
    let player = this;
    this.get_coords = function (repeat) {
        if(repeat){
            console.log("Нельзя поставить в эту ячейку");
        }
        console.log("Введите строку и столбец через пробел");
        return readline.question().split(" ");
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
    this.get_coords = function (repeat) {
        return  [getRandomInt(0, player._board.rows), getRandomInt(0, player._board.columns)];
    }
}

function SnakePlayer (){
    Player.apply(this, arguments);
    let player = this;
    this.get_coords = function (repeat) {
        let x,y, start_c;
        for(let r = 0; r < player._board.rows; r++) {
            if(r % 2 === 0){
                start_c = 1
            }
            else {
                start_c = 0;
            }
            for (let c = start_c; c < player._board.columns; c+=2) {
                if(player._board._board[r][c] === " "){
                    x = r;
                    y = c;
                    break;
                }
            }
            if(x !== undefined){
                break;
            }
        }
        return [x, y];
    }
}

async function GameServer(players, sizes){
    let board = new BoardConfiguration(sizes[0], sizes[1]);
    for(let i in players) {
        players[i].set_game(String.fromCharCode("A".charCodeAt(0)+parseInt(i)), board);
    }
    while (true){
        let player_max_points = 0;
        let winner = null;
        for(let player of players) {
            console.log("Ходит игрок " + player._marker);
            await player.move();
            if(player_max_points < player.points){
                player_max_points = player.points;
                winner = player;
            }
            else if(player_max_points === player.points) {
                winner = null;
            }
            if (board.count === 0) {
                return winner;
            }
        }
    }
}

async function Tournament(laps, players, sizes){
    let points = new Array(laps+1);
    for(let i = 0; i <= laps; i++) {
        points[i] = new Array(players.length);
        for(let j = 0; j <players.length; j++){
            points[i][j] = 0;
        }
    }
    let lap = 1;
    while (lap <= laps) {
        console.log("Lap: " + lap + ".\n");
        let winner = await GameServer(players, sizes);
        if(!winner){
            for(let i in players) {
                points[lap-1][i]++;
            }
        }
        else points[lap-1][players.indexOf(winner)] += 3;
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
            let sizes;
            while(is_not_size){
                is_not_size = false;
                sizes = readline.question().split(" ");
                sizes[0] = parseInt(sizes[0]);
                sizes[1] = parseInt(sizes[1]);
                if(!sizes || sizes[0] < 2 || sizes[1] < 2){
                    is_not_size = true;
                    console.log("Недопустимая размерность доски");
                }
            }
            resolve(sizes);
        })
    }).then(function (sizes) {
        Tournament(laps, players, sizes)
    });
}

Main();


//реверси
"use strict";
const readline = require("readline-sync");

function BoardConfiguration () {
    this.rows = 8;
    this.columns = 8;
    this._board = new Array(this.rows);
    for(let i = 0; i < this.rows; i++){
        this._board[i] = new Array(this.columns);
        for(let j = 0; j < this.columns; j++){
            this._board[i][j] = ".";
        }
    }

    this.print = function(){
        let board_str = "";
        for(let i = 0; i < this.rows; i++){
            board_str += this._board[i].join(" ");
            board_str += "\n";
        }
        console.log(board_str);
    }

    this.free_places = this.rows*this.columns;
    this.makeMove = function (marker, row, column){
        let player_new_chips = 1;
        if(row >= this.rows || column >= this.columns || this._board[row][column] !== "."){
            return 0;
        }
        this._board[row][column] = marker;
        this.free_places --;
//проверка линий
        //вниз
        for(let i = row+1; i < this.rows; i++){
            if(this._board[i][column] === "."){
                break;
            }
            else if(this._board[i][column] === marker){
                for(let j = row+1; j < i; j++){
                    this._board[j][column] = marker;
                }
                player_new_chips += i - row - 1;
                break;
            }
        }
        //вправо
        for(let i = column+1; i < this.columns; i++){
            if(this._board[row][i] === "."){
                break;
            }
            else if(this._board[row][i] === marker){
                for(let j = column+1; j < i; j++){
                    this._board[row][j] = marker;
                }
                player_new_chips += i - column - 1;
                break;
            }

        }
        //вверх
        for(let i = row-1; i > -1; i--){
            if(this._board[i][column] === "."){
                break;
            }
            else if(this._board[i][column] === marker){
                for(let j = row-1; j > i; j--){
                    this._board[j][column] = marker;
                }
                player_new_chips += row - i - 1;
                break;
            }
        }
        //влево
        for(let i = column-1; i > -1; i--){
            if(this._board[row][i] === "."){
                break;
            }
            else if(this._board[row][i] === marker){
                for(let j = column-1; j > i; j--){
                    this._board[row][j] = marker;
                }
                player_new_chips += column - i - 1;
                break;
            }
        }
//проверка диагоналей
        //низ право
        for (let i = 1; i < Math.min(this.rows-row, this.columns-column)-1; i++) {
            let x = row + i;
            let y = column + i;
            if (this._board[x][y] === ".") {
                break;
            }
            else if(this._board[x][y] === marker){
                for(let j = 1; j < i; j++){
                    this._board[row+j][column+j] = marker;
                }
                player_new_chips += i-1;
                break;
            }
        }
        //вверх лево
        for (let i = 1; i < Math.min(row, column)+1; i++) {
            let x = row - i;
            let y = column - i;
            if (this._board[x][y] === ".") {
                break;
            }
            else if(this._board[x][y] === marker){
                for(let j = 1; j < i; j++){
                    this._board[row - j][column-j] = marker;
                }
                player_new_chips += i-1;
                break;
            }

        }
        //вниз влево
        for (let i = 1; i < Math.min(this.rows-row-1, column); i++) {
            let x = row + i;
            let y = column - i;
            if (this._board[x][y] === ".") {
                break;
            }
            else if(this._board[x][y] === marker){
                for(let j = 1; j < i; j++){
                    this._board[row+j][column-j] = marker;
                }
                player_new_chips += i-1;
                break;
            }
        }
        //вверх право
        for (let i = 1; i < Math.min(row, this.columns-column-1); i++) {
            let x = row - i;
            let y = column + i;
            if (this._board[x][y] === ".") {
                break;
            }
            else if(this._board[x][y] === marker){
                for(let j = 1; j < i; j++){
                    this._board[row-j][column+j] = marker;
                }
                player_new_chips += i-1;
                break;
            }
        }
        this.print();
        return player_new_chips;
    }
}

function Player (){
    this.set_game = function (marker, board){
        this.chips_count = 0;
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
            } while (points === 0 && player._board.free_places !== 0)
            player.chips_count += points;
            resolve(points);
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
        for(let r = 0; r < player._board.rows; r++) {
            for (let c = 0; c < player._board.columns; c++) {
                if(player._board._board[r][c] === "."){
                    return [r, c];
                }
            }
        }
    }
}

async function GameServer(player1, player2){
    let active_player = player1;
    let board = new BoardConfiguration();
    player1.set_game("B", board);
    player2.set_game("W", board);
    while (board.free_places){
        console.log("Ходит игрок " + active_player._marker);
        let points = await active_player.move();
        if(active_player === player1){
            if(points > 1){
                player2.chips_count -= (points-1);
            }
            active_player = player2;
        }
        else {
            if(points > 1){
                player1.chips_count -= (points-1);
            }
            active_player = player1;
        }
        console.log("Player1("+player1._marker+"): " + player1.chips_count, "Player2("+player2._marker+"): " + player2.chips_count);
    }
    if(player1.chips_count === player2.chips_count){
        return undefined;
    }
    return player1.chips_count > player2.chips_count ? player1 : player2;
}

async function Tournament(laps, players){
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
                let winner = await GameServer(players[i], players[j]);
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
        Tournament(laps, players)
    });
}

Main();


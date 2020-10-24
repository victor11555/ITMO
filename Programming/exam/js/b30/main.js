//калах
"use strict";
const readline = require("readline-sync");

function BoardConfiguration (stones) {
    this.rows = 2;
    this.columns = 6;
    this.kalah = [0, 0];
    this._board = new Array(this.rows);
    this.has_stones = [true, true];
    for(let i = 0; i < this.rows; i++){
        this._board[i] = new Array(this.columns);
        for(let j = 0; j < this.columns; j++){
            this._board[i][j] = stones/12;
        }
    }

    this.print = function(){
        let board_str = this.kalah[0] + "\t";
        board_str += this._board[0].join("\t");
        board_str += "\n";
        board_str += "\t" + this._board[1].join("\t");
        board_str += "\t" + this.kalah[1];
        console.log(board_str);
    }

    this.makeMove = function (row, column){
        let opposite_row = Number(!Boolean(row));
        this.has_stones = [false, false];
        if(column >= this.columns || this._board[row][column] === 0){
            return false;
        }
        let stones_count = this._board[row][column];
        this._board[row][column] = 0;
        let x, y;
        let start = column+1;
        if(row === 0){
            start = column -1;
        }
        while (stones_count){
            if(row === 1) {
                for (let i = start; i < this.columns; i++) {
                    if (!stones_count) {
                        if(x === undefined ) {
                            y = i - 1;
                            x = row;
                        }
                        break;
                    }
                    this._board[row][i]++;
                    stones_count--;
                }
                if (stones_count) {
                    this.kalah[row]++;
                    stones_count--;
                }
                else if(x === undefined){
                    x = row
                    y = this.columns-1;
                }
                for (let i = this.columns - 1; i > -1; i--){
                    if(!stones_count) {
                        break;
                    }
                    this._board[row - 1][i]++;
                    stones_count--;
                }
                if (stones_count) {
                    this.kalah[opposite_row]++;
                    stones_count--;
                }
                start = 0;
            }
            else {
                for (let i = start; i > -1; i--){
                    if (!stones_count) {
                        if(x === undefined ) {
                            y = i + 1;
                            x = row;
                        }
                        break;
                    }
                    this._board[row][i]++;
                    stones_count--;
                }
                if (stones_count) {
                    this.kalah[row]++;
                    stones_count--;
                }
                else if(x === undefined){
                    x = row
                    y = 0;
                }
                for (let i = 0; i < this.columns; i++) {
                    if (!stones_count){
                        break;
                    }
                    this._board[row+1][i]++;
                    stones_count--;
                }
                if (stones_count) {
                    this.kalah[opposite_row]++;
                    stones_count--;
                }
                start = this.columns-1;
            }
        }
        if(x!== undefined && this._board[x][y] === 1 && this._board[opposite_row][y] !== 0){
            this.kalah[x] += this._board[opposite_row][y] + 1;
            this._board[x][y] = 0;
            this._board[opposite_row][y] = 0;
        }

        for(let i = 0; i < this.columns; i++){
            if(this._board[row][i] !== 0){
                this.has_stones[row] = true
            }
        }

        for(let i = 0; i < this.columns; i++){
            if(this._board[opposite_row][i] !== 0){
                this.has_stones[opposite_row] = true
            }
        }
        if(!this.has_stones[row]){
            for(let i = 0; i < this.columns; i++){
                this.kalah[opposite_row] += this._board[opposite_row][i];
                this._board[opposite_row][i] = 0;
            }
        }
        else if(!this.has_stones[opposite_row]){
            for(let i = 0; i < this.columns; i++){
                this.kalah[row] += this._board[row][i];
                this._board[row][i] = 0;
            }
        }
        this.print();
        return true
    }
}

function Player (){
    this.set_game = function (number, board){
        this.number = number;
        this._board = board;
    }
    this.move =  function () {
        let player = this;
        return new Promise(function (resolve, reject) {
            let is_moved = undefined;
            do {
                let column = player.get_column(is_moved !== undefined);
                is_moved = player._board.makeMove(player.number, parseInt(column));
            } while (!is_moved)
            resolve();
        })
    }
}

function HumanPlayer (){
    Player.apply(this, arguments);
    this.get_column = function (repeat) {
        if(repeat){
            console.log("Нельзя взять из этой лунки");
        }
        console.log("Введите номер лунки");
        return readline.question();
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
    this.get_column = function (repeat) {
        return getRandomInt(0, player._board.columns);
    }
}

function SnakePlayer (){
    Player.apply(this, arguments);
    let player = this;
    this.get_column = function (repeat) {
        for(let c = 0; c < player._board.columns; c++) {
            if(player._board._board[player.number][c] !== 0) {
                return c;
            }
        }
    }
}

async function GameServer(player1, player2, stones){
    let active_player = player1;
    let board = new BoardConfiguration(stones);
    player1.set_game(0, board);
    player2.set_game(1, board);
    while ((board.kalah[0] !== board.kalah[1] || board.has_stones[0] && board.has_stones[1])  && board.kalah[0] <= stones/2
    && board.kalah[1] <=stones/2 && board.has_stones[0] && board.has_stones[1]){
        console.log("Ходит игрок " + active_player.number);
        await active_player.move();
        if(active_player === player1){
            active_player = player2;
        }
        else {
            active_player = player1;
        }
    }
    if(board.kalah[0] === board.kalah[1]){
        return null;
    }
    return board.kalah[0] > board.kalah[1] ? player1 : player2;
}

async function Tournament(laps, players, stones){
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
                let winner = await GameServer(players[i], players[j], stones);
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
        caption += "Игрок " + (i+1) + "\t\t";
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

    }).then(function (){
    console.log("Введите количество камней");
    return new Promise(function (resolve, reject){
        let stones;
        do{
            if(stones !== undefined){
                console.log("Недопустимое количество камней");
            }
            stones = parseInt(readline.question());
        }while(stones % 12 !== 0 || stones === 0)
        resolve(stones);
    })
    }).then(function (stones) {
        Tournament(laps, players, stones)
    });
}

Main();


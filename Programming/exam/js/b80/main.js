"use strict";
const readline = require("readline-sync");
//4 в ряд

function BoardConfiguration (rows = 6, columns = 7) {
    this.rows = rows;
    this.columns = columns;
    let count = rows*columns;
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
    this.makeMove = function (marker, column){
        if(this._board[0][column] !== "."){
            return false;
        }
        let row = -1;
        for(let i = 1; i < this.rows; i++){
            if(this._board[i][column] !== "." ){
                this._board[i-1][column] = marker;
                row = i-1;
                count--;
                break;
            }
            else if(i === this.rows-1 && this._board[i][column] === "."){
                this._board[i][column] = marker;
                row = i;
                count--;
            }
        }
        this.print();
        let count_same = 0;
        for(let i = 0; i < rows; i++){
            if(this._board[i][column] === marker){
                count_same ++;
                if(count_same === 4){
                    break;
                }
            }
            else {
                count_same = 0;
            }
        }
        if(count_same !== 4){
            count_same = 0;
            for(let i = 0; i < columns; i++){
                if(this._board[row][i] === marker){
                    count_same ++;
                    if(count_same === 4){
                        break;
                    }
                }
                else {
                    count_same = 0;
                }
            }
        }
        if(count_same !== 4){
            count_same = 0;
            let i_diag = row;
            let j_diag = column;
            while(i_diag !== 0 && j_diag !== 0){
                i_diag--;
                j_diag--;
            }
            let j = j_diag;
            for (let i = i_diag; i < rows; i++) {
                if(j === this.columns) {
                    break;
                }
                if (this._board[i][j++] === marker) {
                    count_same++;
                    if (count_same === 4) {
                        break;
                    }
                } else {
                    count_same = 0;
                }
            }
        }
        if(count_same !== 4){
            count_same = 0;
            let i_diag = row;
            let j_diag = column;
            while(i_diag !== 0 && j_diag !== columns-1){
                i_diag--;
                j_diag++;
            }
            let j = j_diag;
            for (let i = i_diag; i < rows; i++) {
                if(j === -1) {
                    break;
                }
                if (this._board[i][j--] === marker) {
                    count_same++;
                    if (count_same === 4) {
                        console.log("DIAGONAL2")
                        break;
                    }
                } else {
                    count_same = 0;
                }
            }
        }
        if(count_same === 4) {
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
        this._board_config = board;
    }
}

function HumanPlayer (){
    Player.apply(this, arguments);
    let player = this;
    this.move =  function () {
        return  new Promise(function (resolve, reject) {
            let isMoved = false;
            while (!isMoved) {
                console.log("Введите столбец");
                let c = parseInt(readline.question());
                isMoved = player._board_config.makeMove(player._marker, c);
                if(!isMoved){
                    console.log("Нельзя бросить в эту колонку");
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
                isMoved = player._board_config.makeMove(player._marker, getRandomInt(0, player._board_config.columns));
            }
            resolve();
        })
    };
}

function ComputerPlayer (){//бросаем в стоолбец который меньше заполнен
    Player.apply(this, arguments);
    let player = this;
    this.move = function (){

        return new Promise(function (resolve, reject){
            let max_count = 0, min_column;
            for(let j = 0; j < player._board_config.columns; j++){
                let cur_count = 0;
                for(let i = 0; i < player._board_config.rows; i++){
                    if(player._board_config._board[i][j] !== "."){
                        cur_count = i;
                        break;
                    }
                    if(i === player._board_config.rows -1 && cur_count === 0){
                        cur_count = player._board_config.rows;
                    }
                }
                if(cur_count > max_count || cur_count === player._board_config.rows){
                    max_count = cur_count;
                    min_column = j;
                }
            }
            player._board_config.makeMove(player._marker,  min_column);
            resolve();
        })
    };
}

async function GameServer(player1, player2){
    let active_player = player1;
    let board = new BoardConfiguration();
    player1.set_game("R", board);
    player2.set_game("Y", board);
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
    console.log("1: HumanPlayer \n2: RandomPlayer \n3: ComputerPlayer");
    console.log("Введите строку из номеров игроков через пробел ");
    let players_classes = [HumanPlayer, RandomPlayer, ComputerPlayer];
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

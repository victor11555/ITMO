"use strict"
const readline = require("readline-sync");
//морской бой

function BoardConfiguration (rows = 10, columns = 10) {
    this.rows = rows;
    this.columns = columns;
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

    this.setShip = function (x1, y1, x2, y2){
        if(x1 < 0 || x1 >= this.rows || x2 < 0 || x2 >= this.rows || y1 < 0 || y1 >= this.columns || y2 < 0 || y2 >= this.columns ){
            return false;
        }
        if(x1===x2 && y1===y2){
            if(this._board[x1][y1] === "."){
                for(let i = x1-1; i <= x1+1; i++){
                    for(let j = y1-1; j <= y1+1; j++){
                        if(i >= 0 && i < this.rows && j >= 0 && j < this.columns && this._board[i][j] !== "."){
                            return false
                        }
                    }
                }
                this._board[x1][y1] = "H";
            }
            else {
                return false;
            }
        }
        else if(x1===x2){
            if(y2<y1){
                let tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
            for(let j = y1-1; j <= y2+1; j++){
                if((j >= 0 && j < this.columns) && (this._board[x1][j] !== "." || (x1-1 >= 0 && this._board[x1-1][j] !== ".") || (x1+1 < this.rows && this._board[x1+1][j] !== "."))){
                    return false;
                }
            }
            for(let j = y1; j <= y2; j++){
                this._board[x1][j] = "H";
            }
        }
        else {
            if(x2<x1){
                let tmp = x1;
                x1 = x2;
                x2 = tmp;
            }
            for(let i = x1-1; i <= x2+1; i++){

                if((i >= 0 && i < this.rows) && (this._board[i][y1] !== "." || (y1-1 >= 0 && this._board[i][y1-1] !== ".") || (y1+1 < this.columns && this._board[i][y1+1] !== "."))){
                    return false;
                }
            }
            for(let i = x1; i <= x2; i++){
                this._board[i][y1] = "H";
            }
        }
        this.print();
        return true;
    }
}

function Player (){
    this.ships = {
        1: 4,
        2: 3,
        3: 2,
        4: 1
    }
    this.count_ship = 0;

    this.set_opponent = function (opponent){
        this.oposite_board = new BoardConfiguration();
        this.opponent = opponent;
    }

    this._check_move = function (row, column){
        if(this.my_board._board[row][column]=== ".") {
            this.my_board._board[row][column] = "0";
            return 0;
        }
        this.my_board._board[row][column] = "x";
        let is_broken = true;
        let x1 = row,y1 = column,x2 = row,y2 = column;
        for(let i = row-1; i >=0; i--){
            if(this.my_board._board[i][column] !== "." && this.my_board._board[i][column] !== "0" && this.my_board._board[i][column] !== "x" ){
                is_broken = false;
                break
            }
            if(this.my_board._board[i][column]=== "." || this.my_board._board[i][column] === "0"){
                break;
            }
            x1 = i;
        }
        if(is_broken) {
            for (let i = row+1; i < this.my_board.rows; i++) {
                if(this.my_board._board[i][column] !== "." && this.my_board._board[i][column] !== "0" && this.my_board._board[i][column] !== "x" ){
                    is_broken = false;
                    break;
                }
                if(this.my_board._board[i][column]=== "." || this.my_board._board[i][column] === "0"){
                    break;
                }
                x2 = i;
            }
        }
        if(is_broken) {
            for (let j = column-1; j >= 0; j--) {
                if(this.my_board._board[row][j] !== "." && this.my_board._board[row][j] !== "0" && this.my_board._board[row][j] !== "x" ){
                    is_broken = false;
                    break
                }
                if(this.my_board._board[row][j]=== "." || this.my_board._board[row][j] === "0"){
                    break;
                }
                y1 = j;
            }
        }
        if(is_broken) {
            for (let j = column+1; j < this.my_board.columns; j++) {
                if(this.my_board._board[row][j] !== "." && this.my_board._board[row][j] !== "0" && this.my_board._board[row][j] !== "x" ){
                    is_broken = false;
                    break
                }
                if(this.my_board._board[row][j]=== "." || this.my_board._board[row][j] === "0"){
                    break;
                }
                y2 = j;
            }
        }
        if(is_broken){
            this.count_ship--;
            console.log(this.count_ship);
            return [x1, y1, x2, y2];
        }
        return 1;
    }

    this.set_ships = function (){
        let player = this;
        return new Promise(function (resolve, reject) {
            let attempts = 0;
            let set_ships = 0;
            player.count_ship = 0;
            for(let key in player.ships){
                player.count_ship += player.ships[key];
            }
            console.log(player.count_ship);
            while(set_ships < player.count_ship) {
                set_ships = 0;
                player.my_board = new BoardConfiguration();
                for (let ship_length of Object.keys(player.ships)) {
                    ship_length = parseInt(ship_length);
                    for (let i = 0; i < player.ships[ship_length]; i++) {
                        let done = false;
                        let error_message = "";
                        attempts = 0;
                        while (!done && attempts < 1000) {//дать 1000 раз поставить, если нельзя то заново
                            ++attempts;
                            let coords = player._get_coords(ship_length, error_message);
                            let x1 = parseInt(coords[0]);
                            let y1 = parseInt(coords[1]);
                            let x2 = parseInt(coords[2]);
                            let y2 = parseInt(coords[3]);
                            if (x1 === x2 && Math.abs(y2 - y1) + 1 === ship_length || y1 === y2 && Math.abs(x2 - x1) + 1 === ship_length) {
                                done = player.my_board.setShip(x1, y1, x2, y2);
                                if (!done) {
                                    error_message = "Нельзя установить корабль в эти координаты"
                                }
                                else{
                                    set_ships++;
                                }
                            } else {
                                error_message = "Не соответствует длина корабля";
                            }
                        }
                        if (attempts === 1000) {
                            break;
                        }
                    }
                    if (attempts === 1000) {
                        break;
                    }
                }
            }
            resolve();
        })
    }

    this.move = function (){
        let player = this;
        return new Promise(function (resolve, reject) {
            let isMoved = false;
            let x,y;
            let error_message = "";
            while (!isMoved) {
                let coords = player._get_coords_move(error_message);
                x = parseInt(coords[0]);
                y = parseInt(coords[1]);
                isMoved = player.oposite_board._board[x][y] === ".";
                if(!isMoved){
                    error_message = "Вы уже стреляли в эту точку";
                }
            }
            let result = player.opponent._check_move(x,y)
            if(result){
                player.oposite_board._board[x][y] = "x";
                if(player.opponent.count_ship === 0){
                    resolve(2);
                    player.oposite_board.print();
                    return ;
                }
                if(Array.isArray(result)){
                    for(let i = result[0]-1; i <= result[2]+1; i++) {
                        for (let j = result[1] - 1; j <= result[3] + 1; j++) {
                            if(i >= 0 && i < player.oposite_board.rows && j >= 0 && j < player.oposite_board.columns && player.oposite_board._board[i][j] !== "x"){
                                player.oposite_board._board[i][j] = "0";
                            }
                        }
                    }
                }
                player.oposite_board.print();
                resolve(1);
            }
            else {
                player.oposite_board._board[x][y] = "0";
                player.oposite_board.print();
                resolve(0);
            }
        })
    };
}

function HumanPlayer (){
     Player.apply(this, arguments);
     this._get_coords =  function (ship_length, error_message = "") {
         if(error_message){
             console.log(error_message);
         }
         console.log("Введите координаты начала корабля через пробел, длины",  ship_length);
         let coords = readline.question().split(" ");
         let x1 = parseInt(coords[0]);
         let y1 = parseInt(coords[1]);
         if(ship_length === 1){
             return [x1, y1, x1, y1];
         }
         console.log("Введите координаты конца корабля через пробел ");
         let coords2 = readline.question().split(" ");
         let x2 = parseInt(coords2[0]);
         let y2 = parseInt(coords2[1]);
         return [x1, y1, x2, y2];
     }

     this._get_coords_move = function (error_message= ""){
         if(error_message){
             console.log(error_message);
         }
         console.log("Ваши предыдущие ходы");
         this.oposite_board.print();
         console.log("Введите координаты выстрела");
         let coords = readline.question().split(" ");
         let x = coords[0];
         let y = coords[1];
         return [x, y];
     }
 }

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min; //Максимум не включается, минимум включается
}

function RandomPlayer (){
    Player.apply(this, arguments);
    this._get_coords = function (ship_length, error_message = ""){
        --ship_length;
        let x1 = getRandomInt(0, 10);
        let y1 = getRandomInt(0, 10);
        let x2, y2;
        let direction = getRandomInt(0, 4);
        switch (direction) {
            case 0:
                x2 = x1 + ship_length;
                y2 = y1;
                break;
            case 1:
                x2 = x1 - ship_length;
                y2 = y1;
                break;
            case 2:
                y2 = y1 + ship_length;
                x2 = x1;
                break;
            case 3:
                y2 = y1 - ship_length;
                x2 = x1;
                break;
        }
        return [x1, y1, x2, y2];
    }
    this._get_coords_move = function (error_message = ""){
        let x = getRandomInt(0, 10);
        let y = getRandomInt(0, 10);
        return [x, y];
    };
}

function ComputerPlayer (){
    Player.apply(this, arguments);
    this._get_coords = function (ship_length, error_message = ""){
        --ship_length;

        let x1;
        let y1;
        let direction = 1;
        let axis = 1;
        for(let j = 0; j < this.my_board.columns; j+=ship_length+2){
            if(this.my_board._board[ship_length*2][j] === "."){
                x1 = ship_length*2;
                y1 = j;
                break;
            }
        }
        let x2, y2;
        if(axis === 1){
            x2 = x1;
            y2 = y1 + direction * ship_length;
        }
        else {
            y2 = y1;
            x2 = x1 + direction * ship_length;
        }
        return [x1, y1, x2, y2];
    }

    this._get_coords_move = function (error_message = ""){
        let x;
        let y;
        for(let i = 0; i < this.oposite_board.rows; i++){
            for(let j = 0; j < this.oposite_board.columns; j++){
                if(this.oposite_board._board[i][j] === "."){
                    x = i;
                    y = j;
                    break;
                }
            }
            if(x !== undefined && y !== undefined){
                break;
            }
        }
        return [x, y];
    };
 }

async function GameServer(player1, player2){
    let active_player = player1;
    player1.set_opponent(player2);
    console.log("Игрок 0 устанавливает корабли");
    player1.set_ships();
    player2.set_opponent(player1);
    console.log("Игрок 1 устанавливает корабли");
    player2.set_ships();
    console.log("ходит игрок 0");
    while (true){
        let result = await active_player.move();
        if(result === 0) {//не попал, а 1 это попал
            if (active_player === player1) {
                active_player = player2;
                console.log("Ходит игрок 1");
            } else {
                active_player = player1;
                console.log("Ходит игрок 0");
            }
        }
        else if(result === 2){
            if(active_player === player1){
                console.log("Выйграл Игрок0");
            }
            else {
                console.log("Выйграл Игрок1");
            }
            return active_player; //выйграл
        }
        else {
            console.log("Попадание, повторный выстрел");
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
        console.log(i + "\t\t" + points[i].join("\t\t"));

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

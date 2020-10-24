//мат ладьей и королем
"use strict"
const readline = require("readline-sync");

let pos_black_k = [0, 0];
let pos_white_k = [0, 0];
let pos_white_l = [0, 0];
let board = [];

function letter_pos(a){
    return a.charCodeAt(0) - "a".charCodeAt(0);
}

function play(pos_bk, pos_wk, pos_wl) {
    pos_bk = pos_bk.split("");
    pos_black_k[0] = parseInt(pos_bk[1]) - 1;
    pos_black_k[1] = letter_pos(pos_bk[0]);
    pos_wk = pos_wk.split("");
    pos_white_k[0] = parseInt(pos_wk[1]) - 1;
    pos_white_k[1] = letter_pos(pos_wk[0]);
    pos_wl = pos_wl.split("");
    pos_white_l[0] = parseInt(pos_wl[1]) - 1;
    pos_white_l[1] = letter_pos(pos_wl[0]);
    for (let i = 0; i < 8; i++) {
        board.push(["[]", "[]", "[]", "[]", "[]", "[]", "[]", "[]"]);
    }
    board[pos_black_k[0]][pos_black_k[1]] = "bk";
    board[pos_white_k[0]][pos_white_k[1]] = "wk";
    board[pos_white_l[0]][pos_white_l[1]] = "wl";

    print();
    let step_count = true;
    do{
        if(step_count) {
            console.log("Ход комьютера");
            computer_step();
        }
        else {
            console.log("Ваш ход");
            human_step();
        }
        print();
        step_count = !step_count;
    }while (is_free_place())
    console.log("Game over");
}

function print(){
    let result = " \ta\tb\tc\td\te\tf\tg\th\n";
    board.forEach(function (line, index){
        result += (index+1) + "\t" + line.join("\t") + "\n";
    })
    console.log(result);
}

function wk_between_wl_bk(x_wl, y_wl, x_bk, y_bk){ //стоит ли белый король между ладьей и черным королем
    if(x_wl === x_bk && x_wl === pos_white_k[0]) {
        return Math.abs(y_wl - y_bk) ===
            Math.abs(y_wl - pos_white_k[1]) + Math.abs(pos_white_k[1] - y_bk);
    }
    if(y_wl === y_bk && y_wl === pos_white_k[1]) {
        return Math.abs(x_wl - x_bk) ===
            Math.abs(x_wl - pos_white_k[0]) + Math.abs(pos_white_k[0] - x_bk);
    }
}

function is_free_place(){//проверка есть ли куда сходить
    for(let i = -1; i <= 1; i++){
        for(let j = -1; j <= 1; j++){
            let x = pos_black_k[0] + i;
            let y = pos_black_k[1] + j;
            if((i === 0 && j === 0) || x < 0 || x >= 8 || y < 0 || y >= 8) {
                continue;
            }//границы
            if(board[x][y] !== '[]'){
                continue;
            }
            if((x !== pos_white_l[0] && y !== pos_white_l[1]) || wk_between_wl_bk(pos_white_l[0], pos_white_l[1], x, y) ){//не на линии ладьи
                if(!is_figure_near(x, y, "wk")){
                    return true;
                }
            }
        }
    }
    return false;
}

function is_wl_can_go(x, y) { //может ли сходить в координаты белая ладья
    let is_near_wk = false;
    let is_near_bk = false;
    if(x < 0 || x > 7 || y < 0 || y > 7)
        return false;
    if(board[x][y] !== '[]')
        return false;
    if ((x === pos_black_k[0] || y === pos_black_k[1]) && !wk_between_wl_bk(x, y, pos_black_k[0], pos_black_k[1]))
        return false;
    for(let i = -1; i <= 1; i++)
        for(let j = -1; j <= 1; j++) {
            let x_near = x + i;
            let y_near = y + j;
            if((i === 0 && j === 0) || x_near < 0 || x_near > 7 || y_near < 0 || y_near > 7)
                continue;
            if(x_near === pos_black_k[0] && y_near === pos_black_k[1] && !is_near_bk)
                is_near_bk = true;
            if(x_near === pos_white_k[0] && y_near === pos_white_k[1] && !is_near_wk)
                is_near_wk = true;
            if(is_near_bk && is_near_wk)
                return true;
        }
    return !is_near_bk;
}

function is_figure_near(x, y, figure) { //есть ли рядом с координатами указанная фигура
    let pos = pos_white_l;
    if(figure === "bk"){
        pos = pos_black_k;
    }
    else if (figure === "wk"){
        pos = pos_white_k;
    }
    for(let i = -1; i <= 1; i++) {
        for (let j = -1; j <= 1; j++) {
            let x_near = x + i;
            let y_near = y + j;
            if ((i === 0 && j === 0) || x_near < 0 || x_near > 7 || y_near < 0 || y_near > 7)
                continue;
            if (x_near === pos[0] && y_near === pos[1])
                return true;
        }
    }
    return false;
}

function is_wk_can_go(x, y) { //может ли сходить в координаты белый король
    if(x < 0 || x > 7 || y < 0 || y > 7)
        return false;
    if(board[x][y] !== '[]')
        return false;
    return is_figure_near(x, y, "wl") && !is_figure_near(x, y, "bk");
}

function is_bk_in_angle(){ //стоит ли черный король в углу
    for(let i = 0; i < 8; i += 7)
        for(let j = 0; j < 8; j += 7)
            if(pos_black_k[0] === i && pos_black_k[1] === j)
                return true;
    return false;
}

function computer_step() {
    // флаг сходила ли ладья
    let is_wl_go = false;
    //флаги равны ли координаты возможных шагов у черного короля (прижат ли король к стенке)
    let is_x_equals = true;
    let is_y_equals = true;
    for(let i = -1; i<=1; i++){// определеяем прижат ли черный король к стенке (остались ходы только по одной стороне)
        for (let j = -1; j<= 1; j++){
            let x = pos_black_k[0] + i;
            let y = pos_black_k[1] + j;
            if(x < 0 || x > 7 || y < 0 || y > 7)
                continue;
            if(board[x][y] !== '[]')
                continue;
            if(is_bk_can_go(x, y)){
                if(x !== pos_black_k[0]) {
                    is_x_equals = false;
                }
                if(y !== pos_black_k[1]) {
                    is_y_equals = false;
                }
            }
            if(!is_x_equals && !is_y_equals){
                break;
            }
        }
        if(!is_x_equals && !is_y_equals){
            break;
        }
    }
    //если черный король прижат к стенке и короли стоят друг напротив друга по горизонтали - ставим мат ладьей
    //тоже самое, если черный король в углу и белый король в позиции коня
    if(((pos_white_k[0] === pos_black_k[0] && is_y_equals
        || is_bk_in_angle() && is_y_equals
        && Math.abs(pos_black_k[1] - pos_white_k[1]) === 2
        && Math.abs(pos_black_k[0] - pos_white_k[0]) === 1)
        && !is_figure_near(pos_white_l[0], pos_black_k[1], "bk"))
        && !is_figure_near(pos_white_l[0], pos_black_k[1], 'bk')
        && board[pos_white_l[0]][pos_black_k[1]] === '[]') {
        board[pos_white_l[0]][pos_white_l[1]] = "[]";
        pos_white_l[1] = pos_black_k[1];
        board[pos_white_l[0]][pos_white_l[1]] = "wl";
        return;
    }
    //если черный король прижат к стенке и короли стоят друг напротив друга по вертикали - ставим мат ладьей
    //тоже самое, если черный король в углу и белый король в позиции коня
    if(((pos_white_k[1] === pos_black_k[1] && is_x_equals
        || is_bk_in_angle() && is_x_equals && Math.abs(pos_black_k[0] - pos_white_k[0]) === 2
        && Math.abs(pos_black_k[1] - pos_white_k[1]) === 1)
        && !is_figure_near(pos_black_k[0], pos_white_l[1], "bk"))
        && !is_figure_near(pos_black_k[0], pos_white_l[1], 'bk')
        && board[pos_black_k[0]][pos_white_l[1]] === '[]') {
        board[pos_white_l[0]][pos_white_l[1]] = "[]";
        pos_white_l[0] = pos_black_k[0];
        board[pos_white_l[0]][pos_white_l[1]] = "wl";
        return;
    }

    // если черного короля приперли к стенке - ходим только белым королем чтобы поставить его напротив черного
    if(!is_figure_near(pos_white_l[0], pos_white_l[1], "bk") && (is_x_equals
        && Math.abs(pos_white_l[0] - pos_black_k[0]) === 1
        && Math.abs(pos_white_k[0] - pos_black_k[0]) === 2
        || is_y_equals && Math.abs(pos_white_l[1] - pos_black_k[1]) === 1
        && Math.abs(pos_white_k[1] - pos_black_k[1]) === 2)) {
        // если черный король прижат к стенке и расстояние между черным королем и белым вдоль линии разделяющей их ладьи (например, черный
        // король потостоянно убегает от белого) равно 1, то делаем 1 ход белой ладьей чтобы рассинхронизировать движения черного короля
        // и белого (чтобы белый делал шаг раньше черного) и белый мог догнать черного
        if(is_y_equals && Math.abs(pos_white_k[0] - pos_black_k[0])  === 1 || is_x_equals && Math.abs(pos_white_k[1] - pos_black_k[1]) === 1) {
            let side = 0;
            let offset = 1;
            if(is_x_equals) {
                side = 1;
            }
            if(pos_white_l[side] - pos_white_k[side] < 0) {
                offset = -1;
            }
            board[pos_white_l[0]][pos_white_l[1]] = "[]";
            pos_white_l[side] += offset;
            board[pos_white_l[0]][pos_white_l[1]] = "wl";
        }
        //иначе ходим белым королем вдоль стенки, чтобы сравняться с черным королем
        else {
            let side = 0;
            let offset = 1;
            if(Math.abs(pos_white_l[0] - pos_black_k[0]) === 1) {
                side = 1;
            }
            if(pos_white_k[side] - pos_black_k[side] > 0) {
                offset = -1;
            }
            board[pos_white_k[0]][pos_white_k[1]] = "[]";
            pos_white_k[side] += offset;
            board[pos_white_k[0]][pos_white_k[1]] = "wk";
        }
        return;
    }
    // если прижали черного короля к стенке, и в этот момент белый король и черный король стоят на одной линии - отходим
    // черным королем назад на 1 линию, чтобы курсировать вдоль стенки и встать напротив черного короля
    if(is_x_equals && Math.abs(pos_white_l[0] - pos_black_k[0]) === 1 && Math.abs(pos_white_k[0] - pos_black_k[0]) === 1
        || is_y_equals && Math.abs(pos_white_l[1] - pos_black_k[1]) === 1 && Math.abs(pos_white_k[1] - pos_black_k[1]) === 1) {
        let side = 0;
        let offset = -1;
        if(Math.abs(pos_white_l[1] - pos_black_k[1]) === 1) {
            side = 1;
        }
        if(Math.abs(pos_white_k[side] - pos_black_k[side]) > 0) {
            offset = 1;
        }
        board[pos_white_k[0]][pos_white_k[1]] = "[]";
        pos_white_k[side] += offset;
        board[pos_white_k[0]][pos_white_k[1]] = "wk";
        return;
    }
    //если в начале игры рядом с ладьей нет своего короля - идем к нему чтобы защитить ладью
    if (!is_figure_near(pos_white_l[0], pos_white_l[1], "wk")) {
        board[pos_white_l[0]][pos_white_l[1]] = "[]";
        let x = pos_white_k[0] - pos_white_l[0];
        let y = pos_white_k[1] - pos_white_l[1];
        if (x === 0 ) {
            if(y > 0){
                y -= 1;
            }
            else {
                y += 1;
            }
        }
        else if (y === 0) {
            if(x > 0){
                x -= 1;
            }
            else {
                x += 1;
            }
        }
        if (Math.abs(pos_white_l[0] - pos_white_k[0]) < Math.abs(pos_white_l[1] - pos_white_k[1])) {
            if (is_wl_can_go(pos_white_l[0]+x, pos_white_l[1]) && x !== 0) {
                pos_white_l[0] += x;
                is_wl_go = true;
            }
            else if (is_wl_can_go(pos_white_l[0], pos_white_l[1]+y) && y !== 0) {
                pos_white_l[1] += y;
                is_wl_go = true;
            }
        } else {
            if (is_wl_can_go(pos_white_l[0], pos_white_l[1] + y) && y !== 0){
                pos_white_l[1] += y;
                is_wl_go = true;
            }
            else if (is_wl_can_go(pos_white_l[0] + x, pos_white_l[1]) && x !== 0) {
                pos_white_l[0] += x;
                is_wl_go = true;
            }
        }
        board[pos_white_l[0]][pos_white_l[1]] = "wl";
    }
    //иначе ходим ладьей ближе к черному королю (сокращая расстояние по стороне с меньшей разницей)
    else if(Math.abs(pos_white_l[0] - pos_black_k[0]) > 1 || Math.abs(pos_white_l[1] - pos_black_k[1]) > 1) {
        board[pos_white_l[0]][pos_white_l[1]] = "[]";
        //определение по какой стороне расстояние между черным королем и белой ладьей - меньше
        if (Math.abs(pos_white_l[0] - pos_black_k[0]) < Math.abs(pos_white_l[1] - pos_black_k[1])) {
            let offset = -1;
            if (pos_white_l[0] - pos_black_k[0] < 0) {
                offset = 1;
            }
            let x = pos_white_l[0] + offset;
            if (is_wl_can_go(x, pos_white_l[1]) && is_figure_near(x, pos_white_l[1], "wk")) {
                pos_white_l[0] = x;
                is_wl_go = true;
            }
            else if(Math.abs(pos_white_l[1] - pos_black_k[1]) > 1){
                offset = -1;
                if (pos_white_l[1] - pos_black_k[1] < 0) {
                    offset = 1;
                }
                let y = pos_white_l[1] + offset;
                if (is_wl_can_go(pos_white_l[0], y) && is_figure_near(pos_white_l[0], y, "wk")) {
                    pos_white_l[1] = y;
                    is_wl_go = true;
                }
            }
        } else {
            let offset = -1;
            if (pos_white_l[1] - pos_black_k[1] < 0) {
                offset = 1;
            }
            let y = pos_white_l[1] + offset;
            if (is_wl_can_go(pos_white_l[0], y) && is_figure_near(pos_white_l[0], y, "wk")) {
                pos_white_l[1] = y;
                is_wl_go = true;
            }
            else if(Math.abs(pos_white_l[0] - pos_black_k[0]) > 1 ){
                let offset = -1;
                if (pos_white_l[0] - pos_black_k[0] < 0) {
                    offset = 1;
                }
                let x = pos_white_l[0] + offset;
                if (is_wl_can_go(x, pos_white_l[1]) && is_figure_near(x, pos_white_l[1], "wk")) {
                    pos_white_l[0] = x;
                    is_wl_go = true;
                }
            }
        }
        board[pos_white_l[0]][pos_white_l[1]] = "wl";
    }
    //если ладья не сходила - ходим белым королем (в сторону, где удобнее потом будет поставить ладью)
    if(!is_wl_go){
        board[pos_white_k[0]][pos_white_k[1]] = "[]";
        let is_wk_go = false;
        //определение в какую сторону расстояние между ладьей и черным королем меньше - туда направляем белого короля
        if (Math.abs(pos_white_l[0] - pos_black_k[0]) < Math.abs(pos_white_l[1] - pos_black_k[1])) {
            let offset = -1;
            if (pos_white_k[0] - pos_black_k[0] < 0) {
                offset = 1;
            }
            let x = pos_white_k[0] + offset;
            if (is_wk_can_go(x, pos_white_k[1])) {
                pos_white_k[0] = x;
                is_wk_go = true;
            }
            //если белый король может сходить по дианогали, перекрывая черному королю большую площадь - ходим по диагонали
            offset = -1;
            if (pos_white_k[1] - pos_black_k[1] < 0) {
                offset = 1;
            }
            let y = pos_white_k[1] + offset;
            if (is_wk_can_go(pos_white_k[0], y)) {
                pos_white_k[1] = y;
                is_wk_go = true;
            }
        } else {
            let offset = -1;
            if (pos_white_k[1] - pos_black_k[1] < 0) {
                offset = 1;
            }
            let y = pos_white_k[1] + offset;
            if (!is_figure_near(pos_white_k[0], y, "bk")&& is_wk_can_go(pos_white_k[0], y)) {
                pos_white_k[1] = y;
                is_wk_go = true;
            }
            //если белый король может сходить по дианогали, перекрывая черному королю большую площадь - ходим по диагонали
            offset = -1;
            if (pos_white_k[0] - pos_black_k[0] < 0) {
                offset = 1;
            }
            let x = pos_white_k[0] + offset;
            if (!is_figure_near(x, pos_white_k[1], "bk") && is_wk_can_go(x, pos_white_k[1])) {
                pos_white_k[0] = x;
                is_wk_go = true;
            }
        }
        //если в начале игры ситуация, что черный король стоит между белым королем и ладьей. и ладья не может сходить на
        //встречу своему королю - делаем белым королем шаг в сторону своей ладьи
        if(!is_wk_go) {
            let offset = (pos_white_k[1] - pos_white_l[1]) / Math.abs(pos_white_k[1] - pos_white_l[1]);
            if(!is_figure_near(pos_white_k[0], pos_white_k[1] - offset, 'bk') && pos_white_k[1] !== pos_white_l[1]){
                pos_white_k[1] -= offset;
            } else {
                offset = pos_white_k[0] - pos_white_l[0];
                if(!is_figure_near(pos_white_k[0] - offset, pos_white_k[1], 'bk') && pos_white_k[0] !== pos_white_l[0]){
                    pos_white_k[0] -= offset;
                } else {
                    offset = 1;
                    let side = 0;
                    if(pos_white_k[0] === pos_white_l[0]) {
                        side = 1;
                    }

                    if(pos_white_k[side] - pos_black_k[side] < 0) {
                        offset = -1;
                    }
                    pos_white_k[side] += offset;
                }
            }
        }
        board[pos_white_k[0]][pos_white_k[1]] = "wk";
    }
}

function is_bk_can_go(x, y){
    if(x >= 0 && x < 8 && y >= 0 && y < 8) {//границы
        if(is_figure_near(x, y, "wk")){
            return false;
        }
        if((x === pos_white_l[0] || y === pos_white_l[1]) && !wk_between_wl_bk(pos_white_l[0], pos_white_l[1], x, y)){
            return false;
        }
    }
    else {
        return false
    }
    return true;
}

function human_step(query, callback){
    let x,y;
    do{
        if(x !== undefined){
            console.log("Нельзя сходить в эту позицию");
        }
        // для приема координат в браузере
        // let input = prompt("Введите координаты хода ");
        let input = readline.question("Введите координаты хода ", callback);
        x = parseInt(input[1])-1;
        y = letter_pos(input[0]);
    } while (Math.abs(x-pos_black_k[0]) > 1 || Math.abs(y-pos_black_k[1]) > 1 || !is_bk_can_go(x, y)
    || x-pos_black_k[0] === 0 && y-pos_black_k[1] === 0 )
    board[pos_black_k[0]][pos_black_k[1]] = "[]";
    pos_black_k[0] = x;
    pos_black_k[1] = y;
    board[pos_black_k[0]][pos_black_k[1]] = "bk";
}

// для приема координат в браузере
// let positions = prompt("введите начальные позиции фигур в алгебраической нотации через пробел").split(" ");
// play(positions[0], positions[1], positions[2]);

// для приема координат из командной строки
play(process.argv[2], process.argv[3], process.argv[4]);

//play("a1", "c1", "e2");

//ставим наши фигуры рядом и двигаем в сторону короля чужого
//двигаем его в угол
//https://chessrussian.ru/materialy/beginners/mat-korolem-i-ladey/

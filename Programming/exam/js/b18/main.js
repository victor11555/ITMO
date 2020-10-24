//кубик рубика
// S( между передней и задней) E (между верхней и нижней) M(между левой и правой) L(left) R(right) F(front) B(back) U(up) D(down)
let str = process.argv[2];
let cube = [];
let action_list = [];
let sides = ["L", "R", "F", "B", "U", "D", "S", "E", "M"];
let sides_indexes = new Map([
    ["F", [3,5,3,5]],
    ["U", [0,2,3,5]],
    ["D", [6,8,3,5]],
    ["L", [3,5,0,2]],
    ["R", [3,5,6,8]],
    ["B", [3,5,9,11]],
    ])

function create() {

    for (let i = 0; i < 9; i++) {
        cube.push([" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "]);
    }

    for (let i = 3; i < 6; i++) {
        for (let j = 0; j < 3; j++) {
            cube[i][j] = "R";//левая
        }
    }

    for (let i = 3; i < 6; i++) {
        for (let j = 3; j < 6; j++) {
            cube[i][j] = "B";//передняя
        }
    }

    for (let i = 3; i < 6; i++) {
        for (let j = 6; j < 9; j++) {
            cube[i][j] = "O";//правая
        }
    }

    for (let i = 3; i < 6; i++) {
        for (let j = 9; j < 12; j++) {
            cube[i][j] = "G";//задняя
        }
    }

    for (let i = 0; i < 3; i++) {
        for (let j = 3; j < 6; j++) {
            cube[i][j] = "W";//верхняя
        }
    }

    for (let i = 6; i < 9; i++) {
        for (let j = 3; j < 6; j++) {
            cube[i][j] = "Y";//нижняя
        }
    }
}

function print(){
    let res = "";
    for(let i = 0; i < cube.length; i++){
        res += cube[i].join(" ") + '\n';
    }
    console.log(res);
}

function parse(){
    for(let ch of str){
        if(sides.indexOf(ch)!== -1){
            action_list.push(ch);
        } else{
            action_list[action_list.length-1] += ch;
        }
    }
}

function get_tmp_matrix(){
    let tmp_matrix = [];
    for (let line of cube){
        tmp_matrix.push(line.slice());
    }
    return tmp_matrix;
}

function rotate_xy(start_x, end_x, start_y, end_y, direction = 1){
   let tmp_matrix = get_tmp_matrix();
   if(direction === -1){
       end_x = [start_x, start_x = end_x][0];
       end_y = [start_y, start_y = end_y][0];
   }
    if(direction === 1) {
        for (let i = start_x; i < end_x + 1; i++) {
            for (let j = start_y; j < end_y + 1; j++) {
                tmp_matrix[start_x + (j - start_y)][end_y - (i - start_x)] = cube[i][j];
            }
        }
    }
    else {
        for (let i = start_x; i > end_x-1; i--) {
            for (let j = start_y; j > end_y-1; j--) {
                tmp_matrix[start_x - (j - end_y)][start_y - (start_x - i)] = cube[i][j];
            }
        }
    }
    return tmp_matrix;
}

function rotate_z(start_x, end_x, start_y, end_y, direction = 1){
    let tmp_matrix = get_tmp_matrix();
    if(direction === -1){
        end_x = [start_x, start_x = end_x][0];
        end_y = [start_y, start_y = end_y][0];
    }
    if(direction === 1) {
        for (let i = start_x; i < end_x + 1; i++) {
            for (let j = start_y; j < end_y + 1; j++) {
                if(i !== start_x && i !== end_x && j !== start_y && j !== end_y){
                    continue;
                }
                tmp_matrix[start_x + (j - start_y)][end_y - (i - start_x)] = cube[i][j];
            }
        }
    }
    else {
        for (let i = start_x; i > end_x-1; i--) {
            for (let j = start_y; j > end_y-1; j--) {
                if(i !== start_x && i !== end_x && j !== start_y && j !== end_y){
                    continue;
                }
                tmp_matrix[start_x - (j - end_y)][start_y - (start_x - i)] = cube[i][j];
            }
        }
    }
    return tmp_matrix;
}

function slide(start_x, end_x, direction = 1){
    let tmp_matrix = get_tmp_matrix();

    if(direction === 1){
        for(let i = start_x; i < end_x+1; i++){
            for(let j = 0; j < 12; j++){
                if(j + 3 >= 12){
                    tmp_matrix[i][j] = cube[i][j-12+3];
                }
                else {
                    tmp_matrix[i][j] = cube[i][j+3];
                }
            }
        }
    }
    else {
        for (let i = start_x; i < end_x+1; i++) {
            for (let j = 11; j > -1; j--) {
                if (j - 3 < 0) {
                    tmp_matrix[i][j] = cube[i][j + 12 - 3];
                } else {
                    tmp_matrix[i][j] = cube[i][j - 3];
                }
            }
        }
    }
    return tmp_matrix;
}

function slide_y(direction = 1){
    let tmp_matrix = get_tmp_matrix();
    if(direction === 1){
        for(let i = 0; i < 9; i++){
            if(i + 3 >= 9){
                tmp_matrix[i][4] = cube[3+(8-i)][10];
                tmp_matrix[3+(8-i)][10] = cube[i-6][4];
            }
            else {
                tmp_matrix[i][4] = cube[i+3][4];
            }
        }
    }
    else {
        for (let i = 8; i > -1; i--) {
            if (i - 3 < 0) {
                tmp_matrix[i][4] = cube[3+(2-i)][10];
                tmp_matrix[3+(2-i)][10] = cube[i+6][4];
            } else {
                tmp_matrix[i][4] = cube[i-3][4];
            }
        }
    }
    return tmp_matrix;
}

function facial_round(action){
    let direction = 1, angle = 1;
    if(action.length > 1){
        if(action[1] === "'"){
            direction = -1;
        }
        else if(action[1] === "2"){
            angle = 2;
        }
    }
    for(let rotate_count = 0; rotate_count < angle; rotate_count++) {
        let indexes = sides_indexes.get(action[0]);
        if (action[0] === "U") {
            cube = rotate_xy(indexes[0], indexes[1], indexes[2], indexes[3], direction);
            cube = slide(3, 3, direction);
        } else if (action[0] === "D") {
            cube = rotate_xy(indexes[0], indexes[1], indexes[2], indexes[3], direction);
            cube = slide(5, 5, -direction);
        } else if (action[0] === "E") {
            cube = slide(4, 4, direction);
        } else if (action[0] === "M") {
            cube = slide_y(direction);
        } else if (action[0] === "S") {
            indexes = sides_indexes.get("F")
            cube = rotate_z(indexes[0] - 2, indexes[1] + 2, indexes[2] - 2, indexes[3] + 2, direction);
        } else {
            let rt_count = indexes[2] / 3 - 1;
            let self_direction = 1;
            if (rt_count === -1) {
                self_direction = -1;
                rt_count = 1;
            }
            for (let i = 0; i < rt_count; i++) {
                let up_indexes = sides_indexes.get("U");
                cube = rotate_xy(up_indexes[0], up_indexes[1], up_indexes[2], up_indexes[3], self_direction);
                cube = slide(3, 5, self_direction);
                let down_indexes = sides_indexes.get("D");
                cube = rotate_xy(down_indexes[0], down_indexes[1], down_indexes[2], down_indexes[3], -self_direction);
            }
            indexes = sides_indexes.get("F");
            cube = rotate_xy(indexes[0] - 1, indexes[1] + 1, indexes[2] - 1, indexes[3] + 1, direction);
            for (let i = 0; i < rt_count; i++) {
                let up_indexes = sides_indexes.get("U");
                cube = rotate_xy(up_indexes[0], up_indexes[1], up_indexes[2], up_indexes[3], -self_direction);
                cube = slide(3, 5, -self_direction);
                let down_indexes = sides_indexes.get("D");
                cube = rotate_xy(down_indexes[0], down_indexes[1], down_indexes[2], down_indexes[3], self_direction);
            }
        }
    }
}



create();
parse();
print();
for(let action of action_list){
    facial_round(action);
}
print();
//R2FR'F'D2R2UFU'BL2F2RF2U'LF2LBR'D'L'D'RF F'R'DLDRB'L'F2L'UF2R'F2L2B'UF'U'R2D2FRF'R2
//R2FR’F’D2R2UFU'BL2F2RF2U’LF2LBR’D’L’D’RF

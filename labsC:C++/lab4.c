#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#define DEBUGLEVEL 0
#define OP_NOP 0
#define OP_ADD 1
#define OP_SUB 2
#define OP_MUL 3
#define OP_DIV 4
#define OP_MOD 5
#define OP_SQRT 6
#define OP_L 7
#define OP_LE 8
#define OP_G 9
#define OP_GE 10
#define OP_EQ 11
#define OP_NEQ 12



struct tBigNumber{
    unsigned long *value;
    int length;
    long start_pos;
    long last_pos;
    int sign_flag;
    int point_flag;
};

struct tBigNumber tBigNumber1;
struct tBigNumber tBigNumber2;
int oper = OP_NOP;
int global_number_size = 0;
FILE *f_in;
const long buffsize = 128;
int return_code = 0;


//конструктор инициализирующий нулями
void bignum_constructor(struct tBigNumber *current_argum){
    (*current_argum).value = NULL;
    (*current_argum).length = 0;
    (*current_argum).start_pos = 0;
    (*current_argum).last_pos = 0;
    (*current_argum).sign_flag = 0;
    (*current_argum).point_flag = 0;
}



//проверка файла(подсчет длины чисел, проверка их на правильность, вывод длин их и вывод знака)
void check_file(){
    struct tBigNumber cur_tBigNumber;
    int cur_param_index = 1;
    int flag_eof = 0;
    char buff_op[6];
    int i = 0;
    bignum_constructor(&cur_tBigNumber);
    
    
    while(!flag_eof){
        char c = fgetc(f_in);
        
        if((c == -1) && (feof(f_in))) {
            c = 13;
            flag_eof = 1;
        }
    
        if((cur_param_index == 1) || (cur_param_index == 3) ){
            if ((c != 13) && (c != 10)) {   // если не конец строк
                if((c>= 48) && (c <= 57)){
                    if(cur_tBigNumber.length == 0){
                        cur_tBigNumber.start_pos = ftell(f_in)-1;
                    }
                    
                    cur_tBigNumber.length++;
                }
                else if((c == 45) && (cur_tBigNumber.length != 0)){
                    printf("Минус внутри числа\n");
                    return_code = 1;
                    break;//кидаем номер строки где ошибка
                }
                else if(c == 45){//отрицательное
                    cur_tBigNumber.sign_flag = 1;
                }
                else if(c == 46){//c точкой(
                    cur_tBigNumber.point_flag = 1;
                    printf("Число не целое\n");
                    return_code = 2;
                    break;
                }
                else{
                    printf("Число содержит недопустимый символ с ASCII кодом %i в позиции %d\n", c, cur_tBigNumber.length);
                    return_code = 3;
                    break;
                }
            }
            else {
            
                cur_tBigNumber.last_pos = ftell(f_in);
                if(!flag_eof){
                    cur_tBigNumber.last_pos--;
                }
                if(cur_param_index == 1){
                    tBigNumber1 = cur_tBigNumber;
                }
                if(cur_param_index == 3){
                    tBigNumber2 = cur_tBigNumber;
                }
                bignum_constructor(&cur_tBigNumber);
                cur_param_index++;
                continue;
            }
        }
        
        if(cur_param_index == 2) {
            if ((c != 13) && (c != 10) && (i < 5)) {
                buff_op[i] = c;
                i++;
            }
            else{
                buff_op[i] = 0;
                if(!strcmp(buff_op, "+")){
                    oper = OP_ADD;
                }
                else if(!strcmp(buff_op, "-")){
                    oper = OP_SUB;
                }
                else if(!strcmp(buff_op, "*")){
                    oper = OP_MUL;
                }
                else if(!strcmp(buff_op, "/")){
                    oper = OP_DIV;
                }
                else if(!strcmp(buff_op, "%")){
                    oper = OP_MOD;
                }
                else if(!strcmp(buff_op, "#")){
                    oper = OP_SQRT;
                }
                else if(!strcmp(buff_op, "<")){
                    oper = OP_L;
                }
                else if(!strcmp(buff_op, "<=")){
                    oper = OP_LE;
                }
                else if(!strcmp(buff_op, ">")){
                    oper = OP_G;
                }
                else if(!strcmp(buff_op, ">=")){
                    oper = OP_GE;
                }
                else if(!strcmp(buff_op, "==")){
                    oper = OP_EQ;
                }
                else if(!strcmp(buff_op, "!=")){
                    oper = OP_NEQ;
                }
                else {
                    printf("Операция не верна\n");
                    return_code = 4;
                }
                cur_param_index ++;
            }
        }
    }
}


//подсчет максимальной длины
void needed_length(){
    if(tBigNumber1.length > tBigNumber2.length){
        global_number_size = tBigNumber1.length;
    }
    else{
        global_number_size = tBigNumber2.length;
    }
    global_number_size = (global_number_size * 4/32) + 1;//4 бита на цифpу, на 32 размерность будущего массива в лонг
    global_number_size *= 2;
}

//запихивалка нулей
void bignum_put_zero(struct tBigNumber *first_bignum){
    while(1){
        if(first_bignum->value == NULL){
            first_bignum->value = (unsigned long*)malloc(sizeof(long)* global_number_size);
        }
        if(first_bignum->value == NULL){
            return_code = 10;
            break;
        }
        for(int i = 0; i < global_number_size; i++){
            first_bignum->value[i] = 0;
        }
        break;
    }
}

//копирование структур
void bignum_copy_struct(struct tBigNumber *first_bignum, struct tBigNumber *second_bignum){
    (*second_bignum).length = (*first_bignum).length;
    (*second_bignum).start_pos = (*first_bignum).start_pos;
    (*second_bignum).last_pos = (*first_bignum).last_pos;
    (*second_bignum).sign_flag = (*first_bignum).sign_flag;
    (*second_bignum).point_flag = (*first_bignum).point_flag;
    for(int i = 0; i < global_number_size; i++){
        (*second_bignum).value[i] = (*first_bignum).value[i];
    }
}

//чистка памяти
void memory_free_struct(struct tBigNumber *first_bignum){
    free(first_bignum->value);
}

//сравнение с 0
int bignum_compare_0(struct tBigNumber *first_bignum){
    for(int i = global_number_size - 1; i >=0; i--){
        if(first_bignum->value[i] != 0){
            return 0;
        }
    }
    return 1;
}

//четность
int bignum_odd(struct tBigNumber *first_bignum){
    if(first_bignum->value[0] % 2 == 1){
        return 1;
    }
    else {
        return 0;
    }
}

//проверка на отрицатильность числа
int bignum_is_minus(struct tBigNumber *first_bignum){
    if((first_bignum->value[global_number_size-1] & 0x80000000) > 0){
        return 1;
    }
    return 0;
}

//инкремент
void bignum_inc(struct tBigNumber *first_bignum){
    int flag_cary = 1;
    for(int i = 0; i < global_number_size; i++){
        long long a = (long long)first_bignum->value[i];
        long long c = a + flag_cary;
        flag_cary = 0;
        if(c & 0x100000000){
            flag_cary = 1;
            c -= 0x100000000;
        }
        first_bignum->value[i] = (long)c;
    }
}

//декремент
void bignum_dec(struct tBigNumber *first_bignum){
    for(int i = 0; i < global_number_size; i++){
        long a = (long)first_bignum->value[i];
        if (a == 0){
            first_bignum->value[i] = 0xffffffff;
        }
        else{
            first_bignum->value[i] = a - 1;
            break;
        }
    }
}

//сложение
int bignum_add(struct tBigNumber *first_bignum, struct tBigNumber *second_bignum, struct tBigNumber *result_bignum){
    int flag_cary = 0;
    int flag_nonzero = 0;
    for(int i = 0; i < global_number_size; i++){
        long long a = (long long)first_bignum->value[i];
        long long b = (long long)second_bignum->value[i];
        long long c = a + b + flag_cary;
        flag_cary = 0;
        if(c & 0x100000000){
            flag_cary = 1;
            c -= 0x100000000;
        }
        result_bignum->value[i] = (long)c;
        if(c != 0){
            flag_nonzero = 1;
        }
    }
    if(flag_nonzero == 0){
        return 0;
    }
    else{
        if((result_bignum->value[global_number_size-1] & 0x80000000) == 0x80000000){
            return -1;
        }
        else {
            return 1;
        }
    }
}

//отрицание
void bignum_neg(struct tBigNumber *first_bignum){
    for(int i = 0; i < global_number_size; i++){
        first_bignum->value[i] ^= 0xffffffff;
    }
    bignum_inc(first_bignum);
}

// вычетание
int bignum_sub(struct tBigNumber *first_bignum, struct tBigNumber *second_bignum, struct tBigNumber *result_bignum){
    struct tBigNumber tmp;
    bignum_constructor(&tmp);
    bignum_put_zero(&tmp);
    bignum_copy_struct(second_bignum, &tmp);
    bignum_neg(&tmp);
    int res = bignum_add(first_bignum, &tmp, result_bignum);
    memory_free_struct(&tmp);
    return res;
}

//сдвиг влево(а<=32)
void bignum_shift_l(struct tBigNumber *first_bignum, int a){
    long long tmp;
    long cary = 0;
    for(int i = 0; i < global_number_size; i++){
        tmp = first_bignum->value[i];
        tmp = tmp << a;
        first_bignum->value[i] = ((tmp & 0xffffffff) | cary);
        cary = (tmp & 0xffffffff00000000) >> 32;
    }
}

//сдвиг вправо
long bignum_shift_r(struct tBigNumber *first_bignum, int a, int *nonzero){
    long long tmp;
    int flag_nonzero = 0;
    long cary = 0;
    long mask = (1 << a) - 1;
    for(int i = global_number_size - 1; i >= 0; i--){
        tmp = first_bignum->value[i];
        long cary_new= (tmp & mask) << (32-a);
        tmp = (tmp >> a) | cary;
        first_bignum->value[i] = tmp ;
        if(tmp != 0){
            flag_nonzero = 1;
        }
        cary = cary_new;
    }
    if(nonzero != NULL){
        *nonzero = flag_nonzero;
    }
    return cary >> (32-a);
}

//101111101011110000100000000 = 10 v 8
//умножение  на 10^8
void bignum_mul_10v8(struct tBigNumber *first_bignum){
    struct tBigNumber tmp;
    bignum_constructor(&tmp);
    bignum_put_zero(&tmp);
    bignum_copy_struct(first_bignum, &tmp);
    bignum_shift_l(&tmp, 8);//x*2
    bignum_copy_struct(&tmp, first_bignum);
    bignum_shift_l(&tmp, 5);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 1);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 1);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 1);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 2);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 2);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 1);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 1);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 1);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 1);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
    bignum_shift_l(&tmp, 2);//x*8
    bignum_add(&tmp, first_bignum, first_bignum);
}

//сравнение
int bignum_compare(struct tBigNumber *first_bignum, struct tBigNumber *second_bignum){
    struct tBigNumber tmp;
    bignum_constructor(&tmp);
    bignum_put_zero(&tmp);
    int res = bignum_sub(first_bignum, second_bignum, &tmp);
    memory_free_struct(&tmp);
    return res;
}

//вывод на экран
void print_to_screen(char *str, struct tBigNumber *first_bignum){
    printf("%s\t",str);
    for(int i = global_number_size - 1; i >=0; i--){
        printf("%ld\t", first_bignum->value[i]);
    }
    printf("\n");
}

//умножение
void bignum_mul(struct tBigNumber *first_bignum, struct tBigNumber *second_bignum, struct tBigNumber *result_bignum){
    struct tBigNumber a, b, tmp;//в а -большее
    bignum_constructor(&a);
    bignum_constructor(&b);
    bignum_constructor(&tmp);
    bignum_put_zero(&a);
    bignum_put_zero(&b);
    bignum_put_zero(&tmp);
    int sign_of_a = 0;
    int sign_of_b = 0;
    
    if(bignum_compare(first_bignum, second_bignum) == -1){
        bignum_copy_struct(first_bignum, &b);
        bignum_copy_struct(second_bignum, &a);
    }
    else {
        bignum_copy_struct(first_bignum, &a);
        bignum_copy_struct(second_bignum, &b);
    }
    
    if(bignum_is_minus(&a) == 1){
        sign_of_a = -1;
        bignum_neg(&a);
    }
    else {
        sign_of_a = 1;
    }
    
    if(bignum_is_minus(&b) == 1){
        sign_of_b = -1;
        bignum_neg(&b);
    }
    else {
        sign_of_b = 1;
    }
    
    while(bignum_compare_0(&b)==0){
        if(bignum_odd(&b)==1){
            bignum_add(&tmp, &a, &tmp);
            bignum_dec(&b);
        }
        else{
            bignum_shift_l(&a, 1);
            bignum_shift_r(&b, 1, NULL);
        }
    }
    if(sign_of_a * sign_of_b == -1){
        bignum_neg(&tmp);
    }
    bignum_copy_struct(&tmp, result_bignum);
    memory_free_struct(&a);
    memory_free_struct(&b);
    memory_free_struct(&tmp);
}

//модуль структуры
void bignum_abs(struct tBigNumber *first_bignum){
    if( bignum_is_minus(first_bignum)== 1){
        bignum_neg(first_bignum);
    }
}

//деление
void bignum_div(struct tBigNumber *first_bignum, struct tBigNumber *second_bignum, struct tBigNumber *q, struct tBigNumber *r){
    
    struct tBigNumber a,b,d,tmp;
    bignum_constructor(&tmp);
    bignum_put_zero(&tmp);
    bignum_constructor(&d);
    bignum_put_zero(&d);
    bignum_constructor(&a);
    bignum_constructor(&b);
    bignum_put_zero(&a);
    bignum_put_zero(&b);
    bignum_copy_struct(first_bignum, &a);
    bignum_copy_struct(second_bignum, &b);
    int sign_of_a = 0;
    int sign_of_b = 0;
    
    if(bignum_is_minus(&a) == 1){
        sign_of_a = -1;
        bignum_neg(&a);
    }
    else {
        sign_of_a = 1;
    }
    
    if(bignum_is_minus(&b) == 1){
        sign_of_b = -1;
        bignum_neg(&b);
    }
    else {
        sign_of_b = 1;
    }
    bignum_put_zero(q);
    bignum_put_zero(r);
    int flag_exit = 0;
    
    while(1){
        if(bignum_compare_0(&b) == 1){
            printf("Деление на ноль\n");
            return_code = 5;
            break;
        }
        if(bignum_compare(&a, &b) == -1){
            bignum_copy_struct(&a, r);
            break;
        }
        if(bignum_compare(&a, &b) == 0){
            bignum_inc(q);
            break;
        }
        long num_bits = global_number_size * sizeof(unsigned long) * 4;
        
        while(bignum_compare(r, &b) == -1){
            int bit = ((a.value[global_number_size-1])& 0x80000000) >> 31;
            
            bignum_shift_l(r, 1);
            r->value[0] = r->value[0] | bit ;
            bignum_copy_struct(&a, &d);
            bignum_shift_l(&a, 1);
            num_bits--;
        }
        bignum_copy_struct(&d, &a);
        bignum_put_zero(&d);
        bignum_shift_r(r, 1, NULL);
        num_bits++;

        for(int i = 0; i < num_bits; i++){
            int bit = ((a.value[global_number_size-1])& 0x80000000) >> 31;
            bignum_shift_l(r, 1);
            r->value[0] = r->value[0] | bit ;
            if(!(bignum_compare(&b, r) == 1)){
                flag_exit = 1;
                bignum_sub(r, &b, &d);
            }
            else{
                flag_exit = 0;
            }
            bignum_shift_l(&a, 1);
            bignum_shift_l(q, 1);
            q->value[0] = q->value[0] | flag_exit;
            if((bignum_compare_0(q) == 0) && (flag_exit == 1)){
                bignum_copy_struct(&d, r);
            }

        }
        
        if((sign_of_a == -1) && (sign_of_b == -1)){

            if(bignum_compare_0(r) == 0){
                bignum_inc(q);
                bignum_abs(&b);
                bignum_sub(&b, r, &tmp);
                bignum_copy_struct(&tmp, r);
            }
            else {
                bignum_neg(q);
            }
            
        }
        else if((sign_of_a == -1) && (sign_of_b == 1)){
            if(bignum_compare_0(r) == 0){
                bignum_inc(q);
                bignum_neg(q);
                bignum_abs(&b);
                bignum_sub(&b, r, &tmp);
                bignum_copy_struct(&tmp, r);
            }
            else {
                bignum_neg(q);
            }
        }else if((sign_of_a == 1) && (sign_of_b == -1)){
            bignum_neg(q);
        }
        break;
    }
    memory_free_struct(&d);
    memory_free_struct(&a);
    memory_free_struct(&b);
    memory_free_struct(&tmp);
}

//квадратный корень
void bignum_sqrt(struct tBigNumber *first_bignum, struct tBigNumber *res_struct){
    struct tBigNumber tmp, d,rub_struct, rub2_struct;
    bignum_constructor(&tmp);
    bignum_put_zero(&tmp);
    bignum_constructor(&d);
    bignum_put_zero(&d);
    bignum_put_zero(res_struct);
    bignum_constructor(&rub_struct);
    bignum_put_zero(&rub_struct);
    bignum_constructor(&rub2_struct);
    bignum_put_zero(&rub2_struct);
    bignum_copy_struct(first_bignum, res_struct);
    bignum_copy_struct(first_bignum, &d);
    
    if(bignum_is_minus(first_bignum) == 0){
        if(bignum_compare_0(first_bignum) == 0){
            while(1){
                bignum_div(first_bignum, &d, &tmp, &rub_struct);
                
                bignum_add(&tmp, &d, &tmp);
                
                bignum_copy_struct(&tmp, &rub_struct);
                bignum_shift_r(&rub_struct, 1, NULL);
                bignum_copy_struct(&rub_struct, &d);
                
                if(bignum_odd(&tmp)==1){
                    bignum_inc(&d);
                }
                if(bignum_compare(res_struct, &d) == 1){
                    bignum_copy_struct(&d, res_struct);
                }
                else{
                    bignum_div(first_bignum, res_struct, &rub_struct, &rub2_struct);
                    bignum_inc(&rub_struct);
                    if((bignum_compare(&rub_struct, res_struct)==0) &&(bignum_compare_0(&rub2_struct) == 1)){
                        bignum_dec(res_struct);
                    }
                    break;
                }
            }
        }
        else {
            bignum_put_zero(res_struct);
        }
    
    }
    else {
        printf("Корень из отрицательного числа");
        return_code = 6;
    }
    
    memory_free_struct(&rub_struct);
    memory_free_struct(&rub2_struct);
    memory_free_struct(&d);
    memory_free_struct(&tmp);
}

//считывание
void read_value(struct tBigNumber *first_bignum){
    bignum_put_zero(first_bignum);
    struct tBigNumber tmp;
    bignum_constructor(&tmp);
    bignum_put_zero(&tmp);
    int cur_block = 0;
    char buff[9];
    long read_size = 8;
    long cur_pos = first_bignum->last_pos;
    int flag_exit = 0;
    
    while(flag_exit == 0){
        bignum_put_zero(&tmp);
        cur_pos -= read_size;
        if(cur_pos < first_bignum->start_pos){
            read_size = (cur_pos + read_size) - first_bignum->start_pos ;
            cur_pos = first_bignum->start_pos;
            flag_exit = 1;
        }
        fseek(f_in, cur_pos, 0);
        long real_size = fread(&buff, 1, read_size, f_in);
        buff[real_size] = 0;
        long cur_number = atol(buff);
        tmp.value[0] = cur_number;
        for(int i = 1; i <= cur_block; i++){
            bignum_mul_10v8(&tmp);
        }
        bignum_add(first_bignum, &tmp, first_bignum);
        cur_block ++;
    }
    
    if(first_bignum->sign_flag == 1){
        bignum_neg(first_bignum);
    }
    memory_free_struct(&tmp);
}

//функция вызывающая операцию
void calc(struct tBigNumber *result_bignum){
    bignum_put_zero(result_bignum);
    int res;
    struct tBigNumber tmp;
    bignum_constructor(&tmp);
    
    switch (oper) {
        case OP_ADD:
            bignum_add(&tBigNumber1, &tBigNumber2, result_bignum);
            break;
        case OP_SUB:
            bignum_sub(&tBigNumber1, &tBigNumber2, result_bignum);
            break;
        case OP_MUL:
            bignum_mul(&tBigNumber1, &tBigNumber2, result_bignum);
            break;
        case OP_L:
            res = bignum_compare(&tBigNumber1, &tBigNumber2);
            if(res == -1){
                bignum_inc(result_bignum);
            }
            break;
        case OP_LE:
            res = bignum_compare(&tBigNumber1, &tBigNumber2);
            if((res == -1)|| (res == 0)){
                bignum_inc(result_bignum);
            }
            break;
        case OP_G:
            res = bignum_compare(&tBigNumber1, &tBigNumber2);
            if(res == 1){
                bignum_inc(result_bignum);
            }
            break;
        case OP_GE:
            res = bignum_compare(&tBigNumber1, &tBigNumber2);
            if((res == 1)|| (res == 0)){
                bignum_inc(result_bignum);
            }
            break;
        case OP_EQ:
            res = bignum_compare(&tBigNumber1, &tBigNumber2);
            if(res == 0){
                bignum_inc(result_bignum);
            }
            break;
        case OP_NEQ:
            res = bignum_compare(&tBigNumber1, &tBigNumber2);
            if(res != 0){
                bignum_inc(result_bignum);
            }
            break;
        case OP_DIV:
            bignum_div(&tBigNumber1, &tBigNumber2, result_bignum, &tmp);
            break;
        case OP_SQRT:
            bignum_sqrt(&tBigNumber1, result_bignum);
            break;
        case OP_MOD:
            bignum_div(&tBigNumber1, &tBigNumber2, &tmp, result_bignum);
            break;
    }
}

//сохранение/печать в буфер
void print_to_buf(struct tBigNumber *first_bignum, char *buffer){
    char buff[9];
    struct tBigNumber num_10v8,result_int, result_rem;
    bignum_constructor(&num_10v8);
    bignum_constructor(&result_int);
    bignum_constructor(&result_rem);
    bignum_put_zero(&num_10v8);
    bignum_put_zero(&result_int);
    bignum_put_zero(&result_rem);
    bignum_inc(&num_10v8);
    bignum_mul_10v8(&num_10v8);
    bignum_copy_struct(first_bignum, &result_int);
    if(bignum_is_minus(&result_int) == 1){
        result_int.sign_flag = 1;
        bignum_neg(&result_int);
    }
    long j = 0;
    for(int i = 0; i <= global_number_size - 1; i++){
        bignum_div(&result_int, &num_10v8, &result_int,&result_rem);
        sprintf(buff, "%ld", result_rem.value[0]);
        long buf_size = strlen(buff);
        for (long m = buf_size - 1; m>=0; m--) {
            buffer[j] = buff[m];
            j++;
        }
        if(bignum_compare_0(&result_int) == 0){
            if(buf_size < 8){
                for (long k = 0; k< 8 - buf_size; k++) {
                    buffer[j] = 48;// 0 in ascii
                    j++;
                }
            }
        }
        else{
            break;
        }
    }
    if(result_int.sign_flag == 1){
        buffer[j] = 45;//- in ascii
        j++;
    }
    for(int i = 0; i < j/2; i++){
        char tmp = buffer[i];
        buffer[i] = buffer[j - i - 1];
        buffer[j - i - 1] = tmp;
    }
    buffer[j] = 0;
}

int main(int argc, char **argv) {
    struct tBigNumber result_bignum;
    bignum_constructor(&result_bignum);

    char *buffer = (char*)malloc(sizeof(char)* global_number_size * 10 + 1);
    if(buffer == NULL){
        return_code = 10;
    }
    while (1) {
        if(argc != 3){
            printf("Неверное кол-во аргументов\nИспользование: lab4 <имя_входного файла> <имя_выходного_файла>\n");
            return_code = 8;
            break;
        }
        if ((f_in = fopen(argv[1], "r")) == NULL){
            printf("Не удалось открыть входной файл\n");
            return_code = 9;
            break;
        }
        
        check_file();
        needed_length();
        
        
        if(return_code > 0){
            break;
        }
        
        read_value(&tBigNumber1);
        read_value(&tBigNumber2);
        fclose(f_in);
        
        calc(&result_bignum);
        
        
        FILE *f_out;
        if ((f_out = fopen(argv[2], "w")) == NULL){
            printf("Не удалось открыть выходной файл\n");
            return_code = 11;
            break;
        }
        if((return_code == 5) || (return_code == 6)){
            buffer[0] = 78;
            buffer[1] = 65;
            buffer[2] = 78;
        }
        else{
            print_to_buf(&result_bignum, buffer);
        }
        
        fprintf(f_out,"%s", buffer);

        fclose(f_out);
        
    
        break;
    }
    free(buffer);
    memory_free_struct(&tBigNumber1);
    memory_free_struct(&tBigNumber2);
    memory_free_struct(&result_bignum);
                       
    return return_code;
}

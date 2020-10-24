

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define DEBUGLEVEL 0


FILE *f_in;
double **row = NULL;
int  matrix_n;
const int buffsize = 128;
double *result;
double EPS = 0.000001;
int rank_flag = 0;
int flag_no_solut = 0;

//получение н
int get_N(){
    int n = 0;
    char buff[buffsize];
    if (fgets(buff, buffsize, f_in)){
        n = atoi(buff);
    }
    return n;
}

//считывание матрицы из файла
 int read_input_data(){
    int cur_row = 0;
    int cur_col = 0;
    char buff[buffsize];
    int i = 0;
    int flag_eof = 0;
    while(flag_eof == 0){
        char c = fgetc(f_in);
        if((c == -1) && (feof(f_in))) {
            c = 13;
            flag_eof = 1;
        }
        
        if((i==0) && ((c == 13) || (c == 10))){
            continue;
        }
        if ((c != 9) && (c != 13) && (c != 32) && (c != 10)) {// если не таб и не конец строки и не пробел
            if(c == 44){//меняю с запятой на точку
                c = 46;//точка
            }
            buff[i] = c;
            i++;
            
        }
        else {
            buff[i]=0;
            double d = atof(buff);
            *(row[cur_row]+cur_col) = d;
            //printf("value = %f row = %d collumn = %d \n", d, cur_row, cur_col);//////
            
            i = 0;
            cur_col++;
            if((c == 13) ||(c == 10)){
                if(cur_col-1 != matrix_n-1+1){
                    return (cur_row+1);//не хватило элементов матрицы в строке cur_row
                }
                cur_row ++;
                cur_col = 0;
            }else {
                if(cur_col-1 > matrix_n){
                    return (cur_row+1);//больш элементов матрицы в строке cur_row
                }
                
            }
            
        }
    }
     
     
     
    if(cur_row != matrix_n){
        return (-1);//не хватило строк в матрице
    }
     return 0;
}



//печать матрицы
void print_matrix(){
#if DEBUGLEVEL == 1
    for(int r = 0; r < matrix_n; r++){
        for(int c = 0; c < matrix_n + 1; c++){
            printf("%f\t", *(row[r] + c));
        }
        printf("\n");
    }
#endif
}

//печать результата
void print_result(){
#if DEBUGLEVEL == 1
    for(int r = 0; r < matrix_n; r++){
        printf("x (%i) = %f \n", r, result[r]);
    }
#endif
}


//выдача памяти
int memory_init(){
    int return_code = 0;
    row = (double**)malloc(sizeof(long*)*matrix_n);
    if(row == NULL){
        return 6;
    }
    for(int i = 0; i < matrix_n; i++){
        row[i] = (double*)malloc(sizeof(double)*(matrix_n+1));
        if(row[i] == NULL){
            return_code = 6;
        }
    }
    
    result = (double*)malloc(sizeof(double)*matrix_n);
    if(result == NULL){
        return_code = 6;
    }
    return return_code;
}


//освобождение памяти
void memory_free(){
    if(row !=  NULL){
        for(int i = 0; i < matrix_n; i++){
            free(row[i]);
        }
        
        free(row);
        free(result);
    }
}

//нахождение ненулевого элемента
int find_nonezero_elem(int c){
    for(int r = c; r < matrix_n; r++){
        if(fabs(*(row[r] + c)) >= EPS){
            return r;
        }
    }
    return -1;
}

//смена строк местами
void swap_row(int row1, int row2){
    double *tmp = row[row1];
    row[row1] = row[row2];
    row[row2] = tmp;
}

//нормализация строки
void normalize_row(int r_change, int r_sub){
    double koef_div = *(row[r_sub] + r_sub);
    double koef_mul = *(row[r_change] + r_sub);
    for(int i = r_sub; i < matrix_n+1; i ++){
        *(row[r_change] + i) = *(row[r_change] + i) - koef_mul * *(row[r_sub] + i) / koef_div;
    }
}

int print_to_file(char* file_name){
    FILE *f_out;
    if ((f_out = fopen(file_name, "w")) == NULL){
      printf("Не удалось создать выходной файл \n");
      return 5;
    }
    if(flag_no_solut == 1){
        fprintf(f_out, "no solution\n");
    }else if(rank_flag == 1){
        fprintf(f_out, "many solutions\n");
    }
    else{
        for(int r = 0; r < matrix_n; r++){
            fprintf(f_out, "%f\n", result[r]);
        }
    }
    fclose(f_out);
    return 0;
}


int main(int argc, char **argv){
    
    int return_code = 0;
    
    while (1) {
        if(argc != 3){
            printf("Неверное кол-во аргументов \nИспользование: lab3 <имя_входного файла> <имя_выходного_файла>");
            return_code = 2;
            break;
        }
        
        if ((f_in = fopen(argv[1], "r")) == NULL){
            printf("Не удалось открыть входной файл \n");
            return_code = 1;
            break;
        }
        
        //иициализация матрицы и переменных
        matrix_n = get_N();
        return_code = memory_init();
        
        if(return_code > 0){
            printf("Не удалось выделить память");
            break;
        }
        
        int tmp = read_input_data();
        fclose(f_in);
        if(tmp == - 1){
            printf("Не хватает строк матрицы \n");
            return_code = 3;
            break;
        } else if(tmp >0){
            printf("Неверное кол-во значений в строке %i матрицы \n", tmp);
            return_code = 4;
            break;
        }
            
        print_matrix();
        
        //прямой обход к треугольному виду
        for(int i = 0; i < matrix_n; i++){
            int found_row = find_nonezero_elem(i);
            if(found_row >= 0){
                swap_row(found_row, i);
                for(int j = i + 1; j < matrix_n; j++){
                    normalize_row(j,i);
                }
            }
            else {
                rank_flag = 1;//ранг матрицы меньше н
                continue;
            }
        }
        
        //обратный обход
        for(int r = matrix_n-1; (r >= 0) && (flag_no_solut == 0); r--){
        
            result[r] = *(row[r]+matrix_n);
            for(int c = r+1; c < matrix_n; c++){
                result[r] -= (*(row[r] + c) * result[c]);
            }
            if(fabs(*(row[r] + r)) >= EPS){
                result[r] /= *(row[r] + r);
            }
            else if(fabs(result[r]) >= EPS){
                flag_no_solut = 1;//нет решений
                break;
            }
            else{
                rank_flag = 1;//много решений
            }
        
        }
    
    

#if DEBUGLEVEL == 1
        if(flag_no_solut == 1){
            printf("no solutions \n");
        }else if(rank_flag == 1){
            printf("many solutions \n");
        }
        else{
            print_result();
        }
#endif
    
        return_code = print_to_file(argv[2]);
        
        
        break;
    }
    memory_free();

    return return_code;
}

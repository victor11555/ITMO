//шаблонизатор
"use strict"

const fs = require('fs');
let template_file = process.argv[2];
let subs_file = process.argv[3];
let subs_data = "";
let template_text ="";
let variable_map = new Map();

fs.access(template_file, function (error){
    if(error){
        console.log("Файл шаблонов не существует");
        process.exit();
    }
    else {
        fs.access(subs_file, function (error){
            if(error){
                console.log("Файл подстановок не существует");
                process.exit();
            }
            else {
                template_text = fs.readFileSync(template_file, "UTF-8");
                subs_data = fs.readFileSync(subs_file, "UTF-8");
                subs_data.split("\n").forEach(function (value){
                    let subs_parts = value.split("=");
                    let variable = subs_parts[0].trim();
                    subs_parts.splice(0,1);
                    let content = subs_parts.join("").trim();
                    variable_map.set(variable, content);
                });
                let start_var_index = template_text.indexOf("$");
                while (start_var_index !== -1){
                    let end_var_index = template_text.indexOf("$", start_var_index+1);
                    if(end_var_index === -1){
                        end_var_index = template_text.indexOf("\n", start_var_index+1);
                    }
                    if(end_var_index === -1){
                        end_var_index = template_text.length -1;
                    }

                    if(end_var_index !== -1){
                        let cur_var = template_text.substring(start_var_index+1, end_var_index);
                        while (!variable_map.has(cur_var)){
                            cur_var = cur_var.substr(0, cur_var.length - 1);
                            if (cur_var === ""){
                                console.log("Переменная не найдена в подстановках");
                                process.exit();
                            }
                        }
                        template_text = template_text.replace(("$"+cur_var), variable_map.get(cur_var));
                        // console.log(template_text);
                        // console.log("$"+cur_var, variable_map.get(cur_var));

                    }

                    fs.writeFileSync("output.txt", template_text);
                    start_var_index = template_text.indexOf("$");
                }

            }
        })
    }
})
//bbcode
"use strict"
const fs = require('fs');
let expression = process.argv[2];
fs.access(expression, function (error){
    if(!error){
        expression = fs.readFileSync(expression).toString();
    }
    console.log(toHTML(expression));
})

function Node(data){
    this.pair = true;
    if(Array.isArray(data)){
        this.children = data;
    }
    else {
        this.content = data;
    }
    this.toHTML= function (){
        let result = "<" + this.tag;
        if(this.hasOwnProperty("attr")){
            result += " " + this.attr;
        }
        if(!this.pair) {
            return result + "/>";
        }
        let HTMLcode = "";
        if(this.hasOwnProperty("children")){
            this.children.forEach(function (child){
                HTMLcode += child.toHTML();
            })
        }
        else if(this.hasOwnProperty("content")) {
            HTMLcode = this.content;
        }
        return result + ">" + HTMLcode + "</" + this.tag + ">";
    }
}

function Paragraph(data){
    Node.apply(this, arguments);
    this.tag = "p";
}

function Bold(data){
    Node.apply(this, arguments);
    this.tag = "strong";
}

function Italic(data){
    Node.apply(this, arguments);
    this.tag = "em";
}

function Underline(data){
    Node.apply(this, arguments);
    this.tag = "span";
    this.attr = "style='text-decoration:underline;'";
}

function Through(data){
    Node.apply(this, arguments);
    this.tag = "del";
}

function Code(data){
    Node.apply(this, arguments);
    this.tag = "code";
    this.attr = "style='white-space:pre;'"
}

function Url(data, href){
    Node.apply(this, arguments);
    if(!href){
        href = data;
    }
    this.attr = "href='"+href+"'";
    this.tag = "a";
}

function Image(data){
    Node.apply(this, arguments);
    this.pair = false;
    this.attr = "src='"+data+"'";
    delete this.content;
    this.tag = "img";
}

function Quote(data){
    Node.apply(this, arguments);
    this.tag = "blockquote";
}

function Size(data, size){
    Node.apply(this, arguments);
    if(!isNaN(parseInt(size[size.length-1]))){
        size += "px";
    }
    this.attr = "style='font-size:"+size+";'"
    this.tag = "span" ;
}

function Color(data, color){
    Node.apply(this, arguments);
    this.attr = "style='color:"+color+";'";
    this.tag = "span";
}

function List(data){
    Node.apply(this, arguments);
    this.tag = "ul";
    let list = this;
    if(!Array.isArray(data)) {
        this.children = [];
        this.content.split("* ").forEach(function (text) {
            if(text === "" || /^\s*$/.test(text)){
                return ;
            }
            list.children.push(new ListItem(text));
        })
        delete this.content;
    }
}

function ListItem(data){
    Node.apply(this, arguments);
    this.tag = "li";
}

function NumericList(data){
    Node.apply(this, arguments);
    this.tag = "ol";
}

function Table(data){
    Node.apply(this, arguments);
    this.tag = "table";
}

function Row(data){
    Node.apply(this, arguments);
    this.tag = "tr";
}

function Cell(data){
    Node.apply(this, arguments);
    this.tag = "td";
}

let tags_map = new Map([
    ["b", Bold],
    ["i", Italic],
    ["u", Underline],
    ["s", Through],
    ["url", Url],
    ["img", Image],
    ["quote", Quote],
    ["code", Code],
    ["size", Size],
    ["color", Color],
    ["list", List],
    ["ul", List],
    ["li", ListItem],
    ["ol", NumericList],
    ["table", Table],
    ["tr", Row],
    ["td", Cell],
]);

function BBCodeParser(expression){
    let finish_tag_pos = 0;
    let tag_value = "";
    let finish_tag_data_pos = 0;
    let start_index = 0;
    let node_list = [];
    while (start_index < expression.length) {
        let start_tag_pos = expression.indexOf("[", start_index);
         if (start_tag_pos !== -1) {
            if(start_tag_pos - start_index > 1 && !(/^\s*$/.test(expression.substring(start_index+1, start_tag_pos)))){
             node_list.push(new Paragraph(expression.substring(start_index, start_tag_pos)));
            }
            finish_tag_pos = expression.indexOf("]", start_index);
            if (finish_tag_pos === -1) {
                console.log("Ошибка синтаксиса");
                process.exit();
            }
            tag_value = expression.substring(start_tag_pos + 1, finish_tag_pos);
            let param = "";
            if(tag_value.indexOf("=") !== -1){
                let tag_data = tag_value.split("=");
                tag_value = tag_data[0];
                param = tag_data[1];
            }
            if (!tags_map.has(tag_value)) {
                console.log("Неправльный тег: ", tag_value);
                process.exit();
            }
            finish_tag_data_pos = expression.indexOf("[/" + tag_value + "]", finish_tag_pos + 1);
            let parsed_data = BBCodeParser(expression.substring(finish_tag_pos + 1, finish_tag_data_pos));
            let node;
            if(param){
                node = new (tags_map.get(tag_value))(parsed_data, param);
            }
            else {
                node = new (tags_map.get(tag_value))(parsed_data);
            }
            node_list.push(node);
        }
        else {
            if (node_list.length === 0){
                return expression;
            }
            else {
                node_list.push(new Paragraph(expression.substr(start_index)));
                return node_list;
            }
        }
        start_index = finish_tag_data_pos + 3 + tag_value.length;
    }
    return node_list;
}

function toHTML(expression){
    let list = BBCodeParser(expression);
    let result_str = "";
    list.forEach(function (node){
        result_str += "<div>" + node.toHTML() + "</div>\n";
    })//<div> быстро убирается |
    return result_str;
}

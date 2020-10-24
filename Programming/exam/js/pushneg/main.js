//pushneg
"use strict"
const expression = process.argv[2];
let pos = 0;

function skipBlank() {
    while ((pos+1 < expression.length)
        && (expression[pos] === ' '
            || expression[pos] ==='\t'
            ||  expression[pos] ==='\n'
            ||  expression[pos] ==='\r'
        )
        ) {++pos;}
}

function And (first, second){
    this.left = first;
    this.right = second;
    this.toString = function (){
        return this.left.toString() + "&" + this.right.toString();
    }
}

function Or (first, second){
    this.left = first;
    this.right = second;
    this.toString = function (){
        return this.left.toString() + "|" + this.right.toString();
    }
}

function Not (exp){
    this.alone = exp;
    this.toString = function (){
        return "~" + this.alone.toString();
    }
}

function Constant(con){
    this.value = parseInt(con);
    this.toString = function (){
        return this.value.toString();
    }
    Object.defineProperty(this, "getvalue", {
        get: function () {
            return Boolean(this.value);
        }
    })
}

function Variable(v){
    this.name = v;
    this.toString = function (){
        return this.name.toString();
    }
}

function parsExpr(expression){
    let lterm = parseTerm(expression);
    skipBlank();
    while ((pos+1 < expression.length) && (expression[pos] === "&")){
        ++pos;
        let rterm = parseTerm(expression);
        lterm = new And(lterm, rterm);
        skipBlank();
    }
    return lterm;
}

function parseTerm(expression){
    let lfactor = parseFactor(expression);
    skipBlank();
    while ((pos+1 < expression.length) && (expression[pos] === "|")){
        ++pos;
        let rfactor = parseFactor(expression);
        lfactor = new Or(lfactor, rfactor);
        skipBlank();
    }
    return lfactor;
}

function parseFactor(expression){
    skipBlank();
    if(expression[pos]=== "~"){
        ++pos;
        return new Not(parseFactor(expression));
    }
    else if(expression[pos]=== "("){
        skipBlank();
        ++pos;
        let tmp = parsExpr(expression);
        skipBlank();
        if(expression[pos] === ")"){
            ++pos;
            return tmp
        }
        else{
            throw new SyntaxError("Wrong brackets: " + expression);
        }
    }
    else if(expression[pos] === "1"){
        ++pos;
        return  new Constant(1);
    }
    else if(expression[pos] === "0"){
        ++pos;
        return  new Constant(0);
    }
    else if(expression[pos] >= "a" && expression[pos] <= "z"){
        return  new Variable(expression[pos++]);
    }
    else{
        throw new SyntaxError("Wrong Factor: " + expression + pos);
    }
}

function check_expr (root){
    if(root instanceof Not){
        if(root.alone instanceof Constant){
            root = new Constant(Number(!root.alone.getvalue));
        }

        else if(root.alone instanceof Not){
            root = root.alone.alone
            if(root instanceof And || root instanceof Or){
                root.left = check_expr(root.left);
                root.right = check_expr(root.right);
            }
            else if(root instanceof Not){
                root = check_expr(root);
            }
        }

        else if(root.alone instanceof And){
            root = new Or(new Not(root.alone.left), new Not(root.alone.right));
            root.left =check_expr(root.left);
            root.right = check_expr(root.right);
        }

        else if (root.alone instanceof Or){
            root = new And(new Not(root.alone.left), new Not(root.alone.right));
            root.left = check_expr(root.left);
            root.right = check_expr(root.right);
        }
    }
    else if(root instanceof And){
        root.left = check_expr(root.left);
        root.right = check_expr(root.right);
    }
    else if(root instanceof Or){
        root.left = check_expr(root.left);
        root.right = check_expr(root.right);
    }
    return root;
}

function toDNF(expression){
    let root = parsExpr(expression);
    return check_expr(root).toString();
}

console.log(toDNF(expression));

/*
   < expression > ::= < term > and < expression >   |     < term >

   < term > ::=    < factor > or < term >     |    < factor >

   < factor > ::=    not [  (< expression >)     |        < const >     |    < variable > ]
    - может не/быть в начале всего того что в [ ... | ... | ... ]
*/
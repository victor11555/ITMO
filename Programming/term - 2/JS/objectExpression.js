"use strict";

function Variable(str){
	this.str = str;
	this.n = "xyz".indexOf(str);

}
Variable.prototype.evaluate = function(...args) { return args[this.n]; }
Variable.prototype.toString = function() { return this.str; }

function Negate(expression){
    this.expression = expression;
}
Negate.prototype.evaluate = function(x, y, z) {
    return -this.expression.evaluate(x, y, z);
}
Negate.prototype.toString = function() {
    return this.expression + " negate";
}


function Const(one) {
    this.one = one;
}
Const.prototype.evaluate = function(x, y, z){
    return this.one;
}
Const.prototype.toString = function() {
    return this.one.toString();
}

function BinaryOperation(left, right, sign, f){
    this.left = left;
    this.right = right;
    this.sign = sign;
    this.f = f;
}
BinaryOperation.prototype.evaluate = function(x, y, z){
        return this.f(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z));
}
BinaryOperation.prototype.toString = function(){
        return this.left.toString() + " " + this.right.toString() + " " + this.sign;
}

function makeBin(sign, f) {
    return function(left, right){
        return new BinaryOperation(left, right, sign, f);
    }
}

const Add = makeBin("+", (a, b) => a + b);
const Subtract = makeBin("-", (a, b) => a - b);
const Multiply = makeBin("*", (a, b) => a * b);
const Divide = makeBin("/", (a, b) => a / b);

function Min3(left, middle, right){
	this.left = left;
	this.middle = middle;
	this.right = right;

}
Min3.prototype.evaluate = function(x, y, z) {
		return Math.min(this.left.evaluate(x, y, z), this.middle.evaluate(x, y, z), this.right.evaluate(x, y, z));
}
Min3.prototype.toString = function() {
           return this.left.toString() + " " + this.middle.toString() + " " + this.right.toString() + " min3" ;
}

function Max5(left, middle, middle2, middle3, right){
    this.left = left;
	this.middle = middle;
	this.middle2 = middle2;
	this.middle3 = middle3;
	this.right = right;

}
Max5.prototype.evaluate = function(x, y, z){
		return Math.max(this.left.evaluate(x, y, z), this.middle.evaluate(x, y, z), this.middle2.evaluate(x, y, z), this.middle3.evaluate(x, y, z), this.right.evaluate(x, y, z));
}
Max5.prototype.toString = function() {
        return this.left.toString() + " " + this.middle.toString() + " " + this.middle2.toString() + " " + this.middle3.toString() + " " + this.right.toString() + " max5" ;
}

function ArcTan(expression) {
    this.expression = expression;
}
ArcTan.prototype.evaluate = function(x, y, z) {
    return Math.atan(this.expression.evaluate(x, y, z));
}
ArcTan.prototype.toString = function() {
    return this.expression + " atan";
}

function Exp(expression) {
    this.expression = expression;
}
Exp.prototype.evaluate = function(x, y, z) {
    return Math.exp(this.expression.evaluate(x, y, z));
}
Exp.prototype.toString = function() {
    return this.expression + " Exp";
}

function prefix(expr){

}

function parsePrefix(expression){

}




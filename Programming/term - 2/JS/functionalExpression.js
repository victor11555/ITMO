"use strict";

const cnst = val => (x, y, z) => val;

const variable = name => {
    let n = "xyz".indexOf(name);
    return (...args) => args[n];
} 

const UnaryOperation = f => alone => (x, y, z) => f(alone(x, y, z));

const cube = UnaryOperation(alone => alone * alone * alone);
const cuberoot = UnaryOperation(alone => Math.cbrt(alone));
const negate = UnaryOperation(alone => -alone);

const BinaryOperation = f => (left, right) => (x, y, z) => f(left(x, y, z), right(x, y, z));


const add = BinaryOperation((left, right) => left + right);
const subtract = BinaryOperation((left, right) => left - right);
const multiply = BinaryOperation((left, right) => left * right);
const divide = BinaryOperation((left, right) => left / right);








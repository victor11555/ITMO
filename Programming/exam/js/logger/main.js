"use strict";
//logger
const fs = require('fs');
let priorityLevels = new Map([
    ["Debug", 0],
    ["Info", 1],
    ["Warning", 2],
    ["Error", 3]
])
const priority = "Debug";

function Message(text, level){
    this.text = text;
    this.level = level;
}

function Logger(level) {
    this.level = level;
    this.format = "[{level}] {date}: {message}";
    this.log = function (message) {
        if(priorityLevels.get(message.level) < priorityLevels.get(this.level)) {
            return ;
        }
        let date = new Date();
        let log_date = this.format.replace("{level}", message.level).replace("{date}", date).replace("{message}", message.text);
        return this._writee(log_date);
    };
}

function ConsoleLogger(level) {
    Logger.apply(this, arguments);
    this._writee = function(message) {
        console.log(message);
    }
}

function HTMLLogger(level) {
    Logger.apply(this, arguments);
    this.format = "<div style='padding: 5px; border: 1px gray solid'> <p style='font-size: 14px; font-weight: bold'>{level}</p> <p style='font-style: italic'>{date}:</p> <p>{message}</p></div>";
    this._writee = function(message) {
        document.write(message);
    }
    this._date = function(message) {
        const date = new Date();
        document.write(message +" : "+ date);
    }
}

function FileLogger(level){
    Logger.apply(this, arguments);
    this._writee = function(message) {
        fs.writeFileSync("main.log", message + "\n", {flag:"a"});
    }
}

function CompositeLogger(level) {
    Logger.apply(this, arguments);
    this.loggers_list = [];
    this.append = function (logger){
        this.loggers_list.push(logger);
    }
    this.log = function(message) {
        for(let logger of this.loggers_list){
            logger.log(message);
        }
    }
}

module.exports.FileLogger = FileLogger;
module.exports.Message = Message;

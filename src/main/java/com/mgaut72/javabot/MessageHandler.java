package com.mgaut72.javabot;

import com.mgaut72.javabot.JavaBot;

abstract class MessageHandler {

    JavaBot bot;

    public MessageHandler(JavaBot bot){
        this.bot = bot;
    }

    abstract void handle(String message);
}




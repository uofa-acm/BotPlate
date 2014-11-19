package com.mgaut72.ircbot;

import com.mgaut72.ircbot.IRCBot;
import com.mgaut72.IRCProtocol.Message;

abstract class MessageHandler {

    IRCBot bot;

    public MessageHandler(IRCBot bot){
        this.bot = bot;
    }

    abstract void handle(Message message);
}




package com.mgaut72.ircbot;

import java.util.*;
import java.io.IOException;

import com.mgaut72.ircbot.IRCBot;
import com.mgaut72.IRCProtocol.Message;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String [] args){
        String host = "irc.freenode.net";
        String channel = "#uofa-acm";
        String nick = "exampleBot";
        int port = 6667;
        IRCBot bot = new IRCBot(host, channel, nick, port);

        bot.addHandler(new MessageHandler(bot){
            void handle(Message message){
                if(message.getTrailing().contains("hello " + bot.getNick())){
                    bot.sendPrivmsg(bot.getChannel(), "Hey!");
                }
            }
        });

        bot.run();

    }
}

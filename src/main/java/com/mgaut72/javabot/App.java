package com.mgaut72.javabot;

import com.mgaut72.javabot.JavaBot;
import java.util.*;
import java.io.IOException;

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
        JavaBot bot = new JavaBot(host, channel, nick, port);

        bot.addHandler(new MessageHandler(bot){
            void handle(String message){
                if(message.contains("hello " + bot.getNick())){
                    bot.sendPrivmsg(bot.getChannel(), "Hey!");
                }
            }
        });

        try {
            bot.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package com.mgaut72.ircbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;

import com.mgaut72.IRCProtocol.Message;

public class IRCBot {

    // IRC variables
    private String nick;
    private String host;
    private String channel;
    private int port;

    // connection/communication objects
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    // message handlers
    private List<MessageHandler> handlers;

    public IRCBot(String host, String channel, String nick, int port){
        this.host = host;
        this.channel = channel;
        this.nick = nick;
        this.port = port;

        handlers = new ArrayList<MessageHandler>();

        //Need to respond "PONG" when pinged or server drops us
        MessageHandler pingHandler = new MessageHandler(this) {
            void handle(Message message){
                if(message.getCommand().equals("PING"))
                    bot.writeLine("PONG :" + message.getTrailing());
            }
        };
        handlers.add(pingHandler);
    }

    public void addHandler(MessageHandler mh){
        handlers.add(mh);
    }

    protected void writeLine(String message) {
        try{
            if (!message.endsWith("\r\n"))
                message = message + "\r\n";

            System.out.println(">>> " + message);
            out.write(message);
            out.flush();
        }
        catch(Exception e){
            System.err.println("Could not write message : " + message);
        }
    }

    protected void sendPrivmsg(String target, String message){
        writeLine("PRIVMSG " + target + " :" + message);
    }

    public void run() {
        try{
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writeLine("NICK :" + nick);
            writeLine("USER " + nick + " * * :" + nick);
            writeLine("JOIN :" + channel);

            String line = null;

            while((line = in.readLine()) != null) {
                System.out.println(">>> " + line);
                Message msg = new Message(line);

                for (MessageHandler mh : handlers){
                    mh.handle(msg);
                }
            }
        }
        catch(Exception e){
            System.err.println("Cannot connect and operate bot");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String getNick(){ return this.nick; }
    public String getChannel(){ return this.channel; }
    public String getHost(){ return this.host; }
    public int getPort(){ return this.port; }
}

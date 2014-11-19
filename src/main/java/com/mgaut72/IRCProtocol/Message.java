package com.mgaut72.IRCProtocol;

import java.util.*;

public class Message {

    private String prefix;
    private String command;
    private List<String> parameters;
    private String trailing;

    public Message(String rawMessage){
        prefix = "";
        command = "";
        trailing = "";
        parameters = new ArrayList<String>();

        /*
         * parse optional prefix.
         * we know we have a prefix when there is a ':' leading the message
         */
        if(rawMessage.startsWith(":")){
            // prefix goes until the first space character
            int prefixEnd = rawMessage.indexOf(' ');
            prefix = rawMessage.substring(1,prefixEnd);
            rawMessage = rawMessage.substring(prefixEnd+1);
        }

        /*
         * parse mandatory command
         * command is either [a-zA-Z]+
         * or [0-9]{3}
         * followed by a space
         */
        int commandEnd = rawMessage.indexOf(' ');
        if(commandEnd < 0){
            commandEnd = rawMessage.length();
        }
        command = rawMessage.substring(0,commandEnd);

        // see if we are out of input
        if(commandEnd == rawMessage.length()){
            validate();
            return;
        }

        // skip over the whitespace before params
        rawMessage = rawMessage.substring(commandEnd);

        /* from here, there are optionally up to 14 parameters
         * followed by the "trailing" feild
         */

        // if we have ":" this indicates the start of the trailing section
        if(rawMessage.contains(" :")){
            String[] parts = rawMessage.split(" :");
            trailing = parts[1];

            if (!parts[0].isEmpty()){
                String params = parts[0].substring(1);
                for (String s : params.split(" ")){
                    if (!s.isEmpty()){
                        parameters.add(s);
                    }
                }
            }
        }
        else {
            /* if there is no " :", there is either no trailing,
             * or 14 params then trailing
             */
            String[] parts = rawMessage.substring(1).split(" ");
            for(int i=0; i<parts.length; i++){
                if(i < 14){
                    parameters.add(parts[i]);
                }
                else{
                    trailing += parts[i];
                }
            }
        }
        validate();
    }

    private void validate(){
        //TODO
    }

    public String getPrefix(){
        return prefix;
    }

    public String getCommand(){
        return command;
    }

    public String getTrailing(){
        return trailing;
    }

    public List<String> getParameters(){
        return parameters;
    }
}

package com.mgaut72.IRCProtocol;

import java.util.*;

class Message {

    private String prefix;
    private String command;
    private List<String> parameters;
    private String trailing;

    public Message(String rawMessage){
        prefix = "";
        command = "";
        trailing = "";
        parameters = new ArrayList<String>();

        int currIdx = 0; // this will be the index of the first unparsed char

        /*
         * parse optional prefix.
         * we know we have a prefix when there is a ':' leading the message
         */
        if(rawMessage.startsWith(":")){
            // prefix goes until the first space character
            currIdx++; // skip over ':'
            int prefixEnd = rawMessage.indexOf(' ', currIdx);
            prefix = rawMessage.substring(currIdx, prefixEnd);

            currIdx = prefixEnd + 1;
        }

        /*
         * parse mandatory command
         * command is either [a-zA-Z]+
         * or [0-9]{3}
         * followed by a space
         */
        int commandEnd = currIdx;
        char c = rawMessage.charAt(commandEnd);
        if(Character.isDigit(c)){
            commandEnd += 3;
        }
        else{
            while(Character.isAlphabetic(c)){
                commandEnd++;
                c = new Character(rawMessage.charAt(commandEnd));
            }
        }
        command = rawMessage.substring(currIdx,commandEnd);
        currIdx = commandEnd;


        // if we make it here, we have at least 1 parameter
        int numParams = 0;
        while(true){

            // message ends with "\r\n"
            if(rawMessage.startsWith("\r\n", currIdx))
                return;

            currIdx++; // skip over ' '

            // maximal number of parameters, must now be parsing trailing
            if(numParams == 14){
                // when numParams is max, ":" is optional
                if(rawMessage.startsWith(":", currIdx)){
                    currIdx++;
                }

                // trailing is the rest, except for the \r\n at the end
                trailing = rawMessage.substring(currIdx, rawMessage.length() -2);
                return;
            }
            // found a ':' so must be parsing a trailing
            else if(rawMessage.startsWith(":", currIdx)){
                currIdx++;

                // trailing is the rest, except for the \r\n at the end
                trailing = rawMessage.substring(currIdx, rawMessage.length() -2);
                return;
            }
            // parsing another parameter
            else{
                int paramEnd = rawMessage.indexOf(' ', currIdx);

                // hit end of params with no trailing
                if(paramEnd < 0){
                    paramEnd = rawMessage.length()-2;
                }

                parameters.add(rawMessage.substring(currIdx, paramEnd));
                currIdx = paramEnd;
                numParams++;
            }
        }
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

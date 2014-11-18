package com.mgaut72.IRCProtocol;

import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.mgaut72.IRCProtocol.Message;

/**
 * Unit test for IRC Message Parser
 */
public class MessageTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MessageTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( MessageTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testSimpleMessage() {
        String raw;
        Message msg;

        raw = ":<prefix> command params :<trailing>\r\n";
        msg = new Message(raw);
        assertEquals("<prefix>", msg.getPrefix());
        assertEquals("command", msg.getCommand());
        assertEquals(1, msg.getParameters().size());
        assertEquals("params", msg.getParameters().get(0));
        assertEquals("<trailing>", msg.getTrailing());
    }

    public void testNoPrefix(){
        String raw;
        Message msg;

        raw = "command params :<trailing>\r\n";
        msg = new Message(raw);
        assertEquals("", msg.getPrefix());
        assertEquals("command", msg.getCommand());
        assertEquals(1, msg.getParameters().size());
        assertEquals("params", msg.getParameters().get(0));
        assertEquals("<trailing>", msg.getTrailing());
    }

    public void testNoParams(){
        String raw;
        Message msg;

        raw = ":<prefix> command :<trailing>\r\n";
        msg = new Message(raw);
        assertEquals("<prefix>", msg.getPrefix());
        assertEquals("command", msg.getCommand());
        assertEquals(0, msg.getParameters().size());
        assertEquals("<trailing>", msg.getTrailing());
    }

    public void testNoPrefixNoParams(){
        String raw;
        Message msg;

        raw = "command :<trailing>\r\n";
        msg = new Message(raw);
        assertEquals("", msg.getPrefix());
        assertEquals("command", msg.getCommand());
        assertEquals(0, msg.getParameters().size());
        assertEquals("<trailing>", msg.getTrailing());
    }

    public void testNoPrefixNoParamsNoTrailing(){
        String raw;
        Message msg;

        raw = "command\r\n";
        msg = new Message(raw);
        assertEquals("", msg.getPrefix());
        assertEquals("command", msg.getCommand());
        assertEquals(0, msg.getParameters().size());
        assertEquals("", msg.getTrailing());
    }

    public void testNumericCommand(){
        String raw;
        Message msg;

        raw = "000\r\n";
        msg = new Message(raw);
        assertEquals("", msg.getPrefix());
        assertEquals("000", msg.getCommand());
        assertEquals(0, msg.getParameters().size());
        assertEquals("", msg.getTrailing());
    }

    public void testParamList(){
        String raw;
        Message msg;

        raw = ":<prefix> command param1 param2 param3 :<trailing>\r\n";
        msg = new Message(raw);

        assertEquals("<prefix>", msg.getPrefix());
        assertEquals("command", msg.getCommand());
        assertEquals(3, msg.getParameters().size());
        assertEquals("param1", msg.getParameters().get(0));
        assertEquals("param2", msg.getParameters().get(1));
        assertEquals("param3", msg.getParameters().get(2));
        assertEquals("<trailing>", msg.getTrailing());
    }

    public void testMaxParamListExplicitTrailing(){
        String raw;
        Message msg;

        raw = ":<prefix> command "
            + "param1 param2 param3 param4 param5 param6 "
            + "param7 param8 param9 param10 param11 param12 param13 param14 "
            + ":<trailing>\r\n";
        msg = new Message(raw);

        assertEquals("<prefix>", msg.getPrefix());
        assertEquals("command", msg.getCommand());
        assertEquals(14, msg.getParameters().size());
        assertEquals("param1", msg.getParameters().get(0));
        assertEquals("param2", msg.getParameters().get(1));
        assertEquals("param3", msg.getParameters().get(2));
        assertEquals("param4", msg.getParameters().get(3));
        assertEquals("param5", msg.getParameters().get(4));
        assertEquals("param6", msg.getParameters().get(5));
        assertEquals("param7", msg.getParameters().get(6));
        assertEquals("param8", msg.getParameters().get(7));
        assertEquals("param9", msg.getParameters().get(8));
        assertEquals("param10", msg.getParameters().get(9));
        assertEquals("param11", msg.getParameters().get(10));
        assertEquals("param12", msg.getParameters().get(11));
        assertEquals("param13", msg.getParameters().get(12));
        assertEquals("param14", msg.getParameters().get(13));
        assertEquals("<trailing>", msg.getTrailing());
    }

    public void testMaxParamListImplicitTrailing(){
        String raw;
        Message msg;

        raw = ":<prefix> command "
            + "param1 param2 param3 param4 param5 param6 "
            + "param7 param8 param9 param10 param11 param12 param13 param14 "
            + "<trailing>\r\n";
        msg = new Message(raw);

        assertEquals("<prefix>", msg.getPrefix());
        assertEquals("command", msg.getCommand());
        assertEquals(14, msg.getParameters().size());
        assertEquals("param1", msg.getParameters().get(0));
        assertEquals("param2", msg.getParameters().get(1));
        assertEquals("param3", msg.getParameters().get(2));
        assertEquals("param4", msg.getParameters().get(3));
        assertEquals("param5", msg.getParameters().get(4));
        assertEquals("param6", msg.getParameters().get(5));
        assertEquals("param7", msg.getParameters().get(6));
        assertEquals("param8", msg.getParameters().get(7));
        assertEquals("param9", msg.getParameters().get(8));
        assertEquals("param10", msg.getParameters().get(9));
        assertEquals("param11", msg.getParameters().get(10));
        assertEquals("param12", msg.getParameters().get(11));
        assertEquals("param13", msg.getParameters().get(12));
        assertEquals("param14", msg.getParameters().get(13));
        assertEquals("<trailing>", msg.getTrailing());
    }
}

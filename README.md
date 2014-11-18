BotPlate
===== 
<img src=botplate.png height=256 width=256></img>

A Java IRC bot template-ish thing.

IRCBot.java
-----------

This contains the core functionality of the IRC bot.
The constructor takes as arguments:
  * IRC Server
  * Channel
  * Bot Nickname
  * Port to connect to the server

It contains functions to write messages to a channel/person, as
well as to write generic IRC commands.

Most importantly, it contains a collection of `MessageHandler` objects.
The collection of `MessageHandler`s are initialized to contain a ping
handler.  On receiving the `PING` message from the server, this handler
responds with a corresponding `PONG` message so the server does not drop
our connection.

There exists an `addHander` function which allows a user to extend their
bot with any `MessageHandler` object.

When a bot receives a message, it will iterate over all its `MessageHandler`s
and call the `handle` function on the message.

MessageHandler.java
-------------------

This is an abstract class with a concrete constructor, which
takes the `IRCBot` it will be handling messages for as an argument, and
stores it as an instance variable called `bot`.

There is exactly one abstract method:

```
abstract void handle(String message);
```

Create `MessageHandler`s and add them to instances of `IRCBot`s to augment
their functionality.

App.java
--------

An example `IRCBot` implementation, which contains a `MessageHandler`
that when it hears "hello exampleBot" in the channel, it responds with "Hey!"

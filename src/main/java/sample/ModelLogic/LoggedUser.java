package sample.ModelLogic;

import java.util.LinkedList;
import java.util.List;

public class LoggedUser {

    /**
     * getter to the user's name
     * @return the logged user's name
     */
    public String getName() {
        return name;
    }

    /**
     * getter to the user's user name
     * @return the logged user's  username
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * getter to the user's messages
     * @return the logged user's  messages
     */
    public List<Messege> getMessages() {
        return messages;
    }

    /**
     * setter to the user's messages
     * @param messages the new message list that will be set
     */
    public void setMessages(List<Messege> messages) {
        this.messages = messages;
    }

    String name;
    String UserName;
    List<Messege> messages;

    /**
     * constractor
     * @param name
     * @param userName
     */
    public LoggedUser(String name, String userName) {
        this.name = name;
        UserName = userName;
        messages = new LinkedList<>();
    }

    /**
     * checks if there are messages waiting
     * @return true if there are no messages
     */
    public boolean isMailboxEmpty()
    {
        return messages.size() == 0;
    }

    /**
     * adds a message object to the messages list
     * @param message
     */
    public void addToMailBox(Messege message)
    {
        messages.add(message);
    }

    /**
     * count the amoint of messages for the user
     * @return integer value for tne number of messages in the user mailbox
     */
    public int MessagesCount()
    {
        return messages.size();
    }
}

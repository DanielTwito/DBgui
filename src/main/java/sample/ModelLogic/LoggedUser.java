package sample.ModelLogic;

import java.util.ArrayList;
import java.util.List;

public class LoggedUser {
    public String getName() {
        return name;
    }

    public String getUserName() {
        return UserName;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    String name;
    String UserName;
    List<String> messages;

    public LoggedUser(String name, String userName) {
        this.name = name;
        UserName = userName;
        messages = new ArrayList<>();
    }

    public boolean isMailboxEmpty()
    {
        return messages.size() == 0;
    }

    public void addToMailBox(String message)
    {
        messages.add(message);
    }

    public int MessagesCount()
    {
        return messages.size();
    }
}
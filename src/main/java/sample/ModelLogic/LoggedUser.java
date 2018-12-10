package sample.ModelLogic;

import java.util.LinkedList;
import java.util.List;

public class LoggedUser {
    public String getName() {
        return name;
    }

    public String getUserName() {
        return UserName;
    }

    public List<Messege> getMessages() {
        return messages;
    }

    public void setMessages(List<Messege> messages) {
        this.messages = messages;
    }

    String name;
    String UserName;
    List<Messege> messages;

    public LoggedUser(String name, String userName) {
        this.name = name;
        UserName = userName;
        messages = new LinkedList<>();
    }

    public boolean isMailboxEmpty()
    {
        return messages.size() == 0;
    }

    public void addToMailBox(Messege message)
    {
        messages.add(message);
    }

    public int MessagesCount()
    {
        return messages.size();
    }
}

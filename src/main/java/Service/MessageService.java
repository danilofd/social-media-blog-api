package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    
    public Message newMessage(Message message){
        try {
            if(accountDAO.getAccountById(message.getPosted_by()) != null){
                if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255){
                    return messageDAO.newMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public List<Message> listMessages(){
        try {
            return messageDAO.listMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Message getMessageById(int id){
        try {
            return messageDAO.getMessageById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message deleteMessageById(int id){
        try {
            Message message = messageDAO.getMessageById(id);
            if(message != null){
                messageDAO.deleteMessageById(id);
                return message;
            }else{
                return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message updateMessage(int message_id, String message_text){
        try {
            if(messageDAO.getMessageById(message_id) != null && !message_text.isBlank() && message_text.length() < 255){
                messageDAO.updateMessage(message_id, message_text);
                return messageDAO.getMessageById(message_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> listMessagesByAccountId(int account_id){
        try {
            return messageDAO.listMessagesByAccountId(account_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}

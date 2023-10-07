package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    ObjectMapper mapper;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::registerAccount);
        app.post("login", this::login);
        app.post("messages", this::newMessage);
        app.get("messages", this::listMessage);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::listMessageByAccountId);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerAccount(Context ctx) throws JsonProcessingException {
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.registerAccount(account);
        if(newAccount != null){
            ctx.json(mapper.writeValueAsString(newAccount));
        }else{
            ctx.status(400);
        }
    }

    private void login(Context ctx) throws JsonProcessingException{
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountLoged = accountService.login(account);
        if(accountLoged != null){
            ctx.json(mapper.writeValueAsString(accountLoged));
        }else{
            ctx.status(401);
        }
    }

    private void newMessage(Context ctx) throws JsonProcessingException{
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.newMessage(message);
        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
        }else{
            ctx.status(400);
        }
    }

    private void listMessage(Context ctx){
        ctx.json(messageService.listMessages());
    }

    private void getMessageById(Context ctx){
        Message message = messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if(message != null){
            ctx.json(message);
        }else{
            ctx.result();
        }
    }

    private void deleteMessageById(Context ctx){
        Message message = messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if(message != null){
            ctx.json(message);
        }else{
            ctx.result();
        }
    }

    private void updateMessage(Context ctx) throws JsonProcessingException{
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.updateMessage(Integer.parseInt(ctx.pathParam("message_id")), message.getMessage_text());
        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
        }else{
            ctx.status(400);
        }
    }

    private void listMessageByAccountId(Context ctx){
        ctx.json(messageService.listMessagesByAccountId(Integer.parseInt(ctx.pathParam("account_id"))));
    }
    


}
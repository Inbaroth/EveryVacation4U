package Model;

public class Message {


    public enum Type {PENDING, CONFIRM};
    public String message;
    private Type messageType;

    public Message(String message, Type type) {
        this.message = message;
        this.messageType = type;
    }

    //<editor-fold desc="Getters">
    public String getMessage() {
        return message;
    }

    public Type getMessageType() {
        return messageType;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setMessage(String message) {
        this.message = message;
    }
    //</editor-fold>


}

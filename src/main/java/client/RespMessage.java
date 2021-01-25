package client;

public class RespMessage {
    public final static RespMessage NULL_MESSAGE = new RespMessage(RespType.NULL, "-1");
    public final static String MESSAGE_SPLIT = "\r\n";

    private RespType respType;
    private String messageBody;

    public RespMessage(RespType respType, String messageBody) {
        this.respType = respType;
        this.messageBody = messageBody;
    }

    public RespMessage() {

    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return messageBody;
    }

    public RespType getRespType() {
        return respType;
    }

    public void setRespType(RespType respType) {
        this.respType = respType;
    }

    public String encode() {
        return respType.name() + "//" + messageBody + MESSAGE_SPLIT;
    }

    public static RespMessage decode(String resp) {
        String[] split = resp.split("//");
        RespMessage respMessage = new RespMessage(RespType.valueOf(split[0]), split[1]);
        return respMessage;
    }
}

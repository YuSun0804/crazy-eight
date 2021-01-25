package client;

public class ReqMessage {
    private ReqType reqType;
    private String messageBody;

    public ReqMessage(ReqType reqType, String messageBody) {
        this.reqType = reqType;
        this.messageBody = messageBody;
    }

    public ReqType getReqType() {
        return reqType;
    }

    public void setReqType(ReqType reqType) {
        this.reqType = reqType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String encode() {
        return reqType.name() + "," + messageBody;
    }

    public static ReqMessage decode(String req){
        String[] split = req.split(",");
        ReqMessage reqMessage = new ReqMessage(ReqType.valueOf(split[0]), split[1]);
        return reqMessage;
    }
}

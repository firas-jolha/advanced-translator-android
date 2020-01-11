package firas.jolha.advancedtranslator.service;

import java.util.HashMap;

public class ResponseMessage {

    private String messageBody;
    private HashMap<String, String> responseHeaders;

    public ResponseMessage(String messageBody, HashMap<String, String> responseHeaders) {
        this.messageBody = messageBody;
        this.responseHeaders = responseHeaders;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public HashMap<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(HashMap<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}

package firas.jolha.advancedtranslator.service;

import firas.jolha.advancedtranslator.utils.HttpHeader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestMessage {

    private String url;
    private HashMap<String, String> urlParams;
    private HashMap<String, String> requestHeaders;

    public RequestMessage(String url, HashMap<String, String> urlParams, HashMap<String, String> requestHeaders) {
        this.url = url;
        this.urlParams = urlParams;
        this.requestHeaders = requestHeaders;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(HashMap<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    public HashMap<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(HashMap<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getParamString() {
        String s = "";
        StringBuilder builder = new StringBuilder("?");
        try {

            for (Map.Entry<String, String> e : urlParams.entrySet()) {
                builder.append(e.getKey()).append("=").append(URLEncoder.encode(e.getValue(), HttpHeader.ENCODING_CHARSET.getHeaderDefaultValue())).append("&");
            }
            s = builder.toString();
        } catch (UnsupportedEncodingException ex) {

        }
        return s.substring(0, s.length() - 1);
    }

    public String getUrlWithParams() {
        return url + getParamString();
    }

}

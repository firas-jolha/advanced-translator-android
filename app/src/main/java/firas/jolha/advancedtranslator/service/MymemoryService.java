package firas.jolha.advancedtranslator.service;

import firas.jolha.advancedtranslator.utils.HttpHeader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

public class MymemoryService extends AbstractTranslateService {

    private static final char DELIMITER = '|';
    private static final String RESPONSE_TEXT_HEADER1 = "responseData";
    private static final String RESPONSE_TEXT_HEADER2 = "translatedText";
    private static final String REQUEST_TEXT_PARAM = "q";
    private static final String REQUEST_Lang_PARAM = "langpair";
//    private static final String REQUEST_KEY_PARAM = "key";

//    private static final String DETECT_RESPONSE_MESSAGE_HEADER = "lang";

    @Override
    public RequestMessage getRequestMessage(RequestElements requestElements) {
        String url = requestElements.getServiceProvider().getUrl();
        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put(REQUEST_Lang_PARAM, requestElements.getFromLang().getShort_lang() + DELIMITER + requestElements.getToLang().getShort_lang());
        urlParams.put(REQUEST_TEXT_PARAM, requestElements.getText());
        HashMap<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(HttpHeader.ACCEPT.getHeaderKey(), HttpHeader.ACCEPT.getHeaderDefaultValue());
        requestHeaders.put(HttpHeader.CONTENT_TYPE.getHeaderKey(), HttpHeader.CONTENT_TYPE.getHeaderDefaultValue());

        return new RequestMessage(url, urlParams, requestHeaders);
    }

    @Override
    public ResponseMessage getResponseMessage(HttpURLConnection connection) {
        String output = "";
        if (connection != null) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), HttpHeader.ENCODING_CHARSET.getHeaderDefaultValue()))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                output += response.toString();
            } catch (IOException e) {
                //TODO: handle exception
            }
        }
        HashMap<String, String> responseHeaders = new HashMap<>();
        return new ResponseMessage(output, responseHeaders);
    }

    @Override
    public String processResponseMessage(ResponseMessage responseMessage) {
        String response = responseMessage.getMessageBody();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response);
            jsonObject = (JSONObject) jsonObject.get(RESPONSE_TEXT_HEADER1);
            response = (String) jsonObject.get(RESPONSE_TEXT_HEADER2);
//            response = ((JSONArray) jsonObject.get(RESPONSE_TEXT_HEADER)).get(0).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }
}

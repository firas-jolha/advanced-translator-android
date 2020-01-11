package firas.jolha.advancedtranslator.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public abstract class AbstractTranslateService implements ITranslateSevice {

    public static final String REQUEST_METHOD_HEADER_KEY = "";

    public abstract RequestMessage getRequestMessage(RequestElements requestElements);

    public abstract ResponseMessage getResponseMessage(HttpURLConnection connection);

    public abstract String processResponseMessage(ResponseMessage responseMessage);


    public HttpURLConnection connectToService(RequestMessage requestMessage) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(requestMessage.getUrlWithParams());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMessage.getRequestHeaders().get("Request-Method"));
            for (Map.Entry<String, String> e : requestMessage.getRequestHeaders().entrySet()) {
                if (e.getKey() != "Request-Method") {
                    connection.setRequestProperty(e.getKey(), e.getValue());
                }
            }
//            connection.setRequestProperty("Content-Type", CONTENT_TYPE_REQUEST_PROPERTY);
//            connection.setRequestProperty("Accept", ACCEPT_REQUEST_PROPERTY);
            connection.connect();

            // TODO: handle other options of response codes
            if (connection.getResponseCode() != 200) {
                return null;
            }

        } catch (MalformedURLException e) {
            //TODO: handle exception
        } catch (IOException ex) {
            //TODO: handle exception
        }
        return connection;
    }


    public String translateText(RequestElements requestElements) {
        RequestMessage requestMessage = getRequestMessage(requestElements);
        HttpURLConnection connection = connectToService(requestMessage);
        ResponseMessage responseMessage = getResponseMessage(connection);
        return processResponseMessage(responseMessage);
    }

    // factory method
    public static ITranslateSevice getTranslateService(ServiceProvider serviceProvider) {
        switch (serviceProvider) {
            case YANDEX:
                return new YandexService();
            case MYMEMORY:
                return new MymemoryService();
            default:
                return new YandexService();
        }
    }

}

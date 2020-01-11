package firas.jolha.advancedtranslator.service;

public enum ServiceProvider {
    YANDEX("https://translate.yandex.net/api/v1.5/tr.json/translate", "trnsl.1.1.20200103T174915Z.bf0d375d35660b5c.5c230b73e56af2aac30cd21282774483a0ee0ba5"),
    MYMEMORY("https://api.mymemory.translated.net/get", null);

    enum Category {
        TRANSLATE, DETECT;
    }

    private String url;
    private String apiKey;

    ServiceProvider(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }
}

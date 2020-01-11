package firas.jolha.advancedtranslator.utils;

public enum HttpHeader {
    REQUEST_METHOD("Request-Method", "POST"),
    CONTENT_TYPE("Content-Type", "application/x-www-form-urlencoded"),
    ACCEPT("Accept", "application/json"),
    ENCODING_CHARSET("Encoding-Charset","UTF-8")
    ;


    private String headerKey;
    private String headerDefaultValue;

    HttpHeader(String headerKey, String headerDefaultValue) {
        this.headerKey = headerKey;
        this.headerDefaultValue = headerDefaultValue;
    }

    public String getHeaderKey() {
        return headerKey;
    }

    public String getHeaderDefaultValue() {
        return headerDefaultValue;
    }
}

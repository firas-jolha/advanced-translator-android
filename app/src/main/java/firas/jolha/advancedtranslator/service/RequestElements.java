package firas.jolha.advancedtranslator.service;

import firas.jolha.advancedtranslator.utils.Lang;

public class RequestElements {
    private String text;
    private Lang fromLang;
    private Lang toLang;
    private ServiceProvider serviceProvider;

    public RequestElements(String text, Lang fromLang, Lang toLang, ServiceProvider serviceProvider) {
        this.text = text;
        this.fromLang = fromLang;
        this.toLang = toLang;
        this.serviceProvider = serviceProvider;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Lang getFromLang() {
        return fromLang;
    }

    public void setFromLang(Lang fromLang) {
        this.fromLang = fromLang;
    }

    public Lang getToLang() {
        return toLang;
    }

    public void setToLang(Lang toLang) {
        this.toLang = toLang;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}

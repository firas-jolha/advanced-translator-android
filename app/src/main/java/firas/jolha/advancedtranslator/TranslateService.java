package firas.jolha.advancedtranslator;

import android.os.AsyncTask;

import firas.jolha.advancedtranslator.service.AbstractTranslateService;
import firas.jolha.advancedtranslator.service.ITranslateSevice;
import firas.jolha.advancedtranslator.service.RequestElements;

public class TranslateService extends AsyncTask<RequestElements, Integer, String> {
    private RequestElements temp = null;
    private String translatedText;

    @Override
    protected String doInBackground(final RequestElements... requestElementsArr) {
        temp = requestElementsArr[0];
        RequestElements requestElements = temp;
        ITranslateSevice service = AbstractTranslateService.getTranslateService(requestElements.getServiceProvider());
        translatedText = service.translateText(requestElements);
        return translatedText;
    }
}

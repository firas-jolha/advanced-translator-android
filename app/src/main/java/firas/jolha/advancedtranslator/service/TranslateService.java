package firas.jolha.advancedtranslator.service;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TranslateService {
    @POST("/translate?text={text}&lang={fromLang}-{toLang}&key=trnsl.1.1.20200103T174915Z.bf0d375d35660b5c.5c230b73e56af2aac30cd21282774483a0ee0ba5")
    Call<String> translateText(@Path("text") String text, @Path("fromLang") String fromLang
            , @Path("toLang") String toLang);
}

class Playground {
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://translate.yandex.net/api/v1.5/tr.json").build();
        TranslateService service = retrofit.create(TranslateService.class);
        Call<String> call = service.translateText("Hello", "en", "tr");
    }
}
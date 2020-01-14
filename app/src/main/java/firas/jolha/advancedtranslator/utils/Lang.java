package firas.jolha.advancedtranslator.utils;

import java.util.ArrayList;
import java.util.Locale;

public enum Lang {
    ENGLISH(0, "English", "en"),
    RUSSIAN(1, "Russian", "ru"),
    ARABIC(2, "Arabic", "ar"),
    FRENCH(3, "French", "fr"),
    TURKISH(4, "Turkish", "tr"),
    SPANISH(5, "Spanish", "es"),
    KOREAN(6, "Korean", "ko"),
    ;

    private int id;
    private String short_lang;
    private String long_lang;
//    private Locale locale;

    public String getLong_lang() {
        return long_lang;
    }

    public String getShort_lang() {
        return short_lang;
    }

    public int getId() {
        return id;
    }

//    public Locale getLocale() {
//        return locale;
//    }

    Lang(int id, String long_lang, String short_lang) {
        this.id = id;
        this.long_lang = long_lang;
        this.short_lang = short_lang;
    }

//    Lang(int id, String short_lang, String long_lang, Locale locale) {
//        this.id = id;
//        this.short_lang = short_lang;
//        this.long_lang = long_lang;
//        this.locale = locale;
//    }

    public static ArrayList<String> getLangsArrayList() {
        ArrayList<String> langs = new ArrayList<>();
        for (Lang l : Lang.values()) {
            langs.add(l.getLong_lang());
        }
        return langs;
    }

}

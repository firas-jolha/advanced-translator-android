package firas.jolha.advancedtranslator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import firas.jolha.advancedtranslator.service.RequestElements;
import firas.jolha.advancedtranslator.service.ServiceProvider;
import firas.jolha.advancedtranslator.utils.Lang;

//import com.vk.sdk.util.VKUtil;

public class MainActivity extends AppCompatActivity {

    // Project SHA1 fingerprint 61E0995A7D78CA78DF72BD23E312FEAC16119473

    private static final long DELAY = 1000; //milliseconds
    // choose service
    private ServiceProvider serviceProvider = ServiceProvider.YANDEX;
    private Lang fromLang = Lang.ENGLISH;
    private Lang toLang = Lang.RUSSIAN;
    // Views
    private Spinner fromLangSpinner = null;
    private Spinner toLangSpinner = null;
    private EditText translateText = null;
    private EditText outputText = null;
    private ImageButton exchangeLangButton = null;
    private RadioButton translateServiceSelected = null;
    private RadioGroup translateServiceRadioGroup = null;
    private ImageButton clearTranslateTextButton = null;
    private TextView inputTextLengthTextView = null;
    private ImageButton TTSTranslateTextButton = null;
    private TextToSpeech textToSpeech = null;

    // current activity
//    private MainActivity current = this;

    //
    private static String clipboardText = "";

    private void init() {
        fromLangSpinner = findViewById(R.id.fromLangSpinner);
        toLangSpinner = findViewById(R.id.toLangSpinner);
        translateText = findViewById(R.id.translateText);
        outputText = findViewById(R.id.outputText);
        exchangeLangButton = findViewById(R.id.exchangeLanguageButton);
        translateServiceRadioGroup = findViewById(R.id.translateServiceRadioGroup);

        translateServiceSelected = findViewById(R.id.translateService1Button);

        clearTranslateTextButton = findViewById(R.id.clearTranslateTextButton);
        inputTextLengthTextView = findViewById(R.id.inputTextLengthTextView);

        TTSTranslateTextButton = findViewById(R.id.TTSTranslateTextButton);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        initLangSpinner(fromLangSpinner);
        initLangSpinner(toLangSpinner);
        toLangSpinner.setOnItemSelectedListener(getFromLangSpinnerItemSelectedListener());
        fromLangSpinner.setOnItemSelectedListener(getToLangSpinnerItemSelectedListener());

        translateText.addTextChangedListener(getInputTextWatcher());

//        outputText.addTextChangedListener(getOutputTextWatcher());
        outputText.setOnTouchListener(getOutputTextOnTouchListener());


        exchangeLangButton.setOnClickListener(getExchangeLangaugeOnClickListener());

        translateServiceSelected.setOnCheckedChangeListener(getTranslateServiceOnCheckedChangeListener());
        translateServiceSelected.setChecked(true);

        translateServiceRadioGroup.setOnCheckedChangeListener(getTransalateServiceOnCheckedChangeListener());

        clearTranslateTextButton.setOnClickListener(getClearTranslateTextOnClickListener());

        TTSTranslateTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        Log.e("TTS", "" + status);

                        if (Lang.values()[fromLangSpinner.getSelectedItemPosition()]==Lang.ARABIC){
                            textToSpeech.setLanguage(Locale.forLanguageTag("ar_EG"));
                        }else {
                            textToSpeech.setLanguage(Locale.forLanguageTag(Lang.values()[fromLangSpinner.getSelectedItemPosition()].getShort_lang()));
                        }

                        Log.e("TTS", "" + textToSpeech.speak(translateText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, ""));
//                textToSpeech.speak(translateText.getText().toString(), TextToSpeech.QUEUE_ADD, null, "yes");
//                        textToSpeech.synthesizeToFile(translateText.getText().toString(), null, new File("fff.wav"), "");
                        Toast.makeText(MainActivity.this, "Saved in Disk", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

//        {
//            String fingerprints[] = VKUtil.getCertificateFingerprint(this, getPackageName());
//            Log.d("SHA1", fingerprints[0]);
//        }
    } // OnCreate()


    private void initLangSpinner(Spinner langSpinner) {
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Lang.getLangsArrayList());
        langSpinner.setAdapter(arrayAdapter1);
    }

    private AdapterView.OnItemSelectedListener getFromLangSpinnerItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                translateText.setText(translateText.getText() + "");
                translateText.setSelection(translateText.getText().length());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private AdapterView.OnItemSelectedListener getToLangSpinnerItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("spinner", String.format("position is %d, id %d ", position, id));
                if (id == Lang.ARABIC.getId()) {
                    translateText.setTextDirection(View.TEXT_DIRECTION_RTL);
                    translateText.setGravity(Gravity.START);
                    translateText.setHint("اكتب للترجمة....");
                } else {
                    translateText.setTextDirection(View.TEXT_DIRECTION_LTR);
                    translateText.setGravity(Gravity.START);
                    translateText.setHint(R.string.translate_text_hint);
                }
                translateText.setText(translateText.getText() + "");
                translateText.setSelection(translateText.getText().length());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private TextWatcher getOutputTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private TextWatcher getInputTextWatcher() {
        return new TextWatcher() {
            private Timer timer = new Timer();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("Text Changed", String.format("s = %s, start = %d, count = %d, after = %d", s, start, count, after));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (translateText.getText().length() > 0) {
                    clearTranslateTextButton.setVisibility(View.VISIBLE);
                }
//                Log.d("Text Changed", String.format("s = %s, start = %d, count = %d", s, start, count));
            }

            @Override
            public void afterTextChanged(Editable s) {

                final String text = s.toString();
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       {
                                           String[] perms = {"android.permission.INTERNET"};
                                           if (checkSelfPermission("android.permission.INTERNET") != PackageManager.PERMISSION_GRANTED) {
                                               ActivityCompat.requestPermissions(MainActivity.this, perms, 1);
                                           }
                                           String output = "";
                                           try {
                                               if (text == null || text.length() <= 0) return;

                                               Lang fromLang = Lang.values()[fromLangSpinner.getSelectedItemPosition()];
                                               Lang toLang = Lang.values()[toLangSpinner.getSelectedItemPosition()];
                                               if (fromLang == toLang) {
                                                   output = text;
                                               } else {

                                                   ServiceProvider serviceProvider = MainActivity.this.serviceProvider;
                                                   RequestElements requestElements = new RequestElements(text, fromLang, toLang, serviceProvider);

                                                   output = new TranslateService().execute(requestElements).get();
                                               }
                                           } catch (ExecutionException e) {
                                               e.printStackTrace();
                                           } catch (InterruptedException e) {
                                               e.printStackTrace();
                                           }
                                           outputText.setText(output);
                                       }
                                   }
                               }, DELAY
                );
                inputTextLengthTextView.setText(translateText.getText().length() + " char(s)");
            }
        };
    }

    private View.OnClickListener getExchangeLangaugeOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float xFrom = fromLangSpinner.getX();
                float xTo = toLangSpinner.getX();

                TranslateAnimation animation = new TranslateAnimation(0f, xTo
                        , 0f, fromLangSpinner.getY());
                animation.setRepeatMode(0);
                animation.setDuration(500);
                animation.setFillAfter(true);
                fromLangSpinner.startAnimation(animation);
                animation = new TranslateAnimation(0f, -xTo
                        , 0f, toLangSpinner.getY());
                animation.setDuration(500);
                animation.setFillAfter(true);
                toLangSpinner.startAnimation(animation);
//                fromLangSpinner.setTranslationX(-xTo);
//                toLangSpinner.setTranslationX(xTo);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(fromLangSpinner, "translationX", 500f);
//                animator.setDuration(1000);
//                animator.start();
//                animator = ObjectAnimator.ofFloat(toLangSpinner, "translationX", -500f);
//                animator.setDuration(1000);
//                animator.start();

                Spinner temp = fromLangSpinner;
                fromLangSpinner = toLangSpinner;
                toLangSpinner = temp;

//                int temp = fromLangSpinner.getSelectedItemPosition();
//                fromLangSpinner.setSelection(toLangSpinner.getSelectedItemPosition());
//                toLangSpinner.setSelection(temp);
            }
        };
    }

    private CompoundButton.OnCheckedChangeListener getTranslateServiceOnCheckedChangeListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    serviceProvider = ServiceProvider.YANDEX;
                } else {
                    serviceProvider = ServiceProvider.MYMEMORY;
                }
                translateText.setText(translateText.getText() + "");
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener getTransalateServiceOnCheckedChangeListener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("TS-RADIO-BUTTON", "checked id is " + checkedId);
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (checkedId == group.getChildAt(i).getId()) {
                        serviceProvider = ServiceProvider.values()[i];
                        translateText.setText(translateText.getText() + "");
                        break;
                    }

                }

            }
        };
    }

    private View.OnClickListener getClearTranslateTextOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateText.setText("");
                clearTranslateTextButton.setVisibility(View.INVISIBLE);
            }
        };
    }

    private View.OnTouchListener getOutputTextOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // From StackOverFlow
//                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
//                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                    clipboard.setText(text);
//                } else {
//                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                    android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
//                    clipboard.setPrimaryClip(clip);
//                }

                if (outputText.getText().length() > 0 && !outputText.getText().toString().equals(clipboardText)) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboardText = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();

                    ClipData clip = ClipData.newPlainText("Translated Text", outputText.getText());
                    Toast.makeText(MainActivity.this, "Copied to Clipboard :)", Toast.LENGTH_LONG).show();
                    clipboardManager.setPrimaryClip(clip);
                    return true;
                }
                return false;
            }
        };
    }

}

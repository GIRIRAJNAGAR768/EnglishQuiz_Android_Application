package english.grammar.quizapp;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class TextToSpeak extends AppCompatActivity implements

    TextToSpeech.OnInitListener,AdapterView.OnItemSelectedListener {
        AdView banner1,banner2;




        private TextToSpeech tts;
        private Button buttonSpeak;
        private EditText editText;
        private Spinner speedSpinner;
        TextView textView1,speedText;

        Typeface font;
        private static String speed="Normal";
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_text_to_speak);
            setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            font = Typeface.createFromAsset(getAssets(),"font/BabelSans-Bold.ttf");
            banner1=findViewById(R.id.adView1);
            banner2=findViewById(R.id.adView2);

            AdRequest adRequest=new AdRequest.Builder().build();
            banner1.loadAd(adRequest);
            banner2.loadAd(adRequest);

            textView1=findViewById( R.id.textView1 );
            speedText=findViewById( R.id.textView2 );
            tts = new TextToSpeech(this, this);
            buttonSpeak = (Button) findViewById(R.id.button1);
            editText = (EditText) findViewById(R.id.editText1);
            speedSpinner = (Spinner) findViewById(R.id.spinner1);

            textView1.setTypeface(font);
            speedText.setTypeface(font);
            buttonSpeak.setTypeface(font);
            loadSpinnerData();
            speedSpinner.setOnItemSelectedListener(this);

//button click event
            buttonSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    buttonSpeak.setBackgroundColor(getResources().getColor(R.color.speakonclick));

                    new Handler().postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            buttonSpeak.setBackgroundColor(getResources().getColor(R.color.speak));
                        }
                    },100);
                    setSpeed();
                    speakOut();
                }

            });
        }


        @Override
        public void onInit(int status) {

            if (status == TextToSpeech.SUCCESS) {

                int result = tts.setLanguage( Locale.US);

                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported");
                } else {
                    buttonSpeak.setEnabled(true);
                    speakOut();
                }

            } else { Log.e("TTS", "Initilization Failed!");}

        }

        @Override
        public void onDestroy() {
// Don't forget to shutdown tts!
            if (tts != null) {
                tts.stop();
                tts.shutdown();
            }
            super.onDestroy();
        }

    private void setSpeed(){
        if(speed.equals("Very Slow")){
            tts.setSpeechRate(0.1f);
        }
        if(speed.equals("Slow")){
            tts.setSpeechRate(0.5f);
        }
        if(speed.equals("Normal")){
            tts.setSpeechRate(1.0f);//default 1.0
        }
        if(speed.equals("Fast")){
            tts.setSpeechRate(1.5f);
        }
        if(speed.equals("Very Fast")){
            tts.setSpeechRate(2.0f);
        }
        //for setting pitch you may call
        //tts.setPitch(1.0f);//default 1.0
    }

    private void speakOut() {
        String text = editText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void loadSpinnerData() {
        //Data for speed Spinner
        List<String> lables = new ArrayList<String>();
        lables.add("Very Slow");
        lables.add("Slow");
        lables.add("Normal");
        lables.add("Fast");
        lables.add("Very Fast");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        speedSpinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        speed = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "You selected: " + speed,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }


}

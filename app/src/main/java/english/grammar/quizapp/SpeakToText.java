package english.grammar.quizapp;


import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Locale;

public class SpeakToText extends AppCompatActivity {

   TextView copy,reset,text;
    private EditText txvResult;
    Typeface font;

    @Override
   protected void onCreate(Bundle saveInstantState) {
        super.onCreate( saveInstantState );
        setContentView( R.layout.activity_speak_to_text );
        txvResult = findViewById( R.id.txvResult );
        copy = findViewById( R.id.copy );
        reset = findViewById( R.id.reset );
        text = findViewById( R.id.text );
        text.setText( "click on mike to speak something" );
        font = Typeface.createFromAsset( getAssets(), "font/BabelSans-Bold.ttf" );

        txvResult.setTypeface( font );
        copy.setTypeface( font );
        reset.setTypeface( font );
        text.setTypeface( font );

        AdView banner1,banner2;
        banner1=findViewById( R.id.adView1 );
        banner2=findViewById( R.id.adView2 );
        AdRequest adRequest=new AdRequest.Builder().build();
        banner1.loadAd(  adRequest);
        banner2.loadAd( adRequest );


        //copy.execute("hello",
          //      Language.ENGLISH, Language.HINDI);

        copy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txvResult.getText().toString().equals( "" )) {
                    Toast.makeText( SpeakToText.this, "Speak Something Before copy", Toast.LENGTH_LONG ).show();
                    return;
                }
                ((ClipboardManager) SpeakToText.this.getSystemService( CLIPBOARD_SERVICE )).setText( txvResult.getText() );
                Toast.makeText( SpeakToText.this, "Text are Copied", Toast.LENGTH_LONG ).show();
            }
        } );
        reset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txvResult.setText( null );
            }
        } );
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));
                }
                break;
        }
    }

}
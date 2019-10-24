package english.grammar.quizapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class SpokenActivity extends AppCompatActivity {
    AdView banner1,banner2;
    Typeface font;
    Button tts,stt;

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_spoken );
            setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

            tts=findViewById( R.id.texttospeak);
            stt=findViewById( R.id.speaktotext);
            font = Typeface.createFromAsset( getAssets(), "font/BabelSans-Bold.ttf" );
            banner1 = findViewById( R.id.adView1 );
            banner2 = findViewById( R.id.adView2 );

            tts.setText( "Text To Speak" );
            stt.setText( "Speak To Text" );
            tts.setTypeface( font );
            stt.setTypeface( font );

        AdRequest adRequest = new AdRequest.Builder().build();
            banner1.loadAd( adRequest );
            banner2.loadAd( adRequest );

            tts.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity( new Intent( SpokenActivity.this,TextToSpeak.class ) );
                }
            } );

        stt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( SpokenActivity.this,SpeakToText.class ) );
            }
        } );

        }

}

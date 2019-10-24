package english.grammar.quizapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;

public class HomeActivity extends AppCompatActivity {

    LinearLayout start,spoken,option,moreApps;
    TextView t1,t2,t3,t4;
    Typeface font;
    String PLAY_STORE = "https://play.google.com/store/apps/details?id=english.grammar.quizapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MobileAds.initialize(this, "ca-app-pub-3303054497534652~6268180984");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        start=findViewById(R.id.item1);
        spoken=findViewById(R.id.item2);
        option=findViewById(R.id.item3);
        moreApps=findViewById(R.id.item4);

        font= Typeface.createFromAsset(getAssets(),"font/BabelSans-Bold.ttf");

        t1=findViewById(R.id.text1);
        t2=findViewById(R.id.text2);
        t3=findViewById(R.id.text3);
        t4=findViewById(R.id.text4);

        t1.setTypeface( font );
        t2.setTypeface( font );
        t3.setTypeface( font );
        t4.setTypeface( font );

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });

        spoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SpokenActivity.class));
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,OptionActivity.class));
            }
        });

        moreApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert();
               // finish();
               // startActivity(new Intent(HomeActivity.this,MoreAppsActivity.class));
            }
        });

    }

    public void alert(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog,null,true);
        alertdialog.setView(view);
        TextView title = view.findViewById(R.id.errorTitle);
        TextView message = view.findViewById(R.id.errorMessage);
        final Button positive = view.findViewById(R.id.positive);
        final Button negative = view.findViewById(R.id.negative);

        title.setTypeface(font);
        message.setTypeface(font);
        positive.setTypeface(font);
        negative.setTypeface(font);

        title.setText("More Apps");
        message.setText("Unavailable at this time.");
        positive.setText("Ok");
        negative.setText("Rate Now");

        final AlertDialog alert = alertdialog.create();
        alert.setCancelable(false);
        alert.show();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();


            }

        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();

                Intent update = new Intent(Intent.ACTION_VIEW);
                update.setData(Uri.parse(PLAY_STORE));
                startActivity(update);

            }
        });
    }


}

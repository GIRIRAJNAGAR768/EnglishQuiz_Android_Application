package english.grammar.quizapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class LevelActivity extends AppCompatActivity {
    AdView banner1,banner2;
    ConnectionDetector cd;
    Typeface font;
    ListView listView;
    String[] title = {
            "LEVEL 1",
            "LEVEL 2",
            "LEVEL 3",
            "LEVEL 4",
            "LEVEL 5",
            "LEVEL 6",
            "LEVEL 7"
    };

    TextView LevelName;
    int MAXLEVEL,MAXSUBLEVEL,STARTLEVEL;
    String PLAY_STORE = "https://play.google.com/store/apps/details?id=english.grammar.quizapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cd=new ConnectionDetector( this );
        retriveMaxSubLevel();

        banner1=findViewById(R.id.adView1);
        banner2=findViewById(R.id.adView2);

        AdRequest adRequest=new AdRequest.Builder().build();
        banner1.loadAd(adRequest);
        banner2.loadAd(adRequest);

        Bundle b = getIntent().getExtras();
        STARTLEVEL = (int) b.get("START_LEVEL");
        LevelName = findViewById(R.id.LevelName);

        if (STARTLEVEL == 0)
            LevelName.setText("BIGNNER LEVEL");
        else if (STARTLEVEL == 1)
            LevelName.setText("MEDIUM LEVEL");
        else if (STARTLEVEL == 2)
            LevelName.setText("ADVANCED LEVEL");
        else
            LevelName.setText("FINAL LEVEL");


        font = Typeface.createFromAsset(getAssets(), "font/BabelSans-Bold.ttf");
        LevelName.setTypeface(font);

        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);




        listView = findViewById(R.id.sublevleslistid);
        listView.setAdapter(new MyLevelAdapter(LevelActivity.this, title,STARTLEVEL));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {



                 if(MAXLEVEL>STARTLEVEL){
                     if(cd.isConnected()) {
                         Intent quiz = new Intent( LevelActivity.this, QuizActivity.class );
                         quiz.putExtra( "SET_LEVEL", STARTLEVEL );
                         quiz.putExtra( "SET_QUIZ", pos );
                         finish();
                         startActivity( quiz );
                     }
                     else
                     {
                         Toast.makeText( LevelActivity.this, "Network Connection Error...", Toast.LENGTH_SHORT ).show();
                     }
                }else if(MAXLEVEL==STARTLEVEL){
                    if(pos>MAXSUBLEVEL){
                        alert();
                    }else {
                        if(cd.isConnected()) {
                        Intent quiz = new Intent(LevelActivity.this,QuizActivity.class);
                        quiz.putExtra("SET_LEVEL",STARTLEVEL);
                        quiz.putExtra("SET_QUIZ",pos);
                        finish();
                        startActivity(quiz);
                        }
                        else
                        {
                            Toast.makeText( LevelActivity.this, "Network Connection Error...", Toast.LENGTH_SHORT ).show();
                        }
                    }
                }



            }
        });

    }

    public void retriveMaxSubLevel(){
        SharedPreferences retrive = getApplicationContext().getSharedPreferences("novus",MODE_PRIVATE);
        MAXLEVEL=retrive.getInt("MAXLEVEL",0);
        MAXSUBLEVEL=retrive.getInt("MAXSUBLEVEL",0);
    }


    public void alert(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(LevelActivity.this);
        LayoutInflater inflater = LevelActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog,null,true);
        alertdialog.setView(view);
        TextView title = view.findViewById(R.id.errorTitle);
        TextView message = view.findViewById(R.id.errorMessage);
        Button positive = view.findViewById(R.id.positive);
        Button negative = view.findViewById(R.id.negative);

        title.setTypeface(font);
        message.setTypeface(font);
        positive.setTypeface(font);
        negative.setTypeface(font);

        title.setText("Complete Previous Levels");
        message.setText("To play this level you have to complete the previous levels");
        positive.setText("Ok");
        negative.setText("Rate Us");
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
                Intent rateUs = new Intent(Intent.ACTION_VIEW);
                rateUs.setData(Uri.parse(PLAY_STORE));
                startActivity(rateUs);

            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent level = new Intent(LevelActivity.this,MainActivity.class);
        finish();
        startActivity(level);
    }
    public class MyLevelAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] web;
        private int StartLevel;
        int MAXLEVEL, MAXSUBLEVEL;

        public MyLevelAdapter(Activity context,
                              String[] web, int Level) {
            super(context, R.layout.fragment_sublevels, web);
            this.context = context;
            this.web = web;
            this.StartLevel = Level;
            retiveData();


        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.fragment_sublevels, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
            TextView subTxt = rowView.findViewById(R.id.play);
            subTxt.setTypeface(font);

            txtTitle.setText(web[position]);
            txtTitle.setTypeface(font);

            if(StartLevel==MAXLEVEL){
                if(position<MAXSUBLEVEL)
                {
                    subTxt.setText("Completed");
                    subTxt.setTextColor(getResources().getColor(R.color.green));}

                if(position==MAXSUBLEVEL)
                    subTxt.setText("Play");
            }else{
                subTxt.setText("Completed");
                subTxt.setTextColor(getResources().getColor(R.color.green));}

            return rowView;
        }

        public void retiveData() {
            SharedPreferences retrive = context.getApplicationContext().getSharedPreferences("novus", MODE_PRIVATE);
            MAXLEVEL = retrive.getInt("MAXLEVEL", 0);
            MAXSUBLEVEL = retrive.getInt("MAXSUBLEVEL", 0);
        }
    }
}
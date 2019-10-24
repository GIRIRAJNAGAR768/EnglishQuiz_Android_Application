package english.grammar.quizapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {
   AdView banner1,banner2;


    int MAXLEVEL;
    String PLAY_STORE = "https://play.google.com/store/apps/details?id=english.grammar.quizapp";
    Typeface font;
    ConnectionDetector cd;
    ListView listView;
    String[] name = {
            "BEGINNER\nLEVEL",
            "INTERMEDIATE\nLEVEL",
            "ADVANCED\nLEVEL",
            "FINAL\nLEVEL"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cd=new ConnectionDetector( this );
        retriveMaxLevel();

        banner1=findViewById(R.id.adView1);
        banner2=findViewById(R.id.adView2);

        AdRequest adRequest=new AdRequest.Builder().build();
        banner1.loadAd(adRequest);
        banner2.loadAd(adRequest);


        font = Typeface.createFromAsset(getAssets(),"font/BabelSans-Bold.ttf");
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        listView = findViewById(R.id.list);
        listView.setAdapter(new MainActivity.MyAdapter(MainActivity.this, name, MAXLEVEL));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {

                if (pos > MAXLEVEL) {
                    alert();
                } else {
                    if(cd.isConnected()) {
                        Intent sublevel = new Intent( MainActivity.this, LevelActivity.class );
                        sublevel.putExtra( "START_LEVEL", pos );
                        finish();
                        startActivity( sublevel );
                    }
                    else
                    {
                        Toast.makeText( MainActivity.this, "Network Connection Error...", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        });
    }

    public void retriveMaxLevel() {
        SharedPreferences retrive = getApplicationContext().getSharedPreferences("novus", MODE_PRIVATE);
        MAXLEVEL = retrive.getInt("MAXLEVEL", 0);
    }

    public void alert() {
        android.support.v7.app.AlertDialog.Builder alertdialog = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog, null, true);
        alertdialog.setView(view);
        TextView title = view.findViewById(R.id.errorTitle);
        TextView message = view.findViewById(R.id.errorMessage);
        Button positive = view.findViewById(R.id.positive);
        Button negative = view.findViewById(R.id.negative);

        title.setTypeface(font);
        message.setTypeface(font);
        positive.setTypeface(font);
        negative.setTypeface(font);

        title.setText("Complete Previous Level");
        message.setText("To play this level you have to complete the previous level");
        positive.setText("Ok");
        negative.setText("Rate Us");
        final android.support.v7.app.AlertDialog alert = alertdialog.create();
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

    public class MyAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] web;
        private int StartLevel;
        int MAXLEVEL, MAXSUBLEVEL;

        public MyAdapter(Activity context,
                         String[] web, int Level) {
            super(context, R.layout.fragment_grid, web);
            this.context = context;
            this.web = web;
            this.StartLevel = Level;
            retiveData();

        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.fragment_grid, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            TextView subTxt = rowView.findViewById(R.id.subtxt);
            subTxt.setTypeface(font);
            txtTitle.setText(web[position]);
            txtTitle.setTypeface(font);

            if (position < StartLevel)
            {
                subTxt.setText("Completed");
            subTxt.setTextColor(getResources().getColor(R.color.green));}
            else if (position == StartLevel)
                subTxt.setText("Play");
            return rowView;


        }

        public void retiveData() {
            SharedPreferences retrive = context.getApplicationContext().getSharedPreferences("novus", MODE_PRIVATE);
            MAXLEVEL = retrive.getInt("MAXLEVEL", 0);
            MAXSUBLEVEL = retrive.getInt("MAXSUBLEVEL", 0);
        }
    }

}
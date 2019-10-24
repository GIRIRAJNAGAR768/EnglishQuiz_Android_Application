package english.grammar.quizapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.ads.InterstitialAd;


public class OptionActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    InterstitialAd inter1,inter2;
    AdView banner1,banner2;

    Typeface font;
    String whatsappLink,promoteMessage=null;
    ListView listView;
    String[] title = {
            "RESET LEVELS",
            "ABOUT US",
            "CONTACT US",
            "CHECK FOR UPDATE",
            "PROMOTE YOUR APP",
            "SHARE APP",
            "EXIT",
    };

    String PLAY_STORE = "https://play.google.com/store/apps/details?id=english.grammar.quizapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inter1=new InterstitialAd( this );
        inter2=new InterstitialAd( this );
        inter1.setAdUnitId( getResources().getString( R.string.inter1 ) );
        inter2.setAdUnitId(getResources().getString(R.string.inter2));
        TextView txt=findViewById(R.id.LevelName);
        txt.setText("Other Options");
          banner1=findViewById(R.id.adView1);
          banner2=findViewById(R.id.adView2);
        AdRequest adRequest=new AdRequest.Builder().build();
         banner1.loadAd(adRequest);
         banner2.loadAd(adRequest);
        inter1.loadAd( adRequest );
        inter2.loadAd(adRequest);
        font = Typeface.createFromAsset(getAssets(), "font/BabelSans-Bold.ttf");
        txt.setTypeface(font);
        progressDialog=new ProgressDialog(this);




        listView = findViewById(R.id.sublevleslistid);
        listView.setAdapter(new MyLevelAdapter(OptionActivity.this, title));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {

                if(pos==0)
                {
                    alert("reset");
                }
                else if(pos==1)
                {
                    alert("about");
                }

                else if(pos==2)//contact
                {
                    Intent in=new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","help.developement@gmail.com",null));
                    in.putExtra(Intent.EXTRA_SUBJECT,"Supported Mail");
                    startActivity(Intent.createChooser(in,"use service"));

                }

               /* else if(pos==3)//whtsapp group
                {
                    progressDialog.show();
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            whatsappLink = dataSnapshot.getValue(String.class);
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if(whatsappLink==null)
                                Toast.makeText(OptionActivity.this,"Network Connection Error...",Toast.LENGTH_SHORT).show();
                            else {
                                Intent update = new Intent( Intent.ACTION_VIEW );
                                update.setData( Uri.parse( whatsappLink ) );
                                startActivity( update );
                            }
                        }
                    },5000);

                    alert("whatsapp");
                }*/

                else if(pos==3)//update
                {
                    final Intent update1 = new Intent(Intent.ACTION_VIEW);
                    update1.setData(Uri.parse(PLAY_STORE));
                    startActivity(update1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },500);
                }

                else if(pos==6)
                {
                    alert("exit");
                }
                else if(pos==4)
                {
                    /*
                    promote.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            promoteMessage=dataSnapshot.getValue(String.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                    alert("promote");
                }

                else if(pos==5)
                {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out the android Application of English Grammer Learning Quiz at: https://play.google.com/store/apps/details?id=softs.dev.englishquizgame" +
                                    " for play this quiz click on above link and enjoy.\n" +
                                    "Created By Giriraj Nagar/Dev Softs");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);

                }
            }
        });

    }

    public void alert(final String data){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(OptionActivity.this);
        LayoutInflater inflater = OptionActivity.this.getLayoutInflater();
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


        if(data.equals("reset")) {
            title.setText(R.string.Reset);
            message.setText(R.string.ResetMSG);
            positive.setText(R.string.ResetButton);
            negative.setText(R.string.Back);
        }

        else if(data.equals("exit"))
        {
            title.setText("Exit Alert");
            message.setText("Are You Sure You Want To Exit?");
            positive.setText("Sure");
            negative.setText("No");
        }
        else if(data.equals("about"))
        {
            title.setText("About Us");
            message.setText("Giriraj Nagar/Dev Softs\nhelp.developement@gmail.com");
            positive.setText("Ok");
            negative.setText("Rate Now");
        }
        else if(data.equals("promote"))
        {
            title.setText("Promote Your App");
            if(promoteMessage==null)
                message.setText("Coming soon...");
            else
                message.setText(promoteMessage);

            positive.setText("Ok");
            negative.setEnabled(false);
            }

        final AlertDialog alert = alertdialog.create();
        alert.setCancelable(false);
        alert.show();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.equals("reset")) {
                    alert.dismiss();
                    SharedPreferences.Editor saveGameState = getApplicationContext().getSharedPreferences("novus", MODE_PRIVATE).edit();
                    saveGameState.putInt("MAXLEVEL",0);
                    saveGameState.putInt("MAXSUBLEVEL",0);
                    saveGameState.commit();
                    Toast.makeText(OptionActivity.this, "Reset successfully done", Toast.LENGTH_SHORT).show();
                }

                else if(data.equals("exit"))
                {
                    System.exit(1);
                }
                else if(data.equals("about")) {
                    alert.dismiss();
                    if (inter1.isLoaded())
                        inter1.show();
                }
                else if(data.equals("promote"))
                {

                    alert.dismiss();

                }
            }

        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();

                if(data.equals( "exit" ))
                {
                    alert.dismiss();
                    if (inter2.isLoaded())
                        inter2.show();
                }

                if(data.equals("about"))
                {
                    Intent update = new Intent(Intent.ACTION_VIEW);
                    update.setData(Uri.parse(PLAY_STORE));
                    startActivity(update);
                }

            }
        });
    }



    public class MyLevelAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] web;

        private MyLevelAdapter(Activity context,
                              String[] web) {
            super(context, R.layout.fragment_sublevels, web);
            this.context = context;
            this.web = web;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.fragment_sublevels, null, true);
            TextView txtTitle = rowView.findViewById(R.id.title);
            txtTitle.setText(web[position]);
            txtTitle.setTypeface(font);


            return rowView;
        }


    }


}
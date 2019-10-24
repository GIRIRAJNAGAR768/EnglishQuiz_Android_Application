package english.grammar.quizapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MoreAppsActivity extends AppCompatActivity {

  /*  ConnectionDetector cd;
    InterstitialAd interstitialAd;
    Typeface font;
    DatabaseReference databaseReference;
    List<String> link = new ArrayList<>();
    List<String> app = new ArrayList<>();
    List<String>  img = new ArrayList<>();
    ListView listView;*/
    Typeface font;
    String PLAY_STORE = "https://play.google.com/store/apps/details?id=english.grammar.quizapp";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_apps);

        alert();
        font = Typeface.createFromAsset(getAssets(), "font/BabelSans-Bold.ttf");



      /*  cd=new ConnectionDetector(this);
        if(!cd.isConnected()) {
            Toast.makeText( MoreAppsActivity.this, "Network Connection Error...", Toast.LENGTH_SHORT ).show();
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    Toast.makeText( MoreAppsActivity.this, "Network Connection Error...", Toast.LENGTH_SHORT ).show();

                }
            }, 2000 );
        }


        databaseReference= FirebaseDatabase.getInstance().getReference("Apps");
        font = Typeface.createFromAsset(getAssets(),"font/Cooljazz.ttf");

        interstitialAd=new InterstitialAd( this );
        interstitialAd.setAdUnitId( getResources().getString( R.string.inter1 ) );
        AdRequest adRequest=new AdRequest.Builder().build();
        interstitialAd.loadAd( adRequest );


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        listView = findViewById(R.id.list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                link.clear();
                app.clear();
                img.clear();

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    link.add(data.child("Link").getValue().toString());
                    app.add(data.child("Name").getValue().toString());
                    img.add(data.child("Img").getValue().toString());
                    Collections.reverse(link);
                    Collections.reverse(app);
                    Collections.reverse(img);
                    listView.setAdapter(new MyAdapter(MoreAppsActivity.this, app, img));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link.get(pos)));
                startActivity(intent);





            }
        });
    }

    public void onBackPressed()
    {
        startActivity(new Intent( MoreAppsActivity.this,HomeActivity.class ));
        if(interstitialAd.isLoaded())
        {
            interstitialAd.show();
        finish();}
        else
        {
            finish();
        }

    }

    public class MyAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private  List<String> img =new ArrayList<>();
        private  List<String> app =new ArrayList<>();

        private MyAdapter(Activity context, List<String> app, List<String> imageId) {
            super(context, R.layout.fragment_more_app, app);
            this.context = context;
            this.app = app;
            this.img = imageId;


        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.fragment_more_app, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
             txtTitle.setTypeface(font );
            ImageView imageView = rowView.findViewById(R.id.image);
            txtTitle.setText(app.get(position));
            Glide.with(context).load(img.get(position)).thumbnail(Glide.with(context).load(R.drawable.loading)).into(imageView);
            return rowView;
        }


*/





    }

    public void alert(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(MoreAppsActivity.this);
        LayoutInflater inflater = MoreAppsActivity.this.getLayoutInflater();
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

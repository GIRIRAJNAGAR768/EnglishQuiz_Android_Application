package english.grammar.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class QuizActivity extends AppCompatActivity {
   AdView banner1,banner2;
    AdRequest adRequest;
    InterstitialAd inter1,inter2;
    int value_points=0,value_qn=1,value_lives=3,MAXLEVEL,MAXSUBLEVEL,LEVEL,SUBLEVEL ,correctButton;
    Random random_question,random_option;
    Button[] option = new Button[4];
    Typeface font;
    Intent Sublevel;
    TextView question,show_points,show_qn,show_lives,levelname,levelnum,livetext,pointtext,quenumtext;
    List<Integer> askedQuestionID = new ArrayList<>(),optionSetID = new ArrayList<>(),ButttonFilled = new ArrayList<>();
    String[] Questions,split;
    String askingQuestion,CorrectAnswer,UserAnswer,PLAY_STORE,LevelName;
    MediaPlayer mdx,mdxCorrect;
   // Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retriveMax();
        Bundle extra = getIntent().getExtras();
        if(extra!=null){
            LEVEL  = (int)extra.get("SET_LEVEL");
            SUBLEVEL = (int)extra.get("SET_QUIZ");
        }
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       // mdx = MediaPlayer.create(this, R.raw.wrong);
        mdxCorrect=MediaPlayer.create( this, R.raw.audio );
        //vibrator=(Vibrator)getSystemService( Context.VIBRATOR_SERVICE);
       banner1=findViewById(R.id.adView1);
        banner2=findViewById(R.id.adView2);
        inter1=new InterstitialAd(this);
        inter2=new InterstitialAd(this);
        inter1.setAdUnitId(getResources().getString(R.string.inter1));
        inter2.setAdUnitId(getResources().getString(R.string.inter2));
        adRequest=new AdRequest.Builder().build();

        inter1.loadAd(adRequest);
        inter2.loadAd(adRequest);


        oneTimeSetup();
        setQuestion();




    }
    public void oneTimeSetup(){
        //Typeface
        font = Typeface.createFromAsset(getAssets(),"font/BabelSans-Bold.ttf");

        //Get questions form resource according to the level and sublevel
        getQuestions();


        option[0]   = (Button) findViewById(R.id.option0);
        option[1]   = (Button) findViewById(R.id.option1);
        option[2]   = (Button) findViewById(R.id.option2);
        option[3]   = (Button) findViewById(R.id.option3);
        question    = (TextView) findViewById(R.id.qiestion);
        show_points = (TextView) findViewById(R.id.points);
        show_qn  = (TextView) findViewById(R.id.qn);
        show_lives  = (TextView) findViewById(R.id.lives);
        levelname   = (TextView) findViewById(R.id.levellabel);
        levelnum    = (TextView) findViewById(R.id.levelnumber);

      /*  livetext=findViewById(R.id.livetext);
        pointtext=findViewById(R.id.pointtext);
        quenumtext=findViewById(R.id.quenumtext);*/

        show_qn.setText("Que.No: "+String.valueOf(value_qn));
        show_points.setText("Points: "+String.valueOf(value_points));
        levelname.setText(LevelName);
        show_lives.setText("Lives: "+String.valueOf(value_lives));
        int temp = SUBLEVEL+1;
        levelnum.setText("Level "+temp);


        levelname.setTypeface(font);
        levelnum.setTypeface(font);
//        livetext.setTypeface(font);
  //      pointtext.setTypeface(font);
    //    quenumtext.setTypeface(font);
        question.setTypeface(font);
        option[0].setTypeface(font);
        option[1].setTypeface(font);
        option[2].setTypeface(font);
        option[3].setTypeface(font);
        show_points.setTypeface(font);
        show_qn.setTypeface(font);
        show_lives.setTypeface(font);

        //PLAY STORE LINK RATE US
        PLAY_STORE = "https://play.google.com/store/apps/details?id=english.grammar.quizapp";


        //Setup for setOnClickListner
        option[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<4;i++)
                    option[i].setEnabled(false);
                UserAnswer = option[0].getText().toString();
                if(UserAnswer.equals(CorrectAnswer)){
                    UserGiveRightAnswer(0);
                }else {
                    UserGiveWrongAnswer(0,correctButton);
                }

            }
        });
        option[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<4;i++)
                    option[i].setEnabled(false);
                UserAnswer = option[1].getText().toString();
                if(UserAnswer.equals(CorrectAnswer)){
                    UserGiveRightAnswer(1);
                }else {
                    UserGiveWrongAnswer(1,correctButton);
                }
            }
        });
        option[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<4;i++)
                    option[i].setEnabled(false);
                UserAnswer = option[2].getText().toString();
                if(UserAnswer.equals(CorrectAnswer)){
                    UserGiveRightAnswer(2);
                }else {
                    UserGiveWrongAnswer(2,correctButton);
                }
            }
        });
        option[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<4;i++)
                    option[i].setEnabled(false);
                UserAnswer = option[3].getText().toString();
                if(UserAnswer.equals(CorrectAnswer)){
                    UserGiveRightAnswer(3);
                }else {
                    UserGiveWrongAnswer(3,correctButton);
                }
            }
        });

    }
    public void retriveMax(){
        SharedPreferences retrive = getApplicationContext().getSharedPreferences("novus",MODE_PRIVATE);
        MAXLEVEL=retrive.getInt("MAXLEVEL",0);
        MAXSUBLEVEL = retrive.getInt("MAXSUBLEVEL",0);
    }
    public void setQuestion(){

       banner1.loadAd(adRequest);
        banner2.loadAd(adRequest);
        int Question_number;
        random_question = new Random();
        Question_number = random_question.nextInt(Questions.length);
        if(askedQuestionID.contains(Question_number)){
            setQuestion();
        }
        else {
            askedQuestionID.add(Question_number);
            split(Questions[Question_number]);
            question.setText(askingQuestion);
            setOption();
        }
    }
    public void split(String string){
        split = string.split("#");
        askingQuestion = split[4];
        CorrectAnswer = split[0];
        split[4]=null;
    }
    public void setOption(){
        int Option_number,Button_Number;
        optionSetID.clear();
        ButttonFilled.clear();
        random_option = new Random();
        for(;ButttonFilled.size()<4;){
            Option_number = random_option.nextInt(4);
            Button_Number = random_option.nextInt(4);
            if(optionSetID.contains(Option_number) || ButttonFilled.contains(Button_Number)){
            }else{
                optionSetID.add(Option_number);
                if(Option_number==0){
                    correctButton=Button_Number;
                   // Log.d( "Correct",""+i);
                }
                ButttonFilled.add(Button_Number);
                option[Button_Number].setText(split[Option_number]);
            }
        }
    }
    public void UserGiveRightAnswer(int buttonId){
        mdxCorrect.start();
        option[buttonId].setBackgroundColor(getResources().getColor(R.color.green));
      //  alert("RIGHT");

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                mdxCorrect.pause();
                value_points = value_points+10;
                show_points.setText("Points: "+String.valueOf(value_points));
                ++value_qn;
                show_qn.setText("Que.No: "+String.valueOf(value_qn));
                refreshPage();
            }
        },2000);


    }
    public void UserGiveWrongAnswer(int buttonId,int correctButton){
        //vibrator.vibrate(1000);
        //mdx.start();
        option[correctButton].setBackgroundColor(getResources().getColor(R.color.green));
        option[buttonId].setBackgroundColor(getResources().getColor(R.color.red));


       // alert("WRONG");

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                //mdx.pause();
                value_lives--;
                show_lives.setText("Lives: "+String.valueOf(value_lives));
                ++value_qn;
                show_qn.setText("Que.No: "+String.valueOf(value_qn));
                refreshPage();
            }
        },3500);



    }
    @SuppressLint("NewApi")
    public void refreshPage(){
        for(int i=0;i<4;i++){
            option[i].setEnabled(true);
            option[i].setBackground(getResources().getDrawable(R.drawable.buttnbackexergridoption));
        }
        if(askedQuestionID.size()<=9){
            if(value_lives>0) {
                setQuestion();
            }else {
                LevelLost();
            }
        }else {
            if(value_lives>0) {
                LevelWon();
            }else {
                LevelLost();
            }
        }
    }

    public void LevelLost(){
        alert("LOST");

    }
    public void LevelWon(){

        if(LEVEL==MAXLEVEL&&SUBLEVEL==MAXSUBLEVEL){
            boolean saved = saveData();
            if(LEVEL==4){
                GameCompleted();
            }else if(saved){
                alert("NEW_LEVEL");
            }
        }else {
            setNewLevel_miscCondition();
            alert("WON");
        }
    }
    public boolean saveData(){
        SUBLEVEL++;
        if(SUBLEVEL==7) {
            if (LEVEL <= 3) {
                LEVEL++;
                if(LEVEL!=4)
                    SUBLEVEL =0;
            }
        }
        SharedPreferences.Editor saveGameState = getApplicationContext().getSharedPreferences("novus", MODE_PRIVATE).edit();
        saveGameState.putInt("MAXLEVEL",LEVEL);
        saveGameState.putInt("MAXSUBLEVEL",SUBLEVEL);
        return saveGameState.commit();
    }
    public void alert(final String code){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(QuizActivity.this);
        LayoutInflater inflater = QuizActivity.this.getLayoutInflater();
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

        switch (code) {
            case "RIGHT":
                title.setText(R.string.TitleOnRight);
                message.setText(R.string.MsgOnRightAns);
                positive.setText(R.string.Next);
                negative.setText(R.string.RateUs);
                break;
            case "WRONG":
                title.setText(R.string.TitleOnWrong);
                message.setText(R.string.MsgOnWrong);
                positive.setText(R.string.Next);
                negative.setText(R.string.RateUs);
                break;
            case "WON":
                title.setText(R.string.TitleOnLevelCom);
                message.setText(R.string.MsgOnLevelCom);
                positive.setText(R.string.Next);
                negative.setText(R.string.ButtonBack);
                show_qn.setText("10");
                break;
            case "LOST":

                title.setText(R.string.TitleOnLevelLost);
                message.setText(R.string.MsgOnLevelLost);
                positive.setText(R.string.ButtonRestart);
                negative.setText(R.string.ButtonBack);
                break;
            case "NEW_LEVEL":
                title.setText(R.string.TitleOnLevelCom);
                message.setText(R.string.MsgOnLevelCom);
                positive.setText(R.string.Next);
                negative.setText(R.string.ButtonBack);
                break;
            case "BACK":
                title.setText(R.string.BackTitle);
                message.setText(R.string.ProgressLost);
                positive.setText(R.string.Ok);
                negative.setText(R.string.Cancel);

        }
        final AlertDialog alert = alertdialog.create();
      /*  Window window =alert.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.BOTTOM|Gravity.CENTER;

        Log.d("aler","adfadsf");
        alert.show();
        Log.d("aler","adfadsf");*/
        alert.setCancelable(false);
        alert.show();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (code) {
                    case "WON":
                        Intent quiz1 = new Intent(QuizActivity.this, QuizActivity.class);
                        quiz1.putExtra("SET_LEVEL",LEVEL);
                        quiz1.putExtra("SET_QUIZ",SUBLEVEL);
                        finish();
                        startActivity(quiz1);


                        break;
                    case "NEW_LEVEL":
                        Intent quiz = new Intent(QuizActivity.this, QuizActivity.class);
                        quiz.putExtra("SET_LEVEL",LEVEL);
                        quiz.putExtra("SET_QUIZ",SUBLEVEL);
                        finish();
                        startActivity(quiz);


                        break;
                    case "LOST":

                        if(inter1.isLoaded())
                            inter1.show();
                        recreate();

                        break;
                    case "RIGHT":

                        value_points = value_points+10;
                        show_points.setText(String.valueOf(value_points));
                        ++value_qn;
                        show_qn.setText(String.valueOf(value_qn));
                        refreshPage();
                        break;
                    case  "WRONG":
                        ++value_qn;
                        show_qn.setText(String.valueOf(value_qn));
                        refreshPage();
                        break;
                    case "BACK":

                        Intent Sublevel = new Intent(QuizActivity.this,LevelActivity.class);
                        Sublevel.putExtra("START_LEVEL",LEVEL);
                        startActivity(Sublevel);
                        if(inter2.isLoaded()) {
                            inter2.show();
                            finish();
                        }
                        else
                        {
                            finish();

                        }

                }
                alert.dismiss();
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.equals("LOST") || code.equals("WON") || code.equals("NEW_LEVEL")){

                    Sublevel = new Intent(QuizActivity.this,LevelActivity.class);
                    Sublevel.putExtra("START_LEVEL",LEVEL);
                    startActivity(Sublevel);

                            if(inter1.isLoaded()) {
                                inter1.show();
                                finish();
                            }
                            else
                            {
                                finish();

                            }



                }else if(code.equals("BACK")){
                    alert.dismiss();
                }else{
                    Intent rateUs = new Intent(Intent.ACTION_VIEW);
                    rateUs.setData(Uri.parse(PLAY_STORE));
                    startActivity(rateUs);
                }
            }
        });
    }
    public void setNewLevel_miscCondition() {
        if(SUBLEVEL<=5){
            SUBLEVEL++;
        }else{
            LEVEL++;
            SUBLEVEL=0;
        }
    }
    public void getQuestions(){
        switch (LEVEL){
            case 0 :
                LevelName = "BIGNNER";
                switch (SUBLEVEL){
                    case 0:
                        Questions = getResources().getStringArray(R.array.bigg_one);
                        return;
                    case 1:
                        Questions = getResources().getStringArray(R.array.bigg_two);
                        return;
                    case 2:
                        Questions = getResources().getStringArray(R.array.bigg_three);
                        return;
                    case 3:
                        Questions = getResources().getStringArray(R.array.bigg_four);
                        return;
                    case 4:
                        Questions = getResources().getStringArray(R.array.bigg_five);
                        return;
                    case 5:
                        Questions = getResources().getStringArray(R.array.bigg_six);
                        return;
                    case 6:
                        Questions = getResources().getStringArray(R.array.bigg_seven);
                }
                return;
            case 1:
                LevelName = "INTERMIDIATE";
                switch (SUBLEVEL){
                    case 0:
                        Questions = getResources().getStringArray(R.array.inter_one);
                        return;
                    case 1:
                        Questions = getResources().getStringArray(R.array.inter_two);
                        return;
                    case 2:
                        Questions = getResources().getStringArray(R.array.inter_three);
                        return;
                    case 3:
                        Questions = getResources().getStringArray(R.array.inter_four);
                        return;
                    case 4:
                        Questions = getResources().getStringArray(R.array.inter_five);

                        return;
                    case 5:
                        Questions = getResources().getStringArray(R.array.inter_six);
                        return;
                    case 6:
                        Questions = getResources().getStringArray(R.array.inter_seven);
                }
                return;
            case 2:
                LevelName = "ADVANCED";
                switch (SUBLEVEL){
                    case 0:
                        Questions = getResources().getStringArray(R.array.advanced_one);
                        return;
                    case 1:
                        Questions = getResources().getStringArray(R.array.advanced_two);
                        return;
                    case 2:
                        Questions = getResources().getStringArray(R.array.advanced_three);
                        return;
                    case 3:
                        Questions = getResources().getStringArray(R.array.advanced_four);
                        return;
                    case 4:
                        Questions = getResources().getStringArray(R.array.advanced_five);
                        return;
                    case 5:
                        Questions = getResources().getStringArray(R.array.advanced_six);
                        return;
                    case 6:
                        Questions = getResources().getStringArray(R.array.advanced_seven);
                }
                return;
                default:

                    LevelName = "FINAL";
                    switch (SUBLEVEL){
                        case 0:
                            Questions = getResources().getStringArray(R.array.final_one);
                            return;
                        case 1:
                            Questions = getResources().getStringArray(R.array.final_two);
                            return;
                        case 2:
                            Questions = getResources().getStringArray(R.array.final_three);
                            return;
                        case 3:
                            Questions = getResources().getStringArray(R.array.final_four);
                            return;
                        case 4:
                            Questions = getResources().getStringArray(R.array.final_five);
                            return;
                        case 5:
                            Questions = getResources().getStringArray(R.array.final_six);
                            return;
                        case 6:
                            Questions = getResources().getStringArray(R.array.final_seven);
                    }


        }
    }
    @Override
    public void onBackPressed(){
        alert("BACK");
    }



    public void GameCompleted(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(QuizActivity.this);
        LayoutInflater inflater = QuizActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog,null,true);
        alertdialog.setView(view);
        TextView title = view.findViewById(R.id.errorTitle);
        TextView message = view.findViewById(R.id.errorMessage);
        Button positive = view.findViewById(R.id.positive);
        Button negative = view.findViewById(R.id.negative);
        title.setText("All Levels Completed");
        message.setText("You have completed the Levels.For solve again,go to home-->options-->reset levels and try again.Thank you");
        positive.setText("Home");
        negative.setText(R.string.RateUs);
        final AlertDialog alert = alertdialog.create();
        alert.setCancelable(false);
        alert.show();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                Intent home = new Intent(QuizActivity.this,HomeActivity.class);
                startActivity(home);
                if(inter2.isLoaded()) {
                    inter2.show();
                    finish();
                }
                else
                {
                    finish();

                }


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
    public  void onPause(){
        super.onPause();
    }
    @Override
    public  void onResume(){
        super.onResume();
    }
}


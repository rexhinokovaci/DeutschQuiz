package com.modex.deutschquiz;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdk;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class QuestionActivity extends AppCompatActivity implements MaxAdViewAdListener {

    private static final long COUNT_DOWN_TIME = 30000;
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILlIS_LEFT = "keyMilisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";
    public static final String EXTRA_SCORE = "extraScore";


    private long backPressed;

    private TextView textViewQuestion, textViewQuestionCount, textViewCountDown, textViewScores;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private RadioGroup radioGroup;
    public int QuestionCounter, QuestionCountTotal, score;
    private QuestionClass currentQuestions;
    private ColorStateList textColorDefaultCD;
    private CountDownTimer countDownTimer;
    private long timeMillis;
    private boolean answered;
    private ArrayList<QuestionClass> questionsClassList;

    String ADV;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        textViewScores = findViewById(R.id.textViewScore);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewQuestionCount = findViewById(R.id.questionCount);
        textViewCountDown = findViewById(R.id.timeView);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);

        textColorDefaultCD = textViewCountDown.getTextColors();

        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, configuration -> {
            // AppLovin SDK is initialized, start loading ads
            createBannerAd();



        });

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(MainActivity.EXTRA_DIFFICULTY);
        Log.d("DDD", difficulty);


        if (savedInstanceState == null) {

            QuizDbHelper quizDbHelper = new QuizDbHelper(this);
            questionsClassList = quizDbHelper.getQuestions(difficulty);
            QuestionCountTotal = questionsClassList.size();
            Collections.shuffle(questionsClassList);

            showNextQuestion();

        } else {
            questionsClassList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            if (questionsClassList == null) {
                finish();
            }
            QuestionCountTotal = questionsClassList.size();
            QuestionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestions = questionsClassList.get(QuestionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeMillis = savedInstanceState.getLong(KEY_MILlIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);


            if (!answered) {
                startCountDown();
            } else {
                updateCountDownText();
                showSolution();
            }
        }


        // the if condition with incremented i = 0; variable


        radioButton1.setOnClickListener(v -> showSolution());


        radioButton2.setOnClickListener(v -> showSolution());

        radioButton3.setOnClickListener(v -> showSolution());

        radioButton4.setOnClickListener(v -> showSolution());



        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "QuestionId");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "QuestionName");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "QuestionImage");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);



    }


    private void showNextQuestion() {

        radioGroup.clearCheck();

        if (QuestionCounter < QuestionCountTotal) {

            currentQuestions = questionsClassList.get(QuestionCounter);
            textViewQuestion.setText(currentQuestions.getQuestion());

            radioButton1.setText(currentQuestions.getOption1());
            radioButton2.setText(currentQuestions.getOption2());
            radioButton3.setText(currentQuestions.getOption3());
            radioButton4.setText(currentQuestions.getOption4());
            QuestionCounter++;
            textViewQuestionCount.setText(format(Locale.ENGLISH, "Question: %d/%d", QuestionCounter, QuestionCountTotal));
            answered = false;
            // confirm

            timeMillis = COUNT_DOWN_TIME;

            startCountDown();
        } else {
            finishQuiz();
        }

    }

    private void startCountDown() {

        countDownTimer = new CountDownTimer(timeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timeMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {

                timeMillis = 0;
                updateCountDownText();
                checkAnswer();
                showNextQuestion();

            }
        }.start();
    }

    private void updateCountDownText() {

        int minutes = (int) (timeMillis / 1000) / 60;
        int seconds = (int) (timeMillis / 1000) % 60;

        String timeFormatted = format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeMillis < 6000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCD);
        }

//
    }

    void createBannerAd()
    {
        MaxAdView adView = new MaxAdView("1a77e71cf3c1a0b0", this);
        adView.setListener( this );

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = getResources().getDimensionPixelSize( R.dimen.banner_height );

        adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx, Gravity.BOTTOM) );

        // Set background or background color for banners to be fully functional
        adView.setBackgroundColor( Color.WHITE);

        ViewGroup rootView = findViewById( android.R.id.content );
        rootView.addView(adView);

        // Load the ad
        adView.loadAd();
    }


    public void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(radioButton) + 1;

        if (answerNo == currentQuestions.getAnswerNr()) {
            score++;

            textViewScores.setText(String.format(Locale.ENGLISH, "Score: %d", score));

        } else if (answerNo != currentQuestions.getAnswerNr()) {
            score--;
            textViewScores.setText(String.format(Locale.ENGLISH, "Score: %d", score));


        }

    }

    private void showSolution() {

        switch (currentQuestions.getAnswerNr()) {
            case 1:
            case 2:
            case 3:
            case 4:
                textViewQuestion.setText(R.string.bugs_work_text_user);
                checkAnswer();
                showNextQuestion();
                break;

        }
    }


    private void finishQuiz() {
        Intent resultsIntent = new Intent();
        resultsIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultsIntent);
        Log.d("FINISH", "USER FINISHED");
        finish();
    }


    @Override
    public void onBackPressed() {


        if (backPressed + 2000 > System.currentTimeMillis()) {
            Log.d("BACK", "BACK PRESSED");
            finishQuiz();

        } else {

            Toast.makeText(QuestionActivity.this, "Press Back To Exit!", Toast.LENGTH_SHORT).show();


        }

        backPressed = System.currentTimeMillis();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putLong(KEY_QUESTION_COUNT, QuestionCounter);
        outState.putLong(KEY_MILlIS_LEFT, timeMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionsClassList);
    }

    @Override
    public void onAdExpanded(MaxAd ad) {

        Log.d("AD","Ad expanded",(Throwable) ad);

    }

    @Override
    public void onAdCollapsed(MaxAd ad) {
        Log.d("AD","Ad cOLLAPSED",(Throwable) ad);


    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        Log.d("AD","Ad Loaded",(Throwable) ad);


    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

        Log.d("AD","Ad expanded",(Throwable) ad);


    }

    @Override
    public void onAdHidden(MaxAd ad) {
        Log.d("AD","Ad is Hidden",(Throwable) ad);


    }

    @Override
    public void onAdClicked(MaxAd ad) {

        Log.d("AD","Ad is clicked", (Throwable) ad);


    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }
}
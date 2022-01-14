package com.modex.deutschquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static java.lang.String.format;

import com.google.firebase.analytics.FirebaseAnalytics;


@SuppressWarnings({"unused", "RedundantSuppression"})
public class QuestionActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private static final long COUNT_DOWN_TIME = 30000;
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILlIS_LEFT = "keyMilisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";
    public static final String EXTRA_SCORE = "extraScore";
    private final String TAG = "";

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
    @SuppressWarnings("unused")
    String ADV;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
//            if (incremented) {
//                timeMillis = 1000;
//            }

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
            textViewCountDown.setTextColor(Color.BLUE);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCD);
        }

//
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

}
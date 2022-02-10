package com.modex.deutschquiz;


import static java.lang.String.format;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;
import java.util.Timer;


@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    private static final int REQ_CODE_QUIZ = 1;
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";
    public static final String SHARED_PRFSS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScoree";
    private int highScore;
    private long backPressed;
    private Dialog twitterDialog;
    private TextView textViewHighScore;
    private Spinner spinnerDifficulty;
    private ViewPager viewPageId;
    private int customPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Please make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );

        textViewHighScore = findViewById(R.id.textView_highScore);
        loadHighscore();

        spinnerDifficulty = findViewById(R.id.spinner_difficulty);



        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "MainId");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MainName");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "MainImage");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        viewPageId = findViewById(R.id.viewPageId);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPageId.setAdapter(viewPagerAdapter);




        //This is the VIEW PAGE for slides

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(), 2000, 5000);

        //Checking the Difficulty Levels

        String[] difficultyLevels = QuestionClass.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficultyLevels);

        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);




    }

    public class TimerTask extends java.util.TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(() -> {

                viewPageId.setCurrentItem(customPosition % 20);
                customPosition++;

            });

        }
    }

    public void openConfirmation(View view) {

        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);

        try {
            startActivityForResult(intent, REQ_CODE_QUIZ);
        }
        catch (Exception e) {
            Toast.makeText(MainActivity.this,"Please Select Difficulty!",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuestionActivity.EXTRA_SCORE,0);
                if (score > highScore) {
                    updateHighscore(score);
                }
            }
        }
    }

    public void updateHighscore(int highScoreTwo) {
        highScore = highScoreTwo;
        textViewHighScore.setText(format(Locale.ENGLISH,"High Score: %d", highScore));

        SharedPreferences preferences = getSharedPreferences(SHARED_PRFSS,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_HIGHSCORE, highScore);
        editor.apply();

    }

    public void loadHighscore() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRFSS, MODE_PRIVATE);
        highScore = sharedPreferences.getInt(KEY_HIGHSCORE, 0);
        textViewHighScore.setText(format(Locale.ENGLISH,"High Score: %d", highScore));

    }




    @Override
    public void onBackPressed() {

        if (backPressed + 2000 > System.currentTimeMillis()) {
            finish();

        } else {

            Toast.makeText(MainActivity.this, "Press Back To Exit!", Toast.LENGTH_SHORT).show();

        }

        backPressed = System.currentTimeMillis();

        Log.d("BACK","BACk pressed");

    }

}


class ViewPagerAdapter extends PagerAdapter {

    private final Context context;
    private final Integer [] imagesViewPage = {


    };

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imagesViewPage.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewPager viewPager = (ViewPager) container;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.customlayoutview, viewPager,false);
        ImageView imageView = view.findViewById(R.id.imageView2);
        imageView.setImageResource(imagesViewPage[position]);

        viewPager.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }

}
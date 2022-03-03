package com.modex.deutschquiz;


import static java.lang.String.format;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinPrivacySettings;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinUserService;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Collections;
import java.util.Locale;
import java.util.Timer;


public class MainActivity extends AppCompatActivity implements MaxAdViewAdListener {

    private static final int REQ_CODE_QUIZ = 1;
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";
    public static final String SHARED_PRFSS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScoree";
    private int highScore;
    private long backPressed;
    private TextView textViewHighScore;
    private Spinner spinnerDifficulty;
    private ViewPager viewPageId;
    private int customPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        AppLovinPrivacySettings.setIsAgeRestrictedUser( false, MainActivity.this );

        AppLovinUserService userService = AppLovinSdk.getInstance( this ).getUserService();
        userService.showConsentDialog( this, () -> AppLovinPrivacySettings.setHasUserConsent( false, MainActivity.this ));

        // Please make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, configuration -> {
            // AppLovin SDK is initialized, start loading ads
            createBannerAd();



        });


        textViewHighScore = findViewById(R.id.textView_highScore);
        loadHighscore();

        spinnerDifficulty = findViewById(R.id.spinner_difficulty);



        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "MainId");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MainName");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "MainImage");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        AppLovinSdk.getInstance(MainActivity.this).getSettings().setTestDeviceAdvertisingIds(Collections.singletonList("44ea266f-38ce-4f11-93e1-10ca1ad1abe1"));

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


    @Override
    public void onAdExpanded(MaxAd ad) {
        Log.d("AD","Ad expanded",(Throwable) ad);



    }

    @Override
    public void onAdCollapsed(MaxAd ad) {
        Log.d("AD","Ad Collapsed",(Throwable) ad);

    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        Log.d("AD","Ad Loaded",(Throwable) ad);
    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

        Log.d("AD","Ad Displayed",(Throwable) ad);



    }

    @Override
    public void onAdHidden(MaxAd ad) {
        Log.d("AD","Ad hidden",(Throwable) ad);

    }

    @Override
    public void onAdClicked(MaxAd ad) {
        Log.d("AD","Ad Hidden",(Throwable) ad);

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void openConfirmation(View view) {
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);

        try {
            //noinspection deprecation
            startActivityForResult(intent, REQ_CODE_QUIZ);
        }
        catch (Exception e) {
            Toast.makeText(MainActivity.this,"Please Select Difficulty!",Toast.LENGTH_SHORT).show();
        }


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


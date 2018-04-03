package com.spade.nrc.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.spade.nrc.R;
import com.spade.nrc.ui.channel.presenter.ChannelsPresenter;
import com.spade.nrc.ui.channel.presenter.ChannelsPresenterImpl;
import com.spade.nrc.ui.channel.view.ChannelsView;
import com.spade.nrc.ui.login.view.LoginActivity;
import com.spade.nrc.ui.main.MainActivity;
import com.spade.nrc.utils.LoginProviders;
import com.spade.nrc.utils.PrefUtils;

/**
 * Created by spade on 6/7/17.
 */

public class SplashActivity extends AppCompatActivity implements ChannelsView {

    private static final long DELAY_MILLIS = 1000;
    private ProgressBar progressBar;
    private ChannelsPresenter channelsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        changeLanguage();
//        saveNotificationToken();
//        counterToNavigate();
        init();
    }


    private void init() {
        channelsPresenter = new ChannelsPresenterImpl(this);
        channelsPresenter.setView(this);
        progressBar = findViewById(R.id.progress_bar);
        channelsPresenter.getChannels();
    }

    private void counterToNavigate() {
        Handler handler = new Handler();
        Runnable runnable = this::navigate;
        handler.postDelayed(runnable, DELAY_MILLIS);
    }

    private void navigate() {
        int loginProvider = PrefUtils.getLoginProvider(this);
        if (loginProvider == LoginProviders.NONE.getLoginProviderCode()) {
            startActivity(LoginActivity.getLaunchIntent(this));
        } else {
            startActivity(MainActivity.getLaunchIntent(this));
        }
        PrefUtils.setIsFirstLaunch(this, false);
        finish();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resID) {

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setError(EditText editText, int resId) {

    }

    @Override
    public void channelsLoaded() {
        navigate();
    }

//    @SuppressWarnings("deprecation")
//    public void changeLanguage() {
//        Locale locale;
//        if (PrefUtils.isLanguageSelected(this)) {
//            if (PrefUtils.getAppLang(this).equals(PrefUtils.ARABIC_LANG)) {
//                locale = new Locale(MorePresenterImpl.AR_LANG);
//            } else {
//                locale = new Locale(MorePresenterImpl.EN_LANG);
//            }
//            Configuration conf = new Configuration();
//            conf.locale = locale;
//            getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
//        } else {
//            String deviceLang = Locale.getDefault().getLanguage();
//            if (!deviceLang.equals(PrefUtils.ARABIC_LANG)) {
//                PrefUtils.setAppLang(this, PrefUtils.ENGLISH_LANG);
//            } else {
//                PrefUtils.setAppLang(this, PrefUtils.ARABIC_LANG);
//            }
//        }
//    }
//
//    private void saveNotificationToken() {
//        if (!PrefUtils.isTokenSaved(this)) {
//            progressBar.setVisibility(View.VISIBLE);
//            ApiHelper.saveToken(PrefUtils.getNotificationToken(this), new ApiHelper.SaveTokenCallBacks() {
//                @Override
//                public void onTokenSavedSuccess() {
//                    PrefUtils.setIsTokenSaved(getApplicationContext(), true);
//                    progressBar.setVisibility(View.GONE);
//                    navigate();
////                    counterToNavigate();
//                }
//
//                @Override
//                public void onTokenSavedFailed(String error) {
//                    PrefUtils.setIsTokenSaved(getApplicationContext(), false);
//                    progressBar.setVisibility(View.GONE);
//                    navigate();
////                    counterToNavigate();
//                }
//            });
//        } else {
//            counterToNavigate();
//        }
//    }

//    public static Intent getLaunchIntent(Context context) {
//        return new Intent(context, SplashActivity.class);
//    }
}

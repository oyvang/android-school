package org.oyvang.hangman;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;


public class MainMenu extends Activity {

    private String TAG = "\tMY_MainMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void onClickBritthisPlayMenu(View view) {
        Locale locale = new Locale("en", "en_GB");
        Configuration config = new Configuration();
        config.locale = locale;
        Resources res = getBaseContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
        startPlayMenu();
    }

    public void onClickNorwegianPlayMenu(View view) {
        Locale locale = new Locale("no", "NO");
        Configuration config = new Configuration();
        config.locale = locale;
        Resources res = getBaseContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
        startPlayMenu();
    }

    public void onClickStartGame(View view) {
        startActivity(new Intent(this, org.oyvang.hangman.HangmanActivity.class));
        finish();
    }

    public void onClickMainMenu(View view) {
        setContentView(R.layout.main_menu);
    }

    public void onClickExit(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        /** Disable the go-back button on android phone. Not every phone has
         *  this button or they might remove it later phones. Uses only in-application
         *  buttons to go through the application.
         */

    }

    private void startPlayMenu() {
        setContentView(R.layout.play_menu);
    }
}

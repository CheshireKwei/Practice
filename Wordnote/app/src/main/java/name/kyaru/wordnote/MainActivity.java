package name.kyaru.wordnote;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import name.kyaru.wordnote.datastruct.Preference;
import name.kyaru.wordnote.utils.ActivityManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playVideo();
        initPreference();
        turnToMainPage();
        ActivityManager.put(this);
    }

    private void playVideo(){

    }

    private void initPreference(){
        SharedPreferences preferences = getSharedPreferences(Preference.NAME, MODE_PRIVATE);
        Preference.recentId = preferences.getInt(Preference.KEY_RECENTLY_ID, 0);
    }

    private void turnToMainPage(){
        Intent launcher = new Intent();
        launcher.setClass(this, MainPageActivity.class);
        startActivity(launcher);
    }
}
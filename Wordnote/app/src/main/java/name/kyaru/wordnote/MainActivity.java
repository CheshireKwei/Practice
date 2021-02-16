package name.kyaru.wordnote;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

/* 启动界面 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playVideo();
        turnToMainPage();
        finish();
    }

    private void playVideo(){

    }

    private void turnToMainPage(){
        Intent launcher = new Intent();
        launcher.setClass(this, MainPageActivity.class);
        startActivity(launcher);
    }
}
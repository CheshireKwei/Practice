package name.kyaru.wordnote;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import name.kyaru.wordnote.bgm.EffectManager;

/* 启动界面 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        playVideo();
        loadResource();
    }

    private void playVideo(){

    }

    private void loadResource(){
        EffectManager.getInstance(this, () -> {
            forward();
            finish(); //finish()需在资源加载后再调用，否则当MainPage还未启动时，此Activity销毁后程序已结束
        }); //加载完成后转到主页
    }

    //跳转到主页
    private void forward(){
        Intent launcher = new Intent();
        launcher.setClass(this, MainPageActivity.class);
        startActivity(launcher);
    }
}
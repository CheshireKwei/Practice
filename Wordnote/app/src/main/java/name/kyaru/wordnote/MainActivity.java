package name.kyaru.wordnote;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import name.kyaru.wordnote.bgm.EffectManager;

/* 启动界面 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        playVideo();
        mRequestPermissions();
        loadResource();
    }

    //播放动画，暂时保留
    private void playVideo(){

    }

    //申请权限，暂时保留
    private void mRequestPermissions() {

    }

    //加载音效资源
    private void loadResource() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
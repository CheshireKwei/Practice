package name.kyaru.clickx;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button turnToSettings;
    private View test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Toast.makeText(this, "欢迎使用猫猫头连点器", Toast.LENGTH_SHORT).show();
    }

    private void initView(){
        turnToSettings = findViewById(R.id.turn_to_settings);
        turnToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Settings.canDrawOverlays( MainActivity.this)){ //是否可开启悬浮窗
                    Toast.makeText(MainActivity.this, "请开启猫猫头连点器", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)); //跳转到无障碍服务
                }else {
                    Toast.makeText(MainActivity.this, "请开启悬浮窗权限", Toast.LENGTH_SHORT).show();;
                    requestPermission();
                }
            }
        });
    }

    private void requestPermission(){ //请求悬浮窗权限
        startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
    }
}
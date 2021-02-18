package name.kyaru.wordnote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import name.kyaru.wordnote.bgm.EffectManager;
import name.kyaru.wordnote.datastruct.ReviewPreference;

public class SettingActivity extends AppCompatActivity {
    private ImageButton clickChangeMusicState;
    private ImageButton clickBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        initView();
    }

    private void initView() {
        clickChangeMusicState = findViewById(R.id.click_change_music_state);
        clickBack = findViewById(R.id.click_back);

        //设置监听
        OnClickListenerImpl onClickImpl = new OnClickListenerImpl();
        clickChangeMusicState.setOnClickListener(onClickImpl);
        clickBack.setOnClickListener(onClickImpl);

        //检查首选项的音效属性是否为关闭
        checkPreferenceMusicState();
    }

    private void changeMusicState(){
        if(EffectManager.getInstance().isDoNotPlay()) { //如果已关闭音效
            clickChangeMusicState.setImageResource(R.drawable.music_open);
            EffectManager.getInstance().setDoNotPlay(false);
            ReviewPreference.isDoNotPlay = false;
        }else{
            clickChangeMusicState.setImageResource(R.drawable.music_done);
            EffectManager.getInstance().setDoNotPlay(true);
            ReviewPreference.isDoNotPlay = true;
        }
    }

    private void checkPreferenceMusicState(){
        if(ReviewPreference.isDoNotPlay){ //如果关闭了音效
            clickChangeMusicState.setImageResource(R.drawable.music_done);
        }else{
            clickChangeMusicState.setImageResource(R.drawable.music_open);
        }
    }

    private class OnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click_change_music_state:
                    changeMusicState();
                    break;
                case R.id.click_back:
                    finish();
                    break;
            }
        }
    }
}
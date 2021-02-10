package name.kyaru.wordnote;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import name.kyaru.wordnote.utils.ActivityManager;

public class MainPageActivity extends AppCompatActivity {
    private ImageButton clickExit;
    private ImageButton clickRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        initView();
        ActivityManager.put(this);
    }

    private void initView() {
        clickRecord = findViewById(R.id.click_review_word);
        clickExit = findViewById(R.id.click_exit);

        OnClickListenerImpl ocImpl = new OnClickListenerImpl();

        clickRecord.setOnClickListener(ocImpl);
        clickExit.setOnClickListener(ocImpl); //结束所有活动，完全退出
    }

    private class OnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click_record_word:

                    break;
                case R.id.click_exit:
                    ActivityManager.finishAll();
                    break;
            }
        }
    }
}

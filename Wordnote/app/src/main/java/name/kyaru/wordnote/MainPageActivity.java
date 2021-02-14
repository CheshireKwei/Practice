package name.kyaru.wordnote;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.Preference;
import name.kyaru.wordnote.utils.DatabaseCreator;

public class MainPageActivity extends AppCompatActivity {
    private ImageButton clickExit;
    private ImageButton clickRecord;
    private DatabaseCreator creator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        initView();
        initDatabase();
    }

    private void initDatabase() {
        creator = new DatabaseCreator(this, DatabaseCreator.DBNAME_WORDS, null, 1);
        WordDao.setDatabase(creator.getWritableDatabase());
        Preference.loadPreference(this);
    }

    private void initView() {
        clickRecord = findViewById(R.id.click_record_word);
        clickExit = findViewById(R.id.click_exit);

        //添加监听
        OnClickListenerImpl ocImpl = new OnClickListenerImpl();
        clickRecord.setOnClickListener(ocImpl);
        clickExit.setOnClickListener(ocImpl); //结束所有活动，完全退出
    }

    private class OnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click_record_word:
                    Intent launcher = new Intent(MainPageActivity.this, RecordWordActivity.class);
                    startActivity(launcher);
                    break;
                case R.id.click_exit:
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Preference.savePreference(this);
        creator.close();
        WordDao.setDatabase(null);
    }
}

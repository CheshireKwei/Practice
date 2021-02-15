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

/* 主菜单界面 */
public class MainPageActivity extends AppCompatActivity {
    private ImageButton clickExit;
    private ImageButton clickRecord;
    private ImageButton clickExploreAll;
    private ImageButton clickReview;
    private ImageButton clickRandReview;
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
        clickReview = findViewById(R.id.click_review_word);
        clickRandReview = findViewById(R.id.click_review_rand_word);
        clickExploreAll = findViewById(R.id.click_explore_all);
        clickExit = findViewById(R.id.click_exit);

        //添加监听
        OnClickListenerImpl ocImpl = new OnClickListenerImpl();
        clickRecord.setOnClickListener(ocImpl);
        clickReview.setOnClickListener(ocImpl);
        clickRandReview.setOnClickListener(ocImpl);
        clickExploreAll.setOnClickListener(ocImpl);
        clickExit.setOnClickListener(ocImpl); //结束所有活动，完全退出
    }

    private class OnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent launcher = null;

            switch (v.getId()){
                case R.id.click_record_word:
                    launcher = new Intent(MainPageActivity.this, RecordWordActivity.class);
                    startActivity(launcher);
                    break;
                case R.id.click_explore_all:
                    launcher = new Intent(MainPageActivity.this, ExploreActivity.class);
                    launcher.putExtra(ExploreActivity.KEY_MODE_NAME, WordDao.MODE_ALL); //以ALL模式启动ExploreActivity，查询并显示所有单词
                    startActivity(launcher);
                    break;
                case R.id.click_review_word:
                    launcher = new Intent(MainPageActivity.this, ReviewActivity.class);
                    launcher.putExtra(ExploreActivity.KEY_MODE_NAME, ReviewActivity.MODE_LAST_WORDS); //以LAST_WORDS模式启动ExploreActivity，复习前一天的单词
                    startActivity(launcher);
                    break;
                case R.id.click_review_rand_word:
                    launcher = new Intent(MainPageActivity.this, ReviewActivity.class);
                    launcher.putExtra(ExploreActivity.KEY_MODE_NAME, ReviewActivity.MODE_RAND_WORDS); //以RAND_WORDS模式启动ExploreActivity，随机复习以前的单词
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

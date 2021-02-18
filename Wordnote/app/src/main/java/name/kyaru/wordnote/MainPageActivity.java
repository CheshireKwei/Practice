package name.kyaru.wordnote;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import name.kyaru.wordnote.bgm.EffectManager;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.ReviewPreference;
import name.kyaru.wordnote.datastruct.Word;
import name.kyaru.wordnote.utils.BasicTool;
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
        dataMove();
    }

    //根据是否是同一天来移动数据
    private void dataMove() {
        if(!isSameDay()){ //不是同一天，移动单词
            List<Word> words = WordDao.query(null, WordDao.MODE_ALL, WordDao.TABLE_LAST_WORDS);
            if(words != null) { //如果前一天没有单词，则不移动
                for (Word w : words) {
                    WordDao.insert(w, WordDao.TABLE_WORDS); //将前一天的单词移动到常驻表
                }
                WordDao.delete(null, WordDao.MODE_ALL, WordDao.TABLE_LAST_WORDS); //删除所有单词
            }
        }
    }

    private boolean isSameDay(){
        long t1 = ReviewPreference.time;
        long t2 = System.currentTimeMillis();
        boolean b = BasicTool.isSameDay(t1, t2);
        if(!b){
            ReviewPreference.time = t2; //不是同一天则保存当天时间，供后面比较
        }

        return b;
    }

    //初始化数据库
    private void initDatabase() {
        creator = new DatabaseCreator(this, DatabaseCreator.DBNAME_WORDS, null, 1);
        WordDao.setDatabase(creator.getWritableDatabase());
        ReviewPreference.loadPreference(this); //加载首选项
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
                    launcher.putExtra(ExploreActivity.KEY_MODE, WordDao.MODE_ALL); //以ALL模式启动ExploreActivity，查询并显示所有单词
                    startActivity(launcher);
                    break;
                case R.id.click_review_word:
                    EffectManager.getInstance().play(EffectManager.TYPE_BEGIN); //播放音效
                    launcher = new Intent(MainPageActivity.this, ReviewActivity.class);
                    launcher.putExtra(ExploreActivity.KEY_MODE, ReviewActivity.MODE_LAST_WORDS); //以LAST_WORDS模式启动ExploreActivity，复习前一天的单词
                    startActivity(launcher);
                    break;
                case R.id.click_review_rand_word:
                    EffectManager.getInstance().play(EffectManager.TYPE_BEGIN); //播放音效
                    launcher = new Intent(MainPageActivity.this, ReviewActivity.class);
                    launcher.putExtra(ReviewActivity.KEY_MODE, ReviewActivity.MODE_RAND_WORDS); //以RAND_WORDS模式启动ExploreActivity，随机复习以前的单词
                    startActivity(launcher);
                    break;
                case R.id.click_exit:
                    finish();
                    break;
            }
        }
    }

    //关闭数据库，保存首选项
    @Override
    protected void onDestroy() {
        ReviewPreference.savePreference(this);
        creator.close();
        WordDao.setDatabase(null);
        EffectManager.getInstance().release();
        super.onDestroy();
        int pid = android.os.Process.myPid(); //获取进程pid
        android.os.Process.killProcess(pid); //杀死进程
    }
}

package name.kyaru.wordnote;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.Word;
import name.kyaru.wordnote.datastruct.WordAdapter;

/* 单词查询和浏览界面 */
public class ExploreActivity extends AppCompatActivity {
    public static final String KEY_MODE = "mode";
    public static final String KEY_EN = "en";
    public static final String KEY_CN = "cn";
    private ImageButton clickBack;
    private TextView showTitle;
    private TextView showMessage;
    private RecyclerView showWords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        branch();
    }

    private void branch(){ //根据是否有单词可显示来分支
        Summary sum = resolveIntent();
        List<Word> words = queryWord(sum);
        if(words != null){ //有单词时，设置正常的布局和功能
            setContentView(R.layout.explore);
            initView();
            showTitleFor(sum.getMode());
            WordAdapter adp = new WordAdapter(this, words);
            showWords.setAdapter(adp);
        }else{ //无单词可浏览时，设置显示消息的布局
            setContentView(R.layout.message);
            initViewOnNoWords();
            showMessage.setText("没有可浏览的单词");
        }
    }

    private void showTitleFor(int mode){
        if(mode == WordDao.MODE_ALL){
            showTitle.setText("浏览单词");
        }else{
            showTitle.setText("查找结果");
        }
    }

    private List<Word> queryWord(Summary sum){
        List<Word> words = null;
        List<Word> lastWords = null;

        //查询
        switch (sum.getMode()){
            case WordDao.MODE_ALL:
                words = WordDao.query(null, sum.getMode(), WordDao.TABLE_WORDS);
                lastWords = WordDao.query(null, sum.getMode(), WordDao.TABLE_LAST_WORDS);
                break;
            case WordDao.MODE_EN_AND_CN:
            case WordDao.MODE_ONLY_EN:
            case WordDao.MODE_ONLY_CN:
            case WordDao.MODE_EN_OR_CN:
                words = WordDao.query(sum.toWord(), sum.getMode(), WordDao.TABLE_WORDS);
                lastWords = WordDao.query(sum.toWord(), sum.getMode(), WordDao.TABLE_LAST_WORDS);
                break;
            default:
                showTitle.setText("无单词");
                break;
        }

        //检查查询结果
        if(words != null && lastWords != null) {
            words.addAll(lastWords);
        }else if(words == null && lastWords != null){
            words = lastWords;
        }

        return words;
    }

    private Summary resolveIntent(){
        Intent origin = getIntent();
        if(origin != null){
            return new Summary(origin.getStringExtra(KEY_EN), origin.getStringExtra(KEY_CN), origin.getIntExtra(KEY_MODE, WordDao.MODE_ALL));
        }else{
            throw new RuntimeException("Intent must be not null");
        }
    }

    private void initView() {
        clickBack = findViewById(R.id.click_back);
        showWords = findViewById(R.id.show_words);
        showTitle = findViewById(R.id.show_title);

        //配置RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        showWords.setLayoutManager(layoutManager);
        showWords.setItemAnimator(new DefaultItemAnimator());
        showWords.setHasFixedSize(true);

        //设置监听
        OnClickListenerImpl onClickImpl = new OnClickListenerImpl();
        clickBack.setOnClickListener(onClickImpl);
    }

    private void initViewOnNoWords(){
        clickBack = findViewById(R.id.click_back);
        showMessage = findViewById(R.id.show_message);

        //设置监听
        OnClickListenerImpl onClickImpl = new OnClickListenerImpl();
        clickBack.setOnClickListener(onClickImpl);
    }

    private class OnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click_back:
                    finish();
                    break;
            }
        }
    }

    private class Summary{
        private String en;
        private String cn;
        private int mode;

        public Summary(String en, String cn, int mode){
            this.en = en;
            this.cn = cn;
            this.mode = mode;
        }

        public Word toWord(){
            return new Word(en, cn, 0);
        }

        public int getMode() {
            return mode;
        }
    }
}

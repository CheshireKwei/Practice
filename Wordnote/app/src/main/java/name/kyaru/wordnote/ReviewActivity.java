package name.kyaru.wordnote;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.Word;
import name.kyaru.wordnote.utils.AbsSelectionGenerator;
import name.kyaru.wordnote.utils.BasicTool;
import name.kyaru.wordnote.utils.CnSelectionGenerator;

/* 复习前一天和当天的单词，对应表TABLE_LAST_WORDS */
public class ReviewActivity extends AppCompatActivity {
    public static final String KEY_MODE_NAME = "mode";
    public static final int MODE_LAST_WORDS = 0;
    public static final int MODE_RAND_WORDS = 1;
    private ImageButton clickBack;
    private TextView showEn;
    private Button clickMean1;
    private Button clickMean2;
    private Button clickMean3;
    private ImageView showResultImg;
    private Button clickNext;
    private List<Word> words;
    private int beginIndex = 0;
    private int nextIndex = 0;
    private AbsSelectionGenerator sGenerator;
    private List<String> selections;
    private String anwser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        initView();
        obtainWords();
        layFirstSelection();
    }

    //获取单词
    private void obtainWords() {
        Intent task = getIntent();
        int mode = task.getIntExtra(KEY_MODE_NAME, -1);
        String table = null;
        switch (mode){
            case MODE_LAST_WORDS: //获取前一天全部单词
                table = WordDao.TABLE_LAST_WORDS;
                break;
            case MODE_RAND_WORDS: //以前的单词
                table = WordDao.TABLE_WORDS;
        }
        words = WordDao.query(null, WordDao.MODE_ALL, table); //获取单词
        sGenerator = new CnSelectionGenerator(); //获取中文选项生成器
    }

    //初始化视图
    private void initView() {
        clickBack = findViewById(R.id.click_back);
        showEn = findViewById(R.id.show_en);
        clickMean1 = findViewById(R.id.click_mean_1);
        clickMean2 = findViewById(R.id.click_mean_2);
        clickMean3 = findViewById(R.id.click_mean_3);
        showResultImg = findViewById(R.id.show_result_img);
        clickNext = findViewById(R.id.click_next);

        //设置监听
        OnClickListenerImpl onClickImpl = new OnClickListenerImpl();
        clickBack.setOnClickListener(onClickImpl);
        clickMean1.setOnClickListener(onClickImpl);
        clickMean2.setOnClickListener(onClickImpl);
        clickMean3.setOnClickListener(onClickImpl);
        clickNext.setOnClickListener(onClickImpl);
    }

    //部署第一个选项
    private void layFirstSelection() {
        if(words != null){
            beginIndex = BasicTool.generateRandNumber(words.size());
            nextIndex = beginIndex + 1;
            selections = sGenerator.generate(words, 3, beginIndex, true);
            anwser = words.get(beginIndex).getCn();
            applySelection();
        }else{ //没有单词则跳转到结算界面并显示信息
            Intent launcher = new Intent(this, ResultActivity.class);
            launcher.putExtra(ResultActivity.KEY_MODE_NAME, ResultActivity.MODE_SHOW_MESSAGE);
            launcher.putExtra(ResultActivity.KEY_MESSAGE_NAME, "没有可复习的单词");
            startActivity(launcher);
            finish();
        }
    }

    //部署下一个选项
    private void layNextSelection(){
        if(nextIndex == beginIndex) {//复习完所有单词，结算
            //todo

            finish();
            return;
        }
        if(nextIndex == words.size()){//重置
            nextIndex = 0;
        }
        anwser = words.get(nextIndex).getCn(); //保留答案
        selections = sGenerator.generate(words, 3, nextIndex++, true); //随机生成两个其他单词的中文
        //部署选项
        applySelection();
    }

    private void disposeSelection(int id){
        switch (id){
            case R.id.click_mean_1:
                if(clickMean1.getText().equals(anwser)){
                    clickMean1.setBackgroundColor(Color.GREEN);
                }else{
                    clickMean1.setBackgroundColor(Color.RED);
                }
                break;
            case R.id.click_mean_2:
                if(clickMean2.getText().equals(anwser)){
                    clickMean2.setBackgroundColor(Color.GREEN);
                }else{
                    clickMean2.setBackgroundColor(Color.RED);
                }
                break;
            case R.id.click_mean_3:
                if(clickMean3.getText().equals(anwser)){
                    clickMean3.setBackgroundColor(Color.GREEN);
                }else{
                    clickMean3.setBackgroundColor(Color.RED);
                }
                break;
        }

    }

    private void applySelection(){
        clickMean1.setText(selections.get(0));
        clickMean2.setText(selections.get(1));
        clickMean3.setText(selections.get(2));
    }

    private class OnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click_back: //返回按钮
                    finish();
                    break;
                case R.id.click_mean_1: //第一个意思
                case R.id.click_mean_2: //第二个意思
                case R.id.click_mean_3: //第三个意思
                    disposeSelection(v.getId()); //当点击任意意思按钮时，获取id并判断结果
                    break;
                case R.id.click_next: //下一个
                    layNextSelection();
                    break;
            }
        }
    }
}

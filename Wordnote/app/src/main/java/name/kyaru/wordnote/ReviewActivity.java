package name.kyaru.wordnote;
import android.content.Intent;
import android.graphics.Color;
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
    public static final String KEY_MODE = "mode";
    public static final int MODE_LAST_WORDS = 0;
    public static final int MODE_RAND_WORDS = 1;
    private ImageButton clickBack;
    private TextView showEn;
    private TextView showMessage;
    private TextView showRetainNum;
    private TextView showAnswer;
    private Button clickNext;
    private Button clickMean1;
    private Button clickMean2;
    private Button clickMean3;
    private List<Word> words;
    private AbsSelectionGenerator sGenerator;
    private List<String> selections;
    private String anwser;
    private int beginIndex = 0;
    private int nextIndex = -1;
    private int mode;
    private int correctNum = 0;
    private int reviewTotalNum = 0;
    private boolean afterPick = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainWords();
        checkWordNum();
    }

    private void checkWordNum(){
        if(words != null){
            setContentView(R.layout.review);
            initView();
            layFirstSelection();
        }else{
            setContentView(R.layout.message);
            initViewOnNoWord();
            showMessage.setText("没有可复习的单词");
        }
    }

    //获取单词
    private void obtainWords() {
        Intent task = getIntent();
        mode = task.getIntExtra(KEY_MODE, -1);
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

    //没有单词时，初始化另一界面
    private void initViewOnNoWord(){
        showMessage = findViewById(R.id.show_message);
        clickBack = findViewById(R.id.click_back);

        OnClickListenerImpl onClickImpl = new OnClickListenerImpl();
        clickBack.setOnClickListener(onClickImpl);
    }

    //初始化视图
    private void initView() {
        clickBack = findViewById(R.id.click_back);
        clickMean1 = findViewById(R.id.click_mean_1);
        clickMean2 = findViewById(R.id.click_mean_2);
        clickMean3 = findViewById(R.id.click_mean_3);
        clickNext = findViewById(R.id.click_next);
        showAnswer = findViewById(R.id.show_answer);
        showEn = findViewById(R.id.show_en);
        showRetainNum = findViewById(R.id.show_retain_num);

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
            beginIndex = BasicTool.generateRandNumber(words.size()); //获取一个随机的单词索引
            selections = sGenerator.generate(words, 3, beginIndex, true);
            anwser = words.get(beginIndex).getCn();
            reviewTotalNum++; //增加复习总数

            //部署选项
            showEn.setText(words.get(beginIndex).getEn()); //设置英文
            showRetainNum.setText("剩余" + (words.size() - reviewTotalNum) + "个");
            changeNextIndex();
            applySelection();
        }
    }

    //结算
    private void clearing(){
        Intent launcher = new Intent(this, ResultActivity.class);
        launcher.putExtra(ResultActivity.KEY_MODE, ResultActivity.MODE_CLEARING);
        launcher.putExtra(ResultActivity.KEY_CORRECT_COUNT, correctNum);
        launcher.putExtra(ResultActivity.KEY_TOTAL_COUNT, reviewTotalNum);
        startActivity(launcher);
        finish();
    }

    //部署下一个选项
    private void layNextSelection(){
        if(nextIndex == beginIndex) { //单词遍历完毕后结算
            clearing();
            return;
        }
        anwser = words.get(nextIndex).getCn(); //保留答案
        selections = sGenerator.generate(words, 3, nextIndex, true); //随机生成两个其他单词的中文
        reviewTotalNum++; //增加复习总数

        //部署选项
        showEn.setText(words.get(nextIndex - 1).getEn()); //设置英文
        showRetainNum.setText("剩余" + (words.size() - reviewTotalNum) + "个"); //设置剩余个数
        changeNextIndex(); //改变nextIndex到下一个单词
        applySelection();
    }

    private void changeNextIndex(){
        if(nextIndex == -1){ //为nextIndex初始化
            nextIndex = beginIndex + 1;
        }
        if(nextIndex < words.size()){ //由于beginIndex可能是边界，所以当nextIndex小于单词数目时才递增
            nextIndex++;
        }
        nextIndex = nextIndex % words.size(); //限制大小
    }

    //核对选项
    private void checkSelection(Button btn){
        afterPick = true; //选择后不可再选
        if(btn.getText().equals(anwser)){
            btn.setBackgroundResource(R.drawable.true_selection_bg);
            showAnswer.setText("正确");
            showAnswer.setTextColor(Color.GREEN);
            correctNum++; //增加正确数
        }else{
            btn.setBackgroundResource(R.drawable.false_selection_bg);
            showAnswer.setText("应选择“" + anwser + "”");
            showAnswer.setTextColor(Color.RED);
        }
    }

    //布置界面
    private void applySelection(){
        //设置文字
        clickMean1.setText(selections.get(0));
        clickMean2.setText(selections.get(1));
        clickMean3.setText(selections.get(2));

        //还原界面
        clickMean1.setBackgroundResource(R.drawable.not_chose_bg);
        clickMean2.setBackgroundResource(R.drawable.not_chose_bg);;
        clickMean3.setBackgroundResource(R.drawable.not_chose_bg);

        //还原状态S
        afterPick = false;

        //如果下一次即将结算，则改变按钮文字
        if(nextIndex == beginIndex){
            clickNext.setText("结算");
        }
    }

    private class OnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click_back: //返回按钮
                    if(words != null) { //有单词时结算
                        clearing();
                    }else{ //没有单词直接退出
                        finish();
                    }
                    break;
                case R.id.click_mean_1: //第一个意思
                    if(!afterPick) {
                        checkSelection(clickMean1);
                    }
                    break;
                case R.id.click_mean_2: //第二个意思
                    if(!afterPick) {
                        checkSelection(clickMean2);
                    }
                    break;
                case R.id.click_mean_3: //第三个意思
                    if(!afterPick) {
                        checkSelection(clickMean3);
                    }
                    break;
                case R.id.click_next: //下一个
                    layNextSelection();
                    break;
            }
        }
    }
}

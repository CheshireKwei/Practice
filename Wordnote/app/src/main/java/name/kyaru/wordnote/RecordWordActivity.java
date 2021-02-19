package name.kyaru.wordnote;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.Word;
import name.kyaru.wordnote.utils.BasicTool;

/* 记录单词 */
public class RecordWordActivity extends AppCompatActivity {
    private static final int LIMIT_CHAR_NUM = 15;
    private static final int MODE_CHECK_ONLY_EN = 0; //英文是否为空
    private static final int MODE_CHECK_ONLY_CN = 1; //中文是否为空
    private static final int MODE_CHECK_EN_OR_CN = 2; //英文或中文是否一个不为空
    private static final int MODE_CHECK_EN_AND_CN = 3; //中英文都不为空
    private ImageButton clickBack;
    private EditText inputEn;
    private EditText inputCn;
    private RadioGroup rgroup;
    private int checkedId = R.id.single_en_or_cn;
    private ImageButton clickSearch;
    private ImageButton clickUpdate;
    private ImageButton clickRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_word);
        initView();
    }

    private void initView(){
        clickBack = findViewById(R.id.click_back);
        clickRecord = findViewById(R.id.click_record);
        clickSearch = findViewById(R.id.click_search);
        clickUpdate = findViewById(R.id.click_update);
        inputEn = findViewById(R.id.input_en);
        inputCn = findViewById(R.id.input_cn);
        rgroup = findViewById(R.id.singles);;

        OnClickListener onClickImpl = new OnClickListenerImpl();
        clickBack.setOnClickListener(onClickImpl);
        clickRecord.setOnClickListener(onClickImpl);
        clickSearch.setOnClickListener(onClickImpl);
        clickUpdate.setOnClickListener(onClickImpl);

        RadioGroup.OnCheckedChangeListener onCheckImpl = new OnCheckedChangeListenerImpl();
        rgroup.setOnCheckedChangeListener(onCheckImpl);
    }

    private class OnClickListenerImpl implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click_back:
                    finish();
                    break;
                case R.id.click_search:
                    search();
                    break;
                case R.id.click_update:
                    update();
                    break;
                case R.id.click_record:
                    record();
                    break;
            }
        }

        //搜索单词
        private void search(){
            String en = inputEn.getText().toString();;
            String cn = inputCn.getText().toString();
            int mode = WordDao.MODE_EN_OR_CN;

            switch (checkedId){
                case R.id.single_only_en: //仅匹配英文
                    if(!checkInputCorrect(en, cn, MODE_CHECK_ONLY_EN)){
                        return;
                    }
                    mode = WordDao.MODE_ONLY_EN;
                    break;
                case R.id.single_only_cn: //仅匹配中文
                    if(!checkInputCorrect(en, cn, MODE_CHECK_ONLY_CN)){
                        return;
                    }
                    mode = WordDao.MODE_ONLY_CN;
                    break;
                case R.id.single_en_or_cn: //中英文满足一个
                    if(!checkInputCorrect(en, cn, MODE_CHECK_EN_OR_CN)){
                        return;
                    }
                    mode = WordDao.MODE_EN_OR_CN;
                    break;
                case R.id.single_en_and_cn: //中英文匹配
                    if(!checkInputCorrect(en, cn, MODE_CHECK_EN_AND_CN)){
                        return;
                    }
                    mode = WordDao.MODE_EN_AND_CN;
                    break;
            }

            /* 将查询信息传递给ExploreActivity，由其查询并显示结果 */
            Intent launcher = new Intent(RecordWordActivity.this, ExploreActivity.class);
            launcher.putExtra(ExploreActivity.KEY_EN, en);
            launcher.putExtra(ExploreActivity.KEY_CN, cn);
            launcher.putExtra(ExploreActivity.KEY_MODE, mode);
            startActivity(launcher);
        }

        //更新单词
        private void update(){
            String en = inputEn.getText().toString();
            String cn = inputCn.getText().toString();

            if(!checkInputCorrect(en, cn, MODE_CHECK_EN_AND_CN)){
                return;
            }

            //todo 显示警告弹窗

            //根据英语更新单词
            int updateNum = 0;
            updateNum =  WordDao.update(new Word(en, cn, System.currentTimeMillis()), WordDao.MODE_ONLY_EN, WordDao.TABLE_WORDS);
            updateNum +=  WordDao.update(new Word(en, cn, System.currentTimeMillis()), WordDao.MODE_ONLY_EN, WordDao.TABLE_LAST_WORDS);

            //根据更新数目提示
            if(updateNum > 0){
                showMessage("更新完毕", Toast.LENGTH_SHORT);
            }else{
                showMessage("更新失败，该单词可能不存在", Toast.LENGTH_SHORT);
            }
        }

        //记录单词
        private void record(){
            String en = inputEn.getText().toString();
            String cn = inputCn.getText().toString();

            if(!checkInputCorrect(en, cn, MODE_CHECK_EN_AND_CN)){
                return;
            }
            Word data = new Word(en, cn, System.currentTimeMillis());
            //todo 对单词是否重复进行检测


            boolean b = WordDao.insert(data, WordDao.TABLE_LAST_WORDS);
            if(b){
                Toast.makeText(RecordWordActivity.this, "已记录", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //检查输入，有一个不成立则返回false
    private boolean checkInputCorrect(@NonNull String en, @NonNull String cn, int mode){
        String info = null;
        boolean correct = true;

        //检查字符数
        if(en.length() > LIMIT_CHAR_NUM || cn.length() > LIMIT_CHAR_NUM){
            info = "输入的字符数不能多于15个";
            correct = false;
        }else { //根据mode检查输入状态
            switch (mode) {
                case MODE_CHECK_ONLY_EN:
                    if (en.equals("")){
                        info = "请输入英文";
                        correct = false;
                    }
                    break;
                case MODE_CHECK_ONLY_CN:
                    if(cn.equals("")){
                        info = "请输入中文";
                        correct = false;
                    }
                    break;
                case MODE_CHECK_EN_OR_CN:
                    if(en.equals("") && cn.equals("")){
                        info = "请输入中文或英文";
                        correct = false;
                    }
                    break;
                case MODE_CHECK_EN_AND_CN:
                    if(en.equals("") ||cn.equals("")){
                        info = "请输入中文和英文";
                        correct = false;
                    }
                    break;
            }
        }

        if(!correct) { //输入错误时展示信息
            showMessage(info, Toast.LENGTH_SHORT);
        }
        return correct;
    }

    //利用Toast显示信息
    //老是忘了show，干脆写个方法来调用算了
    private void showMessage(String msg, int length){
        BasicTool.showMessage(this, msg, length);
    }

    private class OnCheckedChangeListenerImpl implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RecordWordActivity.this.checkedId = checkedId;
        }
    }
}

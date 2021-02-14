package name.kyaru.wordnote;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.Preference;
import name.kyaru.wordnote.datastruct.Word;

public class RecordWordActivity extends AppCompatActivity {
    private static final int LIMIT_CHAR_NUM = 15;
    private static final int MODE_CHECK_EN = 0;
    private static final int MODE_CHECK_CN = 1;
    private static final int MODE_CHECK_ANY = 2;
    private static final int MODE_CHECK_ALL = 3;
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

        private void search(){ //搜索单词
            String en = null;
            String cn = null;
            int mode = WordDao.MODE_EN_OR_CN;

            switch (checkedId){
                case R.id.single_only_en: //仅匹配英文
                    en = inputEn.getText().toString();
                    if(en.equals("")){
                        Toast.makeText(RecordWordActivity.this, "英文不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mode = WordDao.MODE_ONLY_EN;
                    break;
                case R.id.single_only_cn: //仅匹配中文
                    cn = inputCn.getText().toString();
                    if(cn.equals("")){
                        Toast.makeText(RecordWordActivity.this, "中文不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mode = WordDao.MODE_ONLY_CN;
                    break;
                case R.id.single_en_or_cn: //中英文满足一个
                    en = inputEn.getText().toString();
                    cn = inputCn.getText().toString();
                    if(en.equals("") && cn.equals("")){
                        Toast.makeText(RecordWordActivity.this, "中英文不能全为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mode = WordDao.MODE_EN_OR_CN;
                    break;
                case R.id.single_en_and_cn: //中英文匹配
                    en = inputEn.getText().toString();
                    cn = inputCn.getText().toString();
                    if(en.equals("") || cn.equals("")){
                        Toast.makeText(RecordWordActivity.this, "中英文不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mode = WordDao.MODE_EN_AND_CN;
                    break;
            }

            /* 将查询信息传递给ExploreActivity，由其查询并显示结果 */
            Intent launcher = new Intent(RecordWordActivity.this, ExploreActivity.class);
            launcher.putExtra("en", en);
            launcher.putExtra("cn", cn);
            //launcher.putExtra("mode", mode);
            launcher.putExtra("mode", WordDao.MODE_ALL);
            startActivity(launcher);
        }

        private void update(){ //更新单词
            String en = inputEn.getText().toString();
            String cn = inputCn.getText().toString();

            if(en.equals("") || cn.equals("")){
                Toast.makeText(RecordWordActivity.this, "中英文不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            //todo
        }

        private void record(){ //记录单词
            String en = inputEn.getText().toString();
            String cn = inputCn.getText().toString();

            if(en.equals("") || cn.equals("")){
                Toast.makeText(RecordWordActivity.this, "中英文不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            //todo 对单词是否重复进行检测
            Word data = new Word(Preference.nextId(), en, cn, System.currentTimeMillis());

            //todo
            boolean b = WordDao.insert(data, WordDao.TABLE_LAST_WORDS);
            if(b){
                Toast.makeText(RecordWordActivity.this, "已记录", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkInput(int mode){
        //todo

        return false;
    }

    private class OnCheckedChangeListenerImpl implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RecordWordActivity.this.checkedId = checkedId;
        }
    }
}

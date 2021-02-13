package name.kyaru.wordnote;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.Preference;
import name.kyaru.wordnote.datastruct.Word;
import name.kyaru.wordnote.utils.DatabaseCreator;

public class RecordWordActivity extends AppCompatActivity {
    private static final int LIMIT_CHAR_NUM = 15;
    private ImageButton clickBack;
    private EditText inputEn;
    private EditText inputCn;
    private TextView showWordTime;
    private RadioGroup rgroup;
    private int checkedId = R.id.single_en_or_cn;
    private ImageButton clickSearch;
    private ImageButton clickUpdate;
    private ImageButton clickRecord;
    private SQLiteDatabase wdb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_word);
        initView();
        initDatabase();
    }

    private void initDatabase() {
        DatabaseCreator creator = new DatabaseCreator(this, "words", null, 1);
        wdb = creator.getWritableDatabase();
        WordDao.setDatabase(wdb); //为Dao设置数据库
        Preference.loadPreference(this); //加载id等数据
    }

    private void initView(){
        clickBack = findViewById(R.id.click_back);
        clickRecord = findViewById(R.id.click_record);
        clickSearch = findViewById(R.id.click_search);
        clickUpdate = findViewById(R.id.click_update);
        inputEn = findViewById(R.id.input_en);
        inputCn = findViewById(R.id.input_cn);
        rgroup = findViewById(R.id.singles);;
        showWordTime = findViewById(R.id.show_word_time);

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
            launcher.putExtra("mode", mode);
            startActivity(launcher);
        }

        private void update(){ //更新单词
            String en = inputEn.getText().toString();
            String cn = inputCn.getText().toString();

            if(en.equals("") || cn.equals("")){
                Toast.makeText(RecordWordActivity.this, "中英文不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean updateOK = WordDao.update(new Word(en, cn, System.currentTimeMillis()));
            if(updateOK){
                Toast.makeText(RecordWordActivity.this, "更新完成", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(RecordWordActivity.this, "更新失败或单词不存在", Toast.LENGTH_SHORT).show();
            }
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



            boolean recordOK = WordDao.insert(data);
            if(recordOK){
                Toast.makeText(RecordWordActivity.this, "记录完成", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(RecordWordActivity.this, "记录失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class OnCheckedChangeListenerImpl implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RecordWordActivity.this.checkedId = checkedId;
        }
    }
}

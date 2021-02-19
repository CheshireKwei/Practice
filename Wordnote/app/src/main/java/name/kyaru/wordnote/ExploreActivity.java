package name.kyaru.wordnote;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.Word;
import name.kyaru.wordnote.datastruct.WordAdapter;
import name.kyaru.wordnote.utils.BasicTool;

/* 单词查询和浏览界面 */
public class ExploreActivity extends AppCompatActivity {
    public static final String KEY_MODE = "mode";
    public static final String KEY_EN = "en";
    public static final String KEY_CN = "cn";
    public static final int WHAT_OK = 0;
    public static final int WHAT_WRONG = 1;
    private static final String extensibleName = ".ew";
    private static final int REQUEST_CODE_EXPORT = 101;
    private static final int REQUEST_CODE_INCLUDE = 102;
    private ImageButton clickBack;
    private TextView showTitle;
    private TextView showMessage;
    private RecyclerView showWords;
    private Uri fileUri;
    private List<Word> words;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        branch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.explore_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.click_export_words: //导出单词
                exportWords();
                break;
            case R.id.click_include_words: //导入单词
                includeWords();
                break;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_INCLUDE: //当请求码为导入单词时
                if(resultCode == RESULT_OK){
                    if(data != null){
                        fileUri = data.getData();
                        try {

                        }catch (JSONException je){
                            BasicTool.showMessage(this, "导出失败", Toast.LENGTH_SHORT);
                        }
                    }
                }
            case REQUEST_CODE_EXPORT: //导出单词
                if(resultCode == RESULT_OK){
                    if(data != null){
                        fileUri = data.getData();
                        try {
                            /**
                             * 由于通过文件选择器获取uri是异步操作，所以只有当系统回调onActivityResult时，才可以进行写入操作，否则会由于
                             * uri为null而写入失败
                             */
                            writeJSONArray(BasicTool.parseToJSONArray(words));
                        }catch (JSONException je){
                            BasicTool.showMessage(this, "导出失败", Toast.LENGTH_SHORT);
                        }
                    }
                }
                break;
        }
    }

    private void branch(){ //根据是否有单词可显示来分支
        Summary sum = resolveIntent();
        words = queryWord(sum);
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

    private void exportWords(){
        if(words == null) {
            BasicTool.showMessage(this, "没有可导出的单词", Toast.LENGTH_SHORT);
            return;
        }

        //打开文件选择器，获取uri
        openSAF(Intent.ACTION_CREATE_DOCUMENT, Intent.CATEGORY_OPENABLE, "application/txt", "export_words" + extensibleName);
    }

    private void writeJSONArray(final JSONArray jArray){
        if(jArray != null && fileUri != null){
            //写入json字符串
            final String jString = jArray.toString();
            boolean correct = true;
            OutputStream os = null;
            try {
                os = getContentResolver().openOutputStream(fileUri);
                os.write(jString.getBytes());
                os.flush();
            }catch (IOException ioe) {
                correct = false; //出现异常后标识为false
            }finally {
                if(os != null){
                    try{
                        os.close();
                    }catch (IOException ioe){
                        correct = false;
                    }
                }
                fileUri = null;
            }

            //根据标志状态显示信息
            if(correct){
                BasicTool.showMessage(this, "导出成功", Toast.LENGTH_SHORT);
            }else{
                BasicTool.showMessage(this, "导出失败", Toast.LENGTH_SHORT);
            }
        }else{
            throw new IllegalArgumentException("参数不能为null");
        }
    }

    private void includeWords(){
        //todo
        Toast.makeText(this, "此功能未实现", Toast.LENGTH_SHORT).show();
    }

    //开启文件选择器以获取Uri
    private void openSAF(String action, String category, String type, String displayName){
        Intent launcher = new Intent(action);
        launcher.addCategory(category);
        launcher.setType(type);
        launcher.putExtra(Intent.EXTRA_TITLE, displayName);
        startActivityForResult(launcher, REQUEST_CODE_EXPORT);
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

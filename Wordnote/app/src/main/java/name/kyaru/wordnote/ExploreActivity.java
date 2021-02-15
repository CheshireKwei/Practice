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
    public static final String KEY_MODE_NAME = "mode";
    private ImageButton clickBack;
    private TextView showInfo;
    private RecyclerView showWords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore);
        initView();
        applyAdapter();
    }

    private void applyAdapter() {
        int mode = -1;

        Intent master = getIntent();
        if(master != null){
            List<Word> words = null;
            mode = master.getIntExtra(KEY_MODE_NAME, -1);
            switch (mode){
                case WordDao.MODE_ALL:
                    words = WordDao.query(null, WordDao.MODE_ALL, WordDao.TABLE_LAST_WORDS);
                    if(words == null) {
                        showInfo.setText("无单词");
                    }else{
                        showInfo.setText("单词列表");
                        RecyclerView.Adapter adapter = new WordAdapter(words, this);
                        showWords.setAdapter(adapter);
                    }
                    break;
                default:
                    showInfo.setText("无单词");
                    break;
            }
        }
    }

    private void initView() {
        clickBack = findViewById(R.id.click_back);
        showWords = findViewById(R.id.show_words);
        showInfo = findViewById(R.id.show_info);

        //配置RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        showWords.setLayoutManager(layoutManager);
        showWords.setItemAnimator(new DefaultItemAnimator());
        showWords.setHasFixedSize(true);

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
}

package name.kyaru.wordnote;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/* 用于显示单词复习的结果 */
public class ResultActivity extends AppCompatActivity {
    public static final String KEY_MODE_NAME = "mode";
    public static final String KEY_MESSAGE_NAME = "message";
    public static final int MODE_SHOW_MESSAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
    }
}
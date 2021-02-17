package name.kyaru.wordnote;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import name.kyaru.wordnote.datastruct.ReviewPreference;

/* 用于显示单词复习的结果 */
public class ResultActivity extends AppCompatActivity {
    public static final String KEY_MODE = "mode";
    public static final String KEY_CORRECT_COUNT = "correct_count";
    public static final String KEY_TOTAL_COUNT = "total_count";
    public static final int MODE_CLEARING = 1;
    private ImageButton clickBack;
    private TextView showCorrectNum;
    private TextView showHistCrtNum;
    private TextView showCorrectRate;
    private TextView showHistCrtRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        initView();
        displayMessage();
    }

    //显示信息
    private void displayMessage() {
        //计算正确率
        int[] datas = resolveIntent();
        int correctRate = calculateCorrectRate(datas[1], datas[2]);

        //计算及保存历史记录
        saveHistory(datas[1], correctRate, datas[2]);

        //显示
        showCorrectNum.setText(datas[1] + "/" + datas[2]);
        showHistCrtNum.setText(ReviewPreference.hisCorrectNum + "/" + ReviewPreference.hisCorrectTotalNum);
        showCorrectRate.setText(correctRate + "%/" + datas[2]);
        showHistCrtRate.setText(ReviewPreference.hisCorrectRate + "%/" +ReviewPreference.hisCorrectRateTotalNum);
    }

    //保存历史
    private void saveHistory(int correctNum, int correctRate, int totalNum){
        if(totalNum >= ReviewPreference.hisCorrectTotalNum){
            if(correctNum > ReviewPreference.hisCorrectNum){
                ReviewPreference.hisCorrectNum = correctNum;
                ReviewPreference.hisCorrectTotalNum = totalNum;
            }
        }
        if(totalNum >= ReviewPreference.hisCorrectRateTotalNum){
            if(correctRate >= ReviewPreference.hisCorrectRate){
                ReviewPreference.hisCorrectRate = correctRate;
                ReviewPreference.hisCorrectRateTotalNum = totalNum;
            }
        }
    }

    //计算正确率
    private int calculateCorrectRate(int correctNum, int totalNum){
        int correctRate = 0;

        if(totalNum != 0){
            correctRate = (int)(((float)correctNum / totalNum) * 100);
        }
        return correctRate;
    }

    //解析上一个活动传递的信息
    private int[] resolveIntent(){
        Intent origin = getIntent();
        int[] datas = new int[3];

        datas[0] = origin.getIntExtra(KEY_MODE, MODE_CLEARING);
        datas[1] = origin.getIntExtra(KEY_CORRECT_COUNT, 0);
        datas[2] = origin.getIntExtra(KEY_TOTAL_COUNT, 0);

        return datas;
    }

    private void initView(){
        clickBack = findViewById(R.id.click_back);
        showCorrectNum = findViewById(R.id.show_correct_num);
        showHistCrtNum = findViewById(R.id.show_history_correct_num);
        showCorrectRate = findViewById(R.id.show_correct_rate);
        showHistCrtRate = findViewById(R.id.show_history_correct_rate);

        //设置监听
        OnClickListenerImpl  onClickImpl = new OnClickListenerImpl();
        clickBack.setOnClickListener(onClickImpl);
    }

    private class OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
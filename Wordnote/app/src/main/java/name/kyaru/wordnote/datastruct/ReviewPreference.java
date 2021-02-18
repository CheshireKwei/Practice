package name.kyaru.wordnote.datastruct;
import android.content.Context;
import android.content.SharedPreferences;

import name.kyaru.wordnote.bgm.EffectManager;

/* 保存首选项 */
public class ReviewPreference {
    private static final String NAME = "basic";
    private static final String KEY_HISTORY_CORRECT_NUM = "history_correct_num";
    private static final String KEY_HISTORY_CORRECT_RATE = "history_correct_rate";
    private static final String KEY_HISTORY_CORRECT_TOTAL_NUM = "history_correct_total_num";
    private static final String KEY_HISTORY_CORRECT_RATE_TOTAL_NUM = "history_correct_rate_total_num";
    private static final String KEY_TIME = "time";
    private static final String KEY_MUSIC_STATE = "music_state";
    public static int hisCorrectNum = 0;
    public static int hisCorrectTotalNum = 0;
    public static int hisCorrectRate = 0;
    public static int hisCorrectRateTotalNum = 0;
    public static long time;
    public static boolean isDoNotPlay = false;

    public static void savePreference(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY_HISTORY_CORRECT_NUM, hisCorrectNum);
        editor.putInt(KEY_HISTORY_CORRECT_TOTAL_NUM, hisCorrectTotalNum);
        editor.putInt(KEY_HISTORY_CORRECT_RATE, hisCorrectRate);
        editor.putInt(KEY_HISTORY_CORRECT_RATE_TOTAL_NUM, hisCorrectRateTotalNum);
        editor.putLong(KEY_TIME, time);
        editor.putBoolean(KEY_MUSIC_STATE, isDoNotPlay);
        editor.commit();
    }

    //加载首选项
    public static void loadPreference(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        hisCorrectNum = sp.getInt(KEY_HISTORY_CORRECT_NUM, 0);
        hisCorrectTotalNum = sp.getInt(KEY_HISTORY_CORRECT_TOTAL_NUM, 0);
        hisCorrectRate = sp.getInt(KEY_HISTORY_CORRECT_RATE, 0);
        hisCorrectRateTotalNum = sp.getInt(KEY_HISTORY_CORRECT_RATE_TOTAL_NUM, 0);
        time = sp.getLong(KEY_TIME, System.currentTimeMillis());
        isDoNotPlay = sp.getBoolean(KEY_MUSIC_STATE, false);

        //加载首选项后处
        afterLoad();
    }

    //加载首选项后处理
    public static void afterLoad(){
        EffectManager.getInstance().setDoNotPlay(isDoNotPlay); //设置是否关闭音效
    }
}

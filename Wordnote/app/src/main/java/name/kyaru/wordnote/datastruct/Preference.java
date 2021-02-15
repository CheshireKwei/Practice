package name.kyaru.wordnote.datastruct;
import android.content.Context;
import android.content.SharedPreferences;

/* 保存首选项 */
public class Preference {
    private static final String NAME = "basic";

    public static void savePreference(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.commit();
    }

    public static void loadPreference(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }
}

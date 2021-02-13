package name.kyaru.wordnote.datastruct;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private static final String NAME = "basic";
    private static final String KEY_RECENTLY_ID = "recently_id";
    private static int recentId = 1;

    public static int nextId(){
        return recentId++;
    }

    public static void savePreference(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY_RECENTLY_ID, recentId);
        editor.commit();
    }

    public static void loadPreference(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        recentId = sp.getInt(KEY_RECENTLY_ID, 1);
    }
}

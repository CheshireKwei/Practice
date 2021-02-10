package name.kyaru.wordnote.utils;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

//管理所有活动的释放
public class ActivityManager {
    private static List<Activity> activitys = new ArrayList<>(5);

    public static void put(Activity a){
        ActivityManager.activitys.add(a);
    }

    public static void remove(Activity a){
        ActivityManager.activitys.remove(a);
    }

    public static boolean finish(Activity a){
        if(ActivityManager.activitys.contains(a)){
            ActivityManager.activitys.get(ActivityManager.activitys.indexOf(a)).finish();
        }

        return false;
    }

    public static void finishAll(){
        for(int i = 0; i < ActivityManager.activitys.size(); i++){
            ActivityManager.activitys.get(i).finish();
        }
    }
}

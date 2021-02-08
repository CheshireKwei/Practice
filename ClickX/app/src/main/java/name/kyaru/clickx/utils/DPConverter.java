package name.kyaru.clickx.utils;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DPConverter {
    public static int convertToPixel(int dp, Context context){
        //DisplayMetrics dm = new DisplayMetrics();
        //windm.getDefaultDisplay().getMetrics(dm);
        //dm.widthPixels; //屏幕宽度
        //dm.heightPixels; //屏幕高度
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}

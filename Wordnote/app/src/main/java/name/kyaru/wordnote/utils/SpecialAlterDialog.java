package name.kyaru.wordnote.utils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class SpecialAlterDialog {
    public static final int TYPE_WARN = 0;
    private static AlertDialog.Builder builder;
    private static int type = TYPE_WARN;
    private static AlertDialog.OnClickListener onClickImpl;

    public static void create(Context context){ //调用此方法来初始化
        builder = new AlertDialog.Builder(context);
        onClickImpl = new OnClickListenerImpl();
    }

    public static void show(){
        builder.show();
    }

    public static void setMessage(String msg, int type){
        if(type == TYPE_WARN && builder != null){
            doWarn(msg);
        }
    }

    private static void doWarn(String msg){ //WARN类型对应的方法，配置警告弹窗
        builder.setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("继续", onClickImpl)
                .setNegativeButton("取消", onClickImpl);
    }

    private static class OnClickListenerImpl implements AlertDialog.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            
        }
    }
}

package name.kyaru.wordnote.utils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import name.kyaru.wordnote.R;

public class SpecialAlterDialog {
    public static final int TYPE_WARN = 0;
    private static AlertDialog.Builder builder;
    private static AlertDialog.OnClickListener onClickImpl;
    private static Runnable positiveTask;
    private static Runnable negativeTask;
    private static int type = TYPE_WARN;

    public static void create(Context context){ //调用此方法来初始化
        if(builder == null) {
            builder = new AlertDialog.Builder(context);
            onClickImpl = new OnClickListenerImpl();
            setMessage("默认", TYPE_WARN);
        }
    }

    public static void show(){
        builder.show();
    }

    public static void setMessage(String msg, int type){
        if(type == TYPE_WARN && builder != null){
            doWarn(msg);
        }
    }

    public static void setTask(Runnable positiveTask, Runnable negativeTask){
        SpecialAlterDialog.positiveTask = positiveTask;
        SpecialAlterDialog.negativeTask = negativeTask;
    }

    private static void doWarn(String msg){ //WARN类型对应的方法，配置警告弹窗
        builder.setTitle("提示")
                .setMessage(msg)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("继续", onClickImpl)
                .setNegativeButton("取消", onClickImpl);
    }

    private static class OnClickListenerImpl implements AlertDialog.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (type){
                case TYPE_WARN:
                    if(which == DialogInterface.BUTTON_POSITIVE){
                        if(positiveTask != null) {
                            positiveTask.run();
                        }
                    }else if(which == DialogInterface.BUTTON_NEGATIVE){
                        if(negativeTask != null) {
                            negativeTask.run();
                        }
                    }
                    break;
            }
        }
    }
}

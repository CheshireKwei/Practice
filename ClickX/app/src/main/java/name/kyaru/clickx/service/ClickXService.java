package name.kyaru.clickx.service;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;
import name.kyaru.clickx.window.ClickXWindow;
<<<<<<< HEAD
import name.kyaru.clickx.window.IFunction;
=======
import name.kyaru.clickx.window.IWindow;
>>>>>>> 2d93302... 提交

/**
 *  继承AccessibilityService以使用无障碍服务，同时实现IFunction接口以为悬浮窗提供具体的功能
 */
public class ClickXService extends AccessibilityService implements IFunction {
    private IWindow windowImpl;
    private Path position;
    private GestureResultCallbackX callbackx;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //空实现
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(getApplicationContext(), "服务中断", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void click(float x, float y, int count, int delay, Handler hChangeState) {
        callbackx.setParams(count, hChangeState);
        GestureDescription.Builder gBuilder = new GestureDescription.Builder();
        position.moveTo(x, y); //设置点击位置
        GestureDescription.StrokeDescription gsDescription = new GestureDescription.StrokeDescription(position, 0, 10);;
        gBuilder.addStroke(gsDescription);
        for(int c = 0; c < count; c++) {
            try{
                Thread.sleep(delay);
            }catch (Exception e){
                e.printStackTrace();
            }
            dispatchGesture(gBuilder.build(), callbackx, null); //分发手势
        }
    }

    @Override
    public void disable() {
        disableSelf();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(getApplicationContext(), "初始化中", Toast.LENGTH_SHORT).show();
        if(init()){
            Toast.makeText(getApplicationContext(), "完成", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "出现了错误", Toast.LENGTH_SHORT).show();
        }
    }

    /* 初始化 */
    private boolean init(){
        windowImpl = new ClickXWindow(this, getApplicationContext());
        position = new Path();
        callbackx = new GestureResultCallbackX();
        return windowImpl.display();
    }

    private class GestureResultCallbackX extends GestureResultCallback{ //重写回调接口
        private int finishedCount = 0;
        private int targetCount;
        private Handler hChangeState;

        public GestureResultCallbackX(){
            super();
            finishedCount = 0;
        }

        @Override
        public void onCompleted(GestureDescription gestureDescription) { //手势完成后
            super.onCompleted(gestureDescription);
            finishOperation();
        }

        @Override
        public void onCancelled(GestureDescription gestureDescription) { //手势取消后
            super.onCancelled(gestureDescription);
            finishOperation(); //后发送的手势可能取代之前的手势，造成手势无效，所以需要重写此方法
        }

        public void setParams(int count, Handler hChangeState){
            this.targetCount = count;
            this.hChangeState = hChangeState;
        }

        /* 手势计数 */
        private void finishOperation(){
            finishedCount++;
            if(finishedCount == targetCount){ //点击数满足后，向主线程发送消息以恢复悬浮窗状态
                hChangeState.sendEmptyMessage(111);
                finishedCount = 0;
            }
        }
    }
}

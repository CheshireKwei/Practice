package name.kyaru.clickx.service;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;
import name.kyaru.clickx.window.ClickXWindow;
import name.kyaru.clickx.window.IFunction;

public class ClickXService extends AccessibilityService implements IFunction {
    private IWindow windowImpl;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //空实现
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(getApplicationContext(), "服务中断", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void click(int x, int y) {
        //todo 实现屏幕点击
    }

    @Override
    public void disable() {
        disableSelf();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        windowImpl = new ClickXWindow(this, getApplicationContext());
        Toast.makeText(getApplicationContext(), "初始化中", Toast.LENGTH_SHORT).show();
        if(windowImpl.display()){
            Toast.makeText(getApplicationContext(), "完成", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "出现了错误", Toast.LENGTH_SHORT).show();
        }
    }
}

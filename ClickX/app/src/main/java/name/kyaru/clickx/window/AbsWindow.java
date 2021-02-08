package name.kyaru.clickx.window;
import android.view.WindowManager;
import name.kyaru.clickx.utils.IConfig;

public abstract class AbsWindow implements IWindow, IConfig<WindowManager> {
    public static final int LEVEL_HIDE_EXTREMES = 10; //隐藏等级_最高
    public static final int LEVEL_HIDE_DEFAULT = 0;
    protected WindowManager.LayoutParams wLayoutParams; //每个窗口都需要窗口参数
    protected WindowManager wManager; //由每个窗口类管理自身窗口的添加和移除

    public abstract void hide(int level);

    protected AbsWindow(){
        this.wLayoutParams = new WindowManager.LayoutParams();
    }

    @Override
    public void config(WindowManager wManager) {
        this.wManager = wManager;
    }

    public static final class index{
        public static final int CLICKX = 0;
        public static final int SCRIPT = 1;
    }
}

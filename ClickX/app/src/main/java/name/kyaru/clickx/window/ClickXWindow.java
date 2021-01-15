package name.kyaru.clickx.window;
import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;
import name.kyaru.clickx.service.IWindow;

public class ClickXWindow implements IWindow {
    private Context appContext;
    private IFunction funcImpl;
    private WindowParams wdparams;
    private View floatingWindow;
    private View restoreWindow;
    private PopupMenu extraOptions;

    public ClickXWindow(IFunction funcImpl, Context appContext){
        this.funcImpl = funcImpl;
        this.appContext = appContext;
        wdparams = new WindowParams();
    }

    @Override
    public boolean display() {
        //todo 显示窗口

        return false;
    }

    private void initView(){

    }

    private static class WindowParams{
        public static final int DEFAULT_DRAW_X = 0;
        public static final int DEFAULT_DRAW_Y = 0;
        public static final int DEFAULT_WIDTH = 0;
        public static final int DEFAULT_HEIGHT = 0;
        public static final int DEFAULT_CLICK_COUNT = 0;
        public static final int DEFAULT_CLICK_DELAY = 0;
        public int drawX;
        public int drawY;
        public int width;
        public int height;
        public int clickCount;
        public int clickDelay;

        public WindowParams(){
            drawX = DEFAULT_DRAW_X;
            drawY = DEFAULT_DRAW_Y;
            width = DEFAULT_WIDTH;
            height = DEFAULT_HEIGHT;
            clickCount = DEFAULT_CLICK_COUNT;
            clickDelay = DEFAULT_CLICK_DELAY;
        }
    }
}

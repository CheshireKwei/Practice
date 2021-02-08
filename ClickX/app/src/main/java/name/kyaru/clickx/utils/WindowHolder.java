package name.kyaru.clickx.utils;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;
import name.kyaru.clickx.window.AbsWindow;

/* 为了后续添加悬浮窗更加方便管理，所以创建了这个工具类 */
public class WindowHolder implements ICallback {
    private WindowManager wManager;
    private List<AbsWindow> wList;

    public WindowHolder(WindowManager wManager){
        this.wManager = wManager;
        wList = new ArrayList<>(5);
    }

    public void add(AbsWindow wind){
        wind.config(wManager);
        wList.add(wind);
    }

    public void display(int index){
        wList.get(index).display();
    }

    @Override
    public void redispaly(int index) {
        wList.get(index).display();
    }

    public void hide(int index, int level){
        wList.get(index).hide(level);
    }

    public void destory(){
        for(AbsWindow abw  : wList){
            abw.hide(AbsWindow.LEVEL_HIDE_EXTREMES);
        }
    }
}

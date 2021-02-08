package name.kyaru.clickx.window;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import name.kyaru.clickx.service.IFunction;
import name.kyaru.clickx.struct.PointData;
import name.kyaru.clickx.utils.ICallback;

public class ScriptWindow extends AbsWindow{
    private IFunction funcImpl;
    private ICallback iCallback;
    private View mainWindow;
    private List<PointData> points;

    public ScriptWindow(IFunction iFunction, ICallback iCallback) {
        this.funcImpl = iFunction;
        this.iCallback = iCallback;
        points = new ArrayList<>();
        initView();
    }

    public void initView(){

    }

    @Override
    public void hide(int level) {
        if(level == LEVEL_HIDE_EXTREMES){
            wManager.removeView(mainWindow);
        }
    }

    @Override
    public boolean display() {
        return false;
    }

}

package name.kyaru.clickx.window;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import name.kyaru.clickx.R;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import name.kyaru.clickx.service.IWindow;
import name.kyaru.clickx.utils.DPConverter;

public class ClickXWindow implements IWindow {
    private static final int FLAGS_NORMAL = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    private static final int FLAGS_THROUGH = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    private static final int STATE_HIDE_LOW = 1;
    private static final int STATE_HIDE_NORMAL = 2;
    private static final int STATE_HIDE_HIGH = 3;
=======
>>>>>>> master_mirror
import name.kyaru.clickx.service.IFunction;
import name.kyaru.clickx.utils.DPConverter;
import name.kyaru.clickx.utils.WindowHolder;

/* 首先显示的窗口，主要窗口 */
public class ClickXWindow extends AbsWindow {
    private static final int FLAGS_NORMAL = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    private static final int FLAGS_THROUGH = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    private static final int LEVEL_HIDE_LOW = 1;
<<<<<<< HEAD
=======
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
    private static Context appContext;
    private IFunction funcImpl;
    private WindowManager windowManager;
    private WindowParams wdParams;
    private View floatingWindow;
    private View restoreWindow;
    private PopupMenu pExtras;
    private PopupMenu pRestore;
    private ImageButton zoom;
    private ImageButton shrink;
    private ImageButton increaseCount;
    private ImageButton decreaseCount;
    private ImageButton showExtras;
    private TextView moveArea;
    private Button continuousClickArea;
    private WindowManager.LayoutParams fwLayoutParams;
    private WindowManager.LayoutParams rwLayoutParams;
<<<<<<< HEAD
=======
<<<<<<< HEAD
    private Handler hChangeState;
    private Runnable task;
    private int hideState; //隐藏状态
    private int clickPointX;
    private int clickPointY;
    private boolean clicking = false; //是否已点击
=======
>>>>>>> master_mirror
    private Handler hChangeLEVEL;
    private Runnable task;
    private WindowHolder wHolder;
    private int recentlyHideLevel; //隐藏状态
    private int clickPointX;
    private int clickPointY;
    private boolean clicking = false; //是否已点击
    private boolean inited = false;
<<<<<<< HEAD
=======
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror

    public ClickXWindow(IFunction funcImpl, Context appContext){
        this.funcImpl = funcImpl;
        this.appContext = appContext;
        wdParams = new WindowParams();
    }

    @Override
    public boolean display() {
<<<<<<< HEAD
=======
<<<<<<< HEAD
        try {
            initView();
        }catch(Exception e){
            return false;
=======
>>>>>>> master_mirror
        if(!inited) {
            try {
                /* 初始化 */
                initView();
                buildOtherWindow();
                inited = true;

                /* 向界面添加窗口 */
                windowManager.addView(floatingWindow, fwLayoutParams);
                recentlyHideLevel = LEVEL_HIDE_DEFAULT;
            } catch (Exception e) {
                return false;
            }
        }else{ //兼容AbsWindow类
            windowManager.addView(floatingWindow, fwLayoutParams);
<<<<<<< HEAD
=======
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
        }

        return true;
    }

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> master_mirror

    @Override
    public void hide(int level) {
        if(level == LEVEL_HIDE_LOW) {
            increaseCount.setVisibility(View.INVISIBLE);
            decreaseCount.setVisibility(View.INVISIBLE);
            zoom.setVisibility(View.INVISIBLE);
            shrink.setVisibility(View.INVISIBLE);
            moveArea.setVisibility(View.INVISIBLE);
        }else if(level == AbsWindow.LEVEL_HIDE_EXTREMES){
            wManager.removeView(floatingWindow);
        }
        recentlyHideLevel = level; //记录最近隐藏的等级
    }

<<<<<<< HEAD
=======
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
    @SuppressLint("ResourceType")
    private void initView(){
        /* 获取视图对象 */
        floatingWindow = LayoutInflater.from(appContext).inflate(R.layout.window, null); //加载悬浮窗
        restoreWindow = LayoutInflater.from(appContext).inflate(R.layout.restore, null); //加载恢复窗口
        windowManager = (WindowManager)appContext.getSystemService(Context.WINDOW_SERVICE); //获取窗口管理器
        zoom = floatingWindow.findViewById(R.id.click_zoom);
        shrink = floatingWindow.findViewById(R.id.click_shrink);
        increaseCount = floatingWindow.findViewById(R.id.click_increase_count);
        decreaseCount = floatingWindow.findViewById(R.id.click_decrease_count);
        showExtras = floatingWindow.findViewById(R.id.click_extra);
        moveArea = floatingWindow.findViewById(R.id.touch_move);
        continuousClickArea = floatingWindow.findViewById(R.id.click_loop_click);
<<<<<<< HEAD
        hChangeLEVEL = new Handler(Looper.getMainLooper()){ //通过Handler在主线程改变状态
=======
<<<<<<< HEAD
        hChangeState = new Handler(Looper.getMainLooper()){ //通过Handler在主线程改变状态
=======
        hChangeLEVEL = new Handler(Looper.getMainLooper()){ //通过Handler在主线程改变状态
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
            @Override
            public void handleMessage(@NonNull Message msg) {
                clicking = false;
                fwLayoutParams.flags = FLAGS_NORMAL;
                windowManager.updateViewLayout(floatingWindow, fwLayoutParams); //还原悬浮窗的点击接收功能
            }
        };
        task = new Runnable() { //执行连点
            @Override
            public void run() {
<<<<<<< HEAD
                funcImpl.click(clickPointX, clickPointY, wdParams.clickCount, wdParams.clickDelay, hChangeLEVEL);
=======
<<<<<<< HEAD
                funcImpl.click(clickPointX, clickPointY, wdParams.clickCount, wdParams.clickDelay, hChangeState);
=======
                funcImpl.click(clickPointX, clickPointY, wdParams.clickCount, wdParams.clickDelay, hChangeLEVEL);
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
            }
        };

        /* 初始化弹出菜单 */
        pExtras = new PopupMenu(appContext, showExtras);
        pExtras.getMenuInflater().inflate(R.layout.popub_items, pExtras.getMenu());
        pRestore = new PopupMenu(appContext, restoreWindow);
        pRestore.getMenuInflater().inflate(R.layout.restore_item, pRestore.getMenu());

        /* 初始化悬浮窗参数 */
        //连点-悬浮窗的参数
        fwLayoutParams = new WindowManager.LayoutParams();
        fwLayoutParams.width = wdParams.DEFAULT_WIDTH;
        fwLayoutParams.height = wdParams.DEFAULT_HEIGHT;
        fwLayoutParams.x = wdParams.DEFAULT_DRAW_X;
        fwLayoutParams.y = wdParams.DEFAULT_DRAW_Y;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            fwLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else{
            fwLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        fwLayoutParams.format = PixelFormat.RGBA_8888;
        fwLayoutParams.flags = FLAGS_NORMAL;

        //还原-悬浮窗的参数
        rwLayoutParams = new WindowManager.LayoutParams();
        rwLayoutParams.width = DPConverter.convertToPixel(40, appContext);
        rwLayoutParams.height = DPConverter.convertToPixel(40, appContext);
        rwLayoutParams.x = 0;
        rwLayoutParams.y = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            rwLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else{
            rwLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        rwLayoutParams.format = PixelFormat.RGBA_8888;
        rwLayoutParams.flags = FLAGS_NORMAL;

        /* 设置监听 */
        //触摸监听
        View.OnTouchListener touLis = new OnTouchListenerImpl();
        moveArea.setOnTouchListener(touLis);
        restoreWindow.setOnTouchListener(touLis);
        continuousClickArea.setOnTouchListener(touLis); //通过触摸监听获取点击位置的坐标

        //点击监听
        View.OnClickListener cliLis = new OnClickListenerImpl();
        showExtras.setOnClickListener(cliLis);
        zoom.setOnClickListener(cliLis);
        shrink.setOnClickListener(cliLis);
        increaseCount.setOnClickListener(cliLis);
        decreaseCount.setOnClickListener(cliLis);
        restoreWindow.setOnClickListener(cliLis);

        //菜单监听
        PopupMenu.OnMenuItemClickListener menuLis = new OnMenuItemClickListenerImpl();
        pExtras.setOnMenuItemClickListener(menuLis);
        pRestore.setOnMenuItemClickListener(menuLis);

        /* 刷新文本 */
        refreshText();
<<<<<<< HEAD
=======
<<<<<<< HEAD

        /* 向界面添加窗口 */
        windowManager.addView(floatingWindow, fwLayoutParams);
        hideState = STATE_HIDE_LOW;
=======
>>>>>>> master_mirror
    }

    private void buildOtherWindow(){
        wHolder = new WindowHolder(windowManager);
        //todo 创建其他窗口的实例
        wHolder.add(new ScriptWindow(funcImpl, wHolder));
<<<<<<< HEAD
=======
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
    }

    private void saveParams(){
        SharedPreferences params = appContext.getSharedPreferences("params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = params.edit();
        editor.putInt(WindowParams.NAME_DRAW_Y, wdParams.drawX);
        editor.putInt(WindowParams.NAME_DRAW_Y, wdParams.drawY);
        editor.putInt(WindowParams.NAME_WIDTH, wdParams.width);
        editor.putInt(WindowParams.NAME_HEIGHT, wdParams.height);
        editor.putInt(WindowParams.NAME_CLICK_COUNT, wdParams.clickCount);
        editor.putInt(WindowParams.NAME_CLICK_DELAY, wdParams.clickDelay);
        editor.apply();
    }

    private void loadParams(){
        SharedPreferences params = appContext.getSharedPreferences("params", Context.MODE_PRIVATE);
        wdParams.drawX = params.getInt(WindowParams.NAME_DRAW_X, WindowParams.DEFAULT_DRAW_X);
        wdParams.drawY = params.getInt(WindowParams.NAME_DRAW_Y, WindowParams.DEFAULT_DRAW_Y);
        wdParams.width = params.getInt(WindowParams.NAME_WIDTH, WindowParams.DEFAULT_WIDTH);
        wdParams.height = params.getInt(WindowParams.NAME_HEIGHT, WindowParams.DEFAULT_HEIGHT);
        wdParams.clickCount = params.getInt(WindowParams.NAME_CLICK_COUNT, WindowParams.DEFAULT_CLICK_COUNT);
        wdParams.clickDelay = params.getInt(WindowParams.NAME_CLICK_DELAY, WindowParams.DEFAULT_CLICK_DELAY);
    }

    private void applyParams(){
        fwLayoutParams.width = wdParams.width;
        fwLayoutParams.height = wdParams.height;
        fwLayoutParams.x = wdParams.drawX;
        fwLayoutParams.y = wdParams.drawY;
        windowManager.updateViewLayout(floatingWindow, fwLayoutParams);
        refreshText();
    }

    private void refreshText(){
        moveArea.setText("点击数[" + wdParams.clickCount + "]" + "   " + "间隔[" + (float)wdParams.clickDelay / 1000 + "]秒");
    }

    /* 资源释放 */
    private void close(){
<<<<<<< HEAD
        wHolder.destory();
=======
<<<<<<< HEAD
        windowManager.removeView(floatingWindow);
=======
        wHolder.destory();
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
        pExtras.dismiss();
        pRestore.dismiss();
        funcImpl.disable();
    }

    private static class WindowParams{
        public static final String NAME_DRAW_X = "drawX";
        public static final String NAME_DRAW_Y = "drawY";
        public static final String NAME_WIDTH = "width";
        public static final String NAME_HEIGHT = "height";
        public static final String NAME_CLICK_COUNT = "click_count";
        public static final String NAME_CLICK_DELAY = "click_delay";
        public static final int DEFAULT_DRAW_X = 0;
        public static final int DEFAULT_DRAW_Y = 0;
        public static final int DEFAULT_WIDTH = DPConverter.convertToPixel(251, appContext);
        public static final int DEFAULT_HEIGHT = DPConverter.convertToPixel(201, appContext);
        public static final int DEFAULT_CLICK_COUNT = 10;
        public static final int DEFAULT_CLICK_DELAY = 200;
        public int drawX; //窗口绘制位置X
        public int drawY;
        public int width; //窗口宽度
        public int height;
        public int clickCount; //连点次数
        public int clickDelay; //连点间隔

        public WindowParams(){
            drawX = DEFAULT_DRAW_X;
            drawY = DEFAULT_DRAW_Y;
            width = DEFAULT_WIDTH;
            height = DEFAULT_HEIGHT;
            clickCount = DEFAULT_CLICK_COUNT;
            clickDelay = DEFAULT_CLICK_DELAY;
        }
    }

    private class OnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.click_extra: //额外功能
                    pExtras.show();
                    break;
                case R.id.click_increase_count: //增加点击数
                        wdParams.clickCount += 2;
                        refreshText();
                    break;
                case R.id.click_decrease_count: //减少点击数
                    if(wdParams.clickCount < 3){
                        Toast.makeText(appContext, "无法继续减少", Toast.LENGTH_SHORT).show();
                    }else{
                        wdParams.clickCount -= 2;
                        refreshText();
                    }
                    break;
                case R.id.click_zoom: //放大
                    wdParams.width += 50;
                    fwLayoutParams.width = wdParams.width;
                    windowManager.updateViewLayout(floatingWindow, fwLayoutParams);
                    break;
                case R.id.click_shrink: //缩小
                    if(wdParams.width - 50 < wdParams.DEFAULT_WIDTH){
                        Toast.makeText(appContext, "无法继续缩小", Toast.LENGTH_SHORT).show();
                    }else{
                        wdParams.width -= 50;
                        fwLayoutParams.width = wdParams.width;
                        windowManager.updateViewLayout(floatingWindow, fwLayoutParams);
                    }
                    break;
                case R.id.click_extra2: //恢复用的悬浮窗，点击时显示菜单
                    pRestore.show();
                    break;
            }
        }
    }

    private class OnTouchListenerImpl implements View.OnTouchListener{
        private int lastRawX;
        private int lastRawY;
        private int distanceX;
        private int distanceY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){ //判断事件类型
                case MotionEvent.ACTION_DOWN:
                    lastRawX = (int)event.getRawX();
                    lastRawY = (int)event.getRawY();
                    clickPointX = lastRawX;
                    clickPointY = lastRawY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowRawX = (int)event.getRawX();
                    int nowRawY = (int)event.getRawY();
                    distanceX = nowRawX - lastRawX;
                    distanceY = nowRawY - lastRawY;
                    lastRawX = nowRawX;
                    lastRawY = nowRawY;
                    switch (v.getId()){ //判断发生触摸事件的视图并刷新
                        case R.id.touch_move:
                            fwLayoutParams.x = fwLayoutParams.x + distanceX;
                            fwLayoutParams.y = fwLayoutParams.y + distanceY;
                            windowManager.updateViewLayout(floatingWindow, fwLayoutParams);
                            break;
                        case R.id.click_extra2:
                            rwLayoutParams.x = rwLayoutParams.x + distanceX;
                            rwLayoutParams.y = rwLayoutParams.y + distanceY;
                            windowManager.updateViewLayout(restoreWindow, rwLayoutParams);
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP: //手指抬起
                    switch (v.getId()){ //记录悬浮窗的绘制位置
                        case R.id.touch_move: //记录悬浮窗的绘制位置
                            wdParams.drawX = fwLayoutParams.x;
                            wdParams.drawY = fwLayoutParams.y;
                            break;
                        case R.id.click_loop_click:
                            if(!clicking) {
                                clicking = true;
                                fwLayoutParams.flags = FLAGS_THROUGH; //改变视图属性为点击穿透
                                windowManager.updateViewLayout(floatingWindow, fwLayoutParams); //刷新视图
                                new Thread(task).start();
                            }
                            break;
                    }
                    break;
            }

            return false;
        }
    }

    private class OnMenuItemClickListenerImpl implements PopupMenu.OnMenuItemClickListener{
<<<<<<< HEAD
        //添加附加功能
=======
<<<<<<< HEAD
=======
        //添加附加功能
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch(item.getItemId()){
                case R.id.click_increase_delay: //增加延迟
                    wdParams.clickDelay += 50;
                    refreshText();
                    break;
                case R.id.click_decrease_delay: //减少延迟
                    if(wdParams.clickDelay >= 100){
                        wdParams.clickDelay -= 50;
                        refreshText();
                    }else if(wdParams.clickDelay > 10){
                        wdParams.clickDelay = 10;
                        refreshText();
                    }else{
                        Toast.makeText(appContext, "无法继续减小", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.click_exit: //退出
                    close();
                    break;
                case R.id.click_hide_window: //隐藏
<<<<<<< HEAD
=======
<<<<<<< HEAD
                    if(hideState == STATE_HIDE_LOW){ //隐藏部分控件
                        increaseCount.setVisibility(View.INVISIBLE);
                        decreaseCount.setVisibility(View.INVISIBLE);
                        zoom.setVisibility(View.INVISIBLE);
                        shrink.setVisibility(View.INVISIBLE);
                        moveArea.setVisibility(View.INVISIBLE);
                        hideState = STATE_HIDE_NORMAL; //修改隐藏状态
                        windowManager.updateViewLayout(floatingWindow, fwLayoutParams); //刷新窗口
                    }else if(hideState == STATE_HIDE_NORMAL){ //直接移除悬浮窗
                        windowManager.removeView(floatingWindow);
=======
>>>>>>> master_mirror
                    if(recentlyHideLevel == LEVEL_HIDE_DEFAULT){ //隐藏部分控件
                        hide(LEVEL_HIDE_LOW);
                        windowManager.updateViewLayout(floatingWindow, fwLayoutParams); //刷新窗口
                    }else if(recentlyHideLevel == LEVEL_HIDE_LOW){ //直接移除悬浮窗
                        hide(LEVEL_HIDE_EXTREMES);
<<<<<<< HEAD
=======
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
                        windowManager.addView(restoreWindow, rwLayoutParams);
                        increaseCount.setVisibility(View.VISIBLE);
                        decreaseCount.setVisibility(View.VISIBLE);
                        zoom.setVisibility(View.VISIBLE);
                        shrink.setVisibility(View.VISIBLE);
                        moveArea.setVisibility(View.VISIBLE);
                        continuousClickArea.setVisibility(View.VISIBLE);
                        fwLayoutParams.flags = FLAGS_NORMAL; //可被点击
<<<<<<< HEAD
=======
<<<<<<< HEAD
                        hideState = STATE_HIDE_HIGH;
                    }
                    break;
                case R.id.click_restore: //点击恢复第一个悬浮窗
                    if(hideState == STATE_HIDE_HIGH){
                        windowManager.removeView(restoreWindow);  //移除第二个悬浮窗
                        windowManager.addView(floatingWindow, fwLayoutParams); //添加第一个悬浮窗
                        hideState = STATE_HIDE_LOW;
=======
>>>>>>> master_mirror
                        recentlyHideLevel = LEVEL_HIDE_EXTREMES;
                    }
                    break;
                case R.id.click_restore: //点击恢复第一个悬浮窗
                    if(recentlyHideLevel == LEVEL_HIDE_EXTREMES){
                        windowManager.removeView(restoreWindow);  //移除第二个悬浮窗
                        windowManager.addView(floatingWindow, fwLayoutParams); //添加第一个悬浮窗
                        recentlyHideLevel = LEVEL_HIDE_DEFAULT;
<<<<<<< HEAD
=======
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
                    }
                    break;
                case R.id.click_load_params: //加载参数
                    loadParams();
                    applyParams();
                    windowManager.updateViewLayout(floatingWindow, fwLayoutParams);
                    Toast.makeText(appContext, "加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.click_save_params: //保存参数
                    saveParams();
                    Toast.makeText(appContext, "保存完毕", Toast.LENGTH_SHORT).show();
                    break;
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> master_mirror
                case R.id.click_show_scriptwindow:
                    wHolder.hide(index.CLICKX, LEVEL_HIDE_EXTREMES); //隐藏此窗口
                    wHolder.display(index.SCRIPT);
                    break;
<<<<<<< HEAD
=======
>>>>>>> 2d93302... 提交
>>>>>>> master_mirror
            }

            return false;
        }
    }
}

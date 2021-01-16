package name.kyaru.clickx.window;
import android.os.Handler;

public interface IFunction {
    void click(float x, float y, int count, int delay, Handler messager);
    void disable();
}

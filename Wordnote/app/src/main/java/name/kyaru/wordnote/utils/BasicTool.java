package name.kyaru.wordnote.utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/* 通用工具 */
public class BasicTool {
    private static final Random random = new Random();

    public static String toTimeString(long time){
        Date date = new Date(time);
        DateFormat format = SimpleDateFormat.getInstance();
        return format.format(date);
    }

    public static int generateRandNumber(int bound){
        return random.nextInt(bound);
    }
}

package name.kyaru.wordnote.utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static boolean isSameDay(Date d1, Date d2){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        return c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) //是否是一年中的同一天
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) //是否是同一年
                && c1.get(Calendar.ERA) == c2.get(Calendar.ERA); //是否是同一世纪
    }

    public static boolean isSameDay(long t1, long t2){
        Date d1 = new Date(t1);
        Date d2 = new Date(t2);
        System.out.println(isSameDay(d1, d2));

        return isSameDay(d1, d2);
    }
}

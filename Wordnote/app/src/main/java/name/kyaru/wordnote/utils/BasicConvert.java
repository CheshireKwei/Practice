package name.kyaru.wordnote.utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicConvert {
    public static String toTimeString(long time){
        Date date = new Date(time);
        DateFormat format = SimpleDateFormat.getInstance();
        return format.format(date);
    }
}

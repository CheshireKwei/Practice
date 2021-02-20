package name.kyaru.wordnote.utils;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.datastruct.Word;

/* 通用工具 */
public class BasicTool {
    public static final int LOCATION_DOWNLOADS = 0;
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

    public static void showMessage(Context context, String msg, int showLength){
        Toast.makeText(context, msg, showLength).show();
    }

    public static void showMessage(Context context, String msg){
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    public static JSONArray parseJSONArray(List<Word> words) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Word w : words) {
            JSONObject jo = new JSONObject();
            jo.put(WordDao.FIELD_EN, w.getEn());
            jo.put(WordDao.FIELD_CN, w.getCn());
            jo.put(WordDao.FIELD_TIME, w.getRecordTime());
            jsonArray.put(jo);
        }

        return jsonArray;
    }

    public static List<Word> parseWord(String sJson) throws JSONException{
        List<Word> words = new ArrayList<>();
        JSONArray jArray = new JSONArray(sJson);
        JSONObject jObject = null;
        Word word = null;

        //遍历json数组，取出单词
        for(int i = 0; i < jArray.length(); i++){
            jObject = (JSONObject)jArray.get(i);
            word = new Word(jObject.getString(WordDao.FIELD_EN), jObject.getString(WordDao.FIELD_CN), jObject.getLong(WordDao.FIELD_TIME));
            words.add(word);
        }

        return words;
    }
}

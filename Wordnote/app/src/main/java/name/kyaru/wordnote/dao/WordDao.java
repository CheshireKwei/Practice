package name.kyaru.wordnote.dao;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import name.kyaru.wordnote.datastruct.Word;

public class WordDao {
    public static final String NAME_TIBLE = "word_list";
    public static final String FIELD_ID = "id";
    public static final String FIELD_EN = "en";
    public static final String FIELD_CN = "cn";
    public static final String FIELD_TIME = "time";
    public static final int MODE_ONLY_EN = 0;
    public static final int MODE_ONLY_CN = 1;
    public static final int MODE_ONLY_ID = 2;
    public static final int MODE_EN_OR_CN = 3;
    public static final int MODE_EN_AND_CN = 4;
    private static SQLiteDatabase db;

    public static boolean insert(Word word) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(FIELD_ID, word.getId());
        cvalues.put(FIELD_EN, word.getEn());
        cvalues.put(FIELD_CN, word.getCn());
        cvalues.put(FIELD_TIME, word.getRecordTime());
        long index = db.insert(NAME_TIBLE, null, cvalues);
        if(index > -1){
            return true;
        }

        return false;
    }

    public static boolean update(Word word){
        return false;
    }

    public static boolean delete(Word word, int mode){
        return false;
    }

    public static List<Word> query(Word word, int mode){
        return null;
    }

    public static boolean exists(String en){
        return false;
    }

    public static void setDatabase(SQLiteDatabase db){
        WordDao.db = db;
    }
}

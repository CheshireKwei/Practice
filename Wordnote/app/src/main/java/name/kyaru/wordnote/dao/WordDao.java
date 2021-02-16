package name.kyaru.wordnote.dao;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.LinkedList;
import java.util.List;
import name.kyaru.wordnote.datastruct.Word;

public class WordDao {
    public static final String TABLE_WORDS = "words";
    public static final String TABLE_LAST_WORDS = "last_words";
    public static final String FIELD_EN = "en";
    public static final String FIELD_CN = "cn";
    public static final String FIELD_TIME = "time";
    public static final int MODE_ALL = -114514;
    public static final int MODE_ONLY_EN = 0;
    public static final int MODE_ONLY_CN = 1;
    public static final int MODE_EN_OR_CN = 3;
    public static final int MODE_EN_AND_CN = 4;
    private static SQLiteDatabase db;

    public static boolean insert(Word word, String table) {
        long index = db.insert(table, null, fromWord(word));
        if(index > -1){
            return true;
        }

        return false;
    }

    public static int update(Word word, int mode, String table){
        switch (mode) {
            case MODE_ONLY_EN: //英文相同时更新
                return db.update(table, fromWord(word), "en=?", new String[]{word.getEn()});
            case MODE_ONLY_CN: //中文相同时更新
                return db.update(table, fromWord(word), "cn=?", new String[]{word.getCn()});
            default:
                return 0;
        }
    }

    public static int delete(Word word, int mode, String table){
        switch (mode){
            case MODE_ALL:
                return db.delete(table, null, null);
            case MODE_EN_AND_CN: //中英文匹配时删除
                return db.delete(table, "en=? AND cn = ?", new String[]{word.getEn(), word.getCn()});
            default:
                return 0;
        }
    }

    public static List<Word> query(Word word, int mode, String table){
        Cursor results = detailQuery(word, mode, table);
        if(results != null && results.getCount() > 0){
            List<Word> words = new LinkedList<>();
            if(results.moveToFirst()) {
                do {
                    Word w = new Word();
                    w.setEn(results.getString(results.getColumnIndex(WordDao.FIELD_EN)));
                    w.setCn(results.getString(results.getColumnIndex(WordDao.FIELD_CN)));
                    w.setRecordTime(results.getLong(results.getColumnIndex(WordDao.FIELD_TIME)));
                    w.setTable(table); //记录单词来自哪张表
                    words.add(w);
                } while (results.moveToNext());
            }
            results.close();
            return words;
        }

        return null;
    }

    public static boolean exists(Word word, int mode, String table){
        Cursor results =  detailQuery(word, mode, table);
        boolean b = results != null;
        results.close();

        return b;
    }

    public static ContentValues fromWord(Word word){
        ContentValues cv = new ContentValues();
        cv.put(FIELD_EN, word.getEn());
        cv.put(FIELD_CN, word.getCn());
        cv.put(FIELD_TIME, word.getRecordTime());

        return cv;
    }

    public static void setDatabase(SQLiteDatabase db){
        WordDao.db = db;
    }

    private static Cursor detailQuery(Word word, int mode, String table){
        switch (mode){
            case MODE_ALL:
                return db.query(table, null, null, null, null, null, null);
            case MODE_EN_AND_CN:
                return db.query(table, null, "en = ? AND cn = ?", new String[]{word.getEn(), word.getCn()}, null, null, null);
            case MODE_ONLY_EN:
                return db.query(table, null, "en = ?", new String[]{word.getEn()}, null, null, null);
            case MODE_ONLY_CN:
                return db.query(table, null, "cn = ?", new String[]{word.getCn()}, null, null, null);
            case MODE_EN_OR_CN:
                return db.query(table, null, "en = ? OR cn = ?", new String[]{word.getEn(), word.getCn()}, null, null, null);
            default:
                throw new IllegalArgumentException("excepted mode");
        }
    }
}

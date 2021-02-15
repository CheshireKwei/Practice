package name.kyaru.wordnote.utils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import name.kyaru.wordnote.dao.WordDao;

/* 创建数据库 */
public class DatabaseCreator extends SQLiteOpenHelper {
    public static final String DBNAME_WORDS = "words";

    public DatabaseCreator(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `" + WordDao.TABLE_WORDS + "`(" +
            WordDao.FIELD_EN + " VARCHAR(30) NOT NULL," +
            WordDao.FIELD_CN + " VARCHAR(30) NOT NULL," +
            "`" + WordDao.FIELD_TIME + "`" + " LONG NOT NULL" +
            ")"
        );

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `" + WordDao.TABLE_LAST_WORDS + "`(" +
            WordDao.FIELD_EN + " VARCHAR(30) NOT NULL," +
            WordDao.FIELD_CN + " VARCHAR(30) NOT NULL," +
            "`" + WordDao.FIELD_TIME + "`" + " LONG NOT NULL" +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

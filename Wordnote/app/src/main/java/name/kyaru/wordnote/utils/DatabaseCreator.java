package name.kyaru.wordnote.utils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import name.kyaru.wordnote.dao.WordDao;

public class DatabaseCreator extends SQLiteOpenHelper {

    public DatabaseCreator(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `" + WordDao.NAME_TIBLE + "`(" +
            WordDao.FIELD_ID + " INT PRIMARY KEY," +
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

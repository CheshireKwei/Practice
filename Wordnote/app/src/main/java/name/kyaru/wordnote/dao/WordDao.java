package name.kyaru.wordnote.dao;
import java.util.List;
import name.kyaru.wordnote.datastruct.Word;

public class WordDao {
    public static final String FIELD_ID = "id";
    public static final String FIELD_EN = "en";
    public static final String FIELD_CN = "cn";
    public static final String FIELD_TIME = "time";
    public static final int MODE_ONLY_EN = 0;
    public static final int MODE_ONLY_CN = 1;
    public static final int MODE_ONLY_ID = 2;
    public static final int MODE_EN_OR_CN = 3;
    public static final int MODE_EN_AND_CN = 4;

    public static boolean insert(Word word) {
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
}

package name.kyaru.wordnote;

import org.junit.Test;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import name.kyaru.wordnote.bgm.EffectManager;
import name.kyaru.wordnote.datastruct.Word;
import name.kyaru.wordnote.utils.BasicTool;
import name.kyaru.wordnote.utils.CnSelectionGenerator;
import name.kyaru.wordnote.utils.Generalisable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        List<String> ss = new LinkedList<>();
        ss.add(0, "hello");
        ss.add(1, "o");
        System.out.println("" + ss.get(1));
    }
}
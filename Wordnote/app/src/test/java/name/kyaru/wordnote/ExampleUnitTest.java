package name.kyaru.wordnote;

import org.junit.Test;
import java.util.LinkedList;
import java.util.List;
import name.kyaru.wordnote.datastruct.Word;
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
        List<Word> words = new LinkedList<>();
        words.add(new Word("c", "bt", 1)); //0
        words.add(new Word("ca", "br", 1)); //1

        words.add(new Word("cb", "bqq", 1)); //2

        words.add(new Word("cc", "bww", 1)); //3
        words.add(new Word("cd", "bae", 1)); //4
        List<String> s = null;
        Generalisable<Word, List<String>> g = new CnSelectionGenerator();
        s = g.generate(words, 3, 2, true);

        for (String ss : s){
            System.out.println(ss);
        }
    }
}
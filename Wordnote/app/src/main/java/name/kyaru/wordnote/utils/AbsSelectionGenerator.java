package name.kyaru.wordnote.utils;
import java.util.List;
import name.kyaru.wordnote.datastruct.Word;

public abstract class AbsSelectionGenerator implements Generalisable<Word, List<String>> {
    //随机选取元素，注意head及tail均需是有效的索引，不要传递大小
    protected int randGenerate(List<String> selections, int count, List<Word> words, int head, int tail){
        if(head < tail) { //当头部大于尾部时，说明超出了范围。
            if(selections.size() < count) {
                int s1 = head + BasicTool.generateRandNumber(tail - head + 1); //公式:起点 + 偏移量
                selections.add(words.get(s1).getCn());
                int leftTail = s1 - 1; //尾部持续减小
                int rightHead = s1 + 1;//首部持续增大
                //先递归左边
                randGenerate(selections, count, words, head, leftTail);
                //再递归右边
                randGenerate(selections, count, words, rightHead, tail);
            }
        }

        return selections.size();
    }
}

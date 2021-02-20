package name.kyaru.wordnote.utils;
import java.util.LinkedList;
import java.util.List;
import name.kyaru.wordnote.datastruct.Word;

public class CnSelectionGenerator extends AbsSelectionGenerator {
    public CnSelectionGenerator(){}

    //以words作为数据源生成随机的选项
    @Override
    public List<String> generate(List<Word> words, int count, int selected, boolean needAdd) {
        List<String> selections = new LinkedList<>();
        if(needAdd) { //如果需要添加已选项，则保留一个位置
            generate(selections, count - 1, words, selected);
            int insertIndex = BasicTool.generateRandNumber(count);
            selections.add(insertIndex, words.get(selected).getCn()); //将答案插入到随机下标，以达到选项随机排序的效果
        }else{ //不需要添加已选项，则全部生成
            generate(selections, count, words, selected);
        }

        return selections;
    }

    @Override
    public List<String> generate(List<String> selections, int count, List<Word> origin, int selected) {
        int size = randGenerate(selections, count, origin, 0, selected - 1); //以selected为中点分成两段递归，selected的前一段
        if(size < count){ //如果选项个数未满足
            int size1 =  randGenerate(selections, count, origin, selected + 1, origin.size() - 1); //selected的后一段
            while(size1 < count){
                selections.add(origin.get(BasicTool.generateRandNumber(origin.size())).getCn()); //如果两段递归后仍无法满足个数，说明单词数量过少，采用拼凑
                ++size1;
            }
        }

        return selections;
    }
}

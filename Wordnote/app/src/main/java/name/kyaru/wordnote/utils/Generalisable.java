package name.kyaru.wordnote.utils;
import java.util.List;

public interface Generalisable<T, R> {
    R generate(List<T> origin, int count, int selected, boolean needAdd);
    R generate(R selections, int count, List<T> origin, int selected);
}

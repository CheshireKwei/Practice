package name.kyaru.wordnote.datastruct;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import name.kyaru.wordnote.R;
import name.kyaru.wordnote.dao.WordDao;
import name.kyaru.wordnote.utils.BasicTool;

/* RecyclerView 的适配器 */
public class WordAdapter extends RecyclerView.Adapter {
    private List<Word> words;
    private Context context;

    public WordAdapter(List<Word> words, Context context){
        this.words = words;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //填充View时将parent传递进去，则View可以根据parent生成布局参数，可以居中
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_explore, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WordViewHolder wvh = (WordViewHolder)holder;
        Word w = words.get(position);

        //设置文本
        wvh.showId.setText(String.valueOf(position + 1));
        wvh.showEn.setText(w.getEn());
        wvh.showCn.setText(w.getCn());

        //设置监听
        View.OnClickListener onClickImpl = (v) -> {
            switch (v.getId()) {
                case R.id.click_show_time:
                    Toast.makeText(context, "记录于" + BasicTool.toTimeString(words.get(position).getRecordTime()), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.click_delete:
                    removeItem(position);
                    break;
            }
        };
        wvh.clickShowTime.setOnClickListener(onClickImpl);
        wvh.clickDelete.setOnClickListener(onClickImpl);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    //移除子项
    private void removeItem(int position){
        WordDao.delete(words.get(position), WordDao.MODE_EN_AND_CN, WordDao.TABLE_LAST_WORDS); //先删除数据库记录
        words.remove(position);//移除集合元素
        notifyItemRemoved(position);//移除视图项目
        notifyItemRangeChanged(position, words.size() - position); //刷新发生改变的位置的数据及其后的数据
    }

    //保存子项视图的容器
    private class WordViewHolder extends RecyclerView.ViewHolder{
        public final TextView showId;
        public final TextView showEn;
        public final TextView showCn;
        public final ImageButton clickShowTime;
        public final ImageButton clickDelete;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            showId = itemView.findViewById(R.id.show_id);
            showEn = itemView.findViewById(R.id.show_en);
            showCn = itemView.findViewById(R.id.show_cn);
            clickShowTime = itemView.findViewById(R.id.click_show_time);
            clickDelete = itemView.findViewById(R.id.click_delete);
        }
    }
}

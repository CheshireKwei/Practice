package name.kyaru.wordnote.bgm;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import name.kyaru.wordnote.R;
import name.kyaru.wordnote.utils.BasicTool;

public class EffectManager {
    public static final int TYPE_BEGIN = 0;
    public static final int TYPE_LAST = 1;
    public static final int TYPE_LOSE = 2;
    public static final int TYPE_PRAISE = 3;
    public static final int TYPE_REGRET = 4;
    public static final int TYPE_VICTORY = 5;
    private static final int MUSIC_NUM = 16;
    private static final int NUM_TYPE = 6;
    private static EffectManager singleton = null;
    private static int[][] resourceIds = null;
    private SoundPool.Builder spBuilder;
    private SoundPool sPool;
    private Map<Integer, Integer> idMapping = new HashMap<>();
    private Context context;
    private Callback callback;
    private boolean doNotPlay = false;

    static {
        resourceIds = new int[MUSIC_NUM][NUM_TYPE];

        resourceIds[0][0] = R.raw.akari_begin;
        resourceIds[0][1] = R.raw.akari_last;
        resourceIds[0][2] = R.raw.akari_lose;
        resourceIds[0][3] = R.raw.akari_praise;
        resourceIds[0][4] = R.raw.akari_regret;
        resourceIds[0][5] = R.raw.akari_victory;

        resourceIds[1][0] = R.raw.kokkoro_begin;
        resourceIds[1][1] = R.raw.kokkoro_last;
        resourceIds[1][2] = R.raw.kokkoro_lose;
        resourceIds[1][3] = R.raw.kokkoro_praise;
        resourceIds[1][4] = R.raw.kokkoro_regret;
        resourceIds[1][5] = R.raw.kokkoro_victory;

        resourceIds[2][0] = R.raw.pecorine_begin;
        resourceIds[2][1] = R.raw.pecorine_last;
        resourceIds[2][2] = R.raw.pecorine_lose;
        resourceIds[2][3] = R.raw.pecorine_praise;
        resourceIds[2][4] = R.raw.pecorine_regret;
        resourceIds[2][5] = R.raw.pecorine_victory;

        resourceIds[3][0] = R.raw.kyaru_begin;
        resourceIds[3][1] = R.raw.kyaru_last;
        resourceIds[3][2] = R.raw.kyaru_lose;
        resourceIds[3][3] = R.raw.kyaru_praise;
        resourceIds[3][4] = R.raw.kyaru_regret;
        resourceIds[3][5] = R.raw.kyaru_victory;

        resourceIds[4][0] = R.raw.miyako_begin;
        resourceIds[4][1] = R.raw.miyako_last;
        resourceIds[4][2] = R.raw.miyako_lose;
        resourceIds[4][3] = R.raw.miyako_praise;
        resourceIds[4][4] = R.raw.miyako_regret;
        resourceIds[4][5] = R.raw.miyako_victory;

        resourceIds[5][0] = R.raw.maho_begin;
        resourceIds[5][1] = R.raw.maho_last;
        resourceIds[5][2] = R.raw.maho_lose;
        resourceIds[5][3] = R.raw.maho_praise;
        resourceIds[5][4] = R.raw.maho_regret;
        resourceIds[5][5] = R.raw.maho_victory;
    }

    private EffectManager(Context context, Callback callback){
        this.context = context;
        this.callback = callback;
        createSoundPool();
        loadAudio();
    }

    //获取效果管理器的对象
    public static EffectManager getInstance(Context context, Callback callback){
        if(singleton != null){
            return singleton;
        }
        singleton = new EffectManager(context, callback);
        return singleton;
    }

    //首次调用含Context的getInstance()后才能调用此方法
    public static EffectManager getInstance(){
        if(singleton == null){
            throw new IllegalStateException("EffectManager未初始化");
        }
        return singleton;
    }

    //播放音效
    public void play(int type){
        if(!doNotPlay){ //可以播放
            int mid = idMapping.get(type);
            sPool.play(mid, 1, 1, 0, 0, 1);
        }
    }

    public void release(){
        sPool.release();
    }

    public void setDoNotPlay(boolean b){
        this.doNotPlay = b;
    }

    //随机加载音效
    private void loadAudio(){
        int role = BasicTool.generateRandNumber(6);
        idMapping.put(TYPE_BEGIN, sPool.load(context, resourceIds[role][TYPE_BEGIN], 1));
        idMapping.put(TYPE_LAST, sPool.load(context, resourceIds[role][TYPE_LAST], 1));
        idMapping.put(TYPE_LOSE, sPool.load(context, resourceIds[role][TYPE_LOSE], 1));
        idMapping.put(TYPE_PRAISE, sPool.load(context, resourceIds[role][TYPE_PRAISE], 1));
        idMapping.put(TYPE_REGRET, sPool.load(context, resourceIds[role][TYPE_REGRET], 1));
        idMapping.put(TYPE_VICTORY, sPool.load(context, resourceIds[role][TYPE_VICTORY], 1));
    }

    //创建SoundPool
    private void createSoundPool(){
        if(Build.VERSION.SDK_INT >= 21){ //当SDK版本在21以上时
            AudioAttributes.Builder abuilder = new AudioAttributes.Builder();
            abuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            spBuilder = new SoundPool.Builder();
            spBuilder.setMaxStreams(6);
            spBuilder.setAudioAttributes(abuilder.build());
            sPool = spBuilder.build();
        }else{
            sPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        sPool.setOnLoadCompleteListener(new OnLoadCompleteListenerImpl(6)); //设置加载成功的监听器
    }

    private class OnLoadCompleteListenerImpl implements SoundPool.OnLoadCompleteListener{
        private int targetNum = 0; //准备加载的音乐数量
        private int loadCompleteNum = 0; //已加载的数量

        public OnLoadCompleteListenerImpl(int targetNum){
            this.targetNum = targetNum;
        }

        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            loadCompleteNum++;
            if(loadCompleteNum >= targetNum){
                if(callback != null) {
                    callback.callBack(); //调用回调接口中的方法
                }
            }
        }
    }
}

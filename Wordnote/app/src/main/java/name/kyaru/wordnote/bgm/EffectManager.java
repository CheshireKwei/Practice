package name.kyaru.wordnote.bgm;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
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
    private static final int MUSIC_NUM = 19;
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

        //茜里
        resourceIds[0][0] = R.raw.akari_begin;
        resourceIds[0][1] = R.raw.akari_last;
        resourceIds[0][2] = R.raw.akari_lose;
        resourceIds[0][3] = R.raw.akari_praise;
        resourceIds[0][4] = R.raw.akari_regret;
        resourceIds[0][5] = R.raw.akari_victory;

        //可可萝
        resourceIds[1][0] = R.raw.kokkoro_begin;
        resourceIds[1][1] = R.raw.kokkoro_last;
        resourceIds[1][2] = R.raw.kokkoro_lose;
        resourceIds[1][3] = R.raw.kokkoro_praise;
        resourceIds[1][4] = R.raw.kokkoro_regret;
        resourceIds[1][5] = R.raw.kokkoro_victory;

        //佩可莉姆
        resourceIds[2][0] = R.raw.pecorine_begin;
        resourceIds[2][1] = R.raw.pecorine_last;
        resourceIds[2][2] = R.raw.pecorine_lose;
        resourceIds[2][3] = R.raw.pecorine_praise;
        resourceIds[2][4] = R.raw.pecorine_regret;
        resourceIds[2][5] = R.raw.pecorine_victory;

        //凯露
        resourceIds[3][0] = R.raw.kyaru_begin;
        resourceIds[3][1] = R.raw.kyaru_last;
        resourceIds[3][2] = R.raw.kyaru_lose;
        resourceIds[3][3] = R.raw.kyaru_praise;
        resourceIds[3][4] = R.raw.kyaru_regret;
        resourceIds[3][5] = R.raw.kyaru_victory;

        //宫子
        resourceIds[4][0] = R.raw.miyako_begin;
        resourceIds[4][1] = R.raw.miyako_last;
        resourceIds[4][2] = R.raw.miyako_lose;
        resourceIds[4][3] = R.raw.miyako_praise;
        resourceIds[4][4] = R.raw.miyako_regret;
        resourceIds[4][5] = R.raw.miyako_victory;

        //真步
        resourceIds[5][0] = R.raw.maho_begin;
        resourceIds[5][1] = R.raw.maho_last;
        resourceIds[5][2] = R.raw.maho_lose;
        resourceIds[5][3] = R.raw.maho_praise;
        resourceIds[5][4] = R.raw.maho_regret;
        resourceIds[5][5] = R.raw.maho_victory;

        //唯一神
        resourceIds[6][0] = R.raw.kyaru2_begin;
        resourceIds[6][1] = R.raw.kyaru2_last;
        resourceIds[6][2] = R.raw.kyaru2_lose;
        resourceIds[6][3] = R.raw.kyaru2_praise;
        resourceIds[6][4] = R.raw.kyaru2_regret;
        resourceIds[6][5] = R.raw.kyaru2_victory;

        //初音
        resourceIds[7][0] = R.raw.hatsune_begin;
        resourceIds[7][1] = R.raw.hatsune_last;
        resourceIds[7][2] = R.raw.hatsune_lose;
        resourceIds[7][3] = R.raw.hatsune_praise;
        resourceIds[7][4] = R.raw.hatsune_regret;
        resourceIds[7][5] = R.raw.hatsune_victory;

        //春nnk
        resourceIds[8][0] = R.raw.neneka_begin;
        resourceIds[8][1] = R.raw.neneka_last;
        resourceIds[8][2] = R.raw.neneka_lose;
        resourceIds[8][3] = R.raw.neneka_praise;
        resourceIds[8][4] = R.raw.neneka_regret;
        resourceIds[8][5] = R.raw.neneka_victory;

        //日和
        resourceIds[9][0] = R.raw.hiyori_begin;
        resourceIds[9][1] = R.raw.hiyori_last;
        resourceIds[9][2] = R.raw.hiyori_lose;
        resourceIds[9][3] = R.raw.hiyori_praise;
        resourceIds[9][4] = R.raw.hiyori_regret;
        resourceIds[9][5] = R.raw.hiyori_victory;

        //镜华
        resourceIds[10][0] = R.raw.kyoka_begin;
        resourceIds[10][1] = R.raw.kyoka_last;
        resourceIds[10][2] = R.raw.kyoka_lose;
        resourceIds[10][3] = R.raw.kyoka_praise;
        resourceIds[10][4] = R.raw.kyoka_regret;
        resourceIds[10][5] = R.raw.kyoka_victory;

        //望
        resourceIds[11][0] = R.raw.nozomi_begin;
        resourceIds[11][1] = R.raw.nozomi_last;
        resourceIds[11][2] = R.raw.nozomi_lose;
        resourceIds[11][3] = R.raw.nozomi_praise;
        resourceIds[11][4] = R.raw.nozomi_regret;
        resourceIds[11][5] = R.raw.nozomi_victory;
        
        //妹弓
        resourceIds[12][0] = R.raw.rino_begin;
        resourceIds[12][1] = R.raw.rino_last;
        resourceIds[12][2] = R.raw.rino_lose;
        resourceIds[12][3] = R.raw.rino_praise;
        resourceIds[12][4] = R.raw.rino_regret;
        resourceIds[12][5] = R.raw.rino_victory;
        
        //妹龙
        resourceIds[13][0] = R.raw.shefi_begin;
        resourceIds[13][1] = R.raw.shefi_last;
        resourceIds[13][2] = R.raw.shefi_lose;
        resourceIds[13][3] = R.raw.shefi_praise;
        resourceIds[13][4] = R.raw.shefi_regret;
        resourceIds[13][5] = R.raw.shefi_victory;
        
        //栞
        resourceIds[14][0] = R.raw.shiori_begin;
        resourceIds[14][1] = R.raw.shiori_last;
        resourceIds[14][2] = R.raw.shiori_lose;
        resourceIds[14][3] = R.raw.shiori_praise;
        resourceIds[14][4] = R.raw.shiori_regret;
        resourceIds[14][5] = R.raw.shiori_victory;
        
        //姐姐
        resourceIds[15][0] = R.raw.shizuru_begin;
        resourceIds[15][1] = R.raw.shizuru_last;
        resourceIds[15][2] = R.raw.shizuru_lose;
        resourceIds[15][3] = R.raw.shizuru_praise;
        resourceIds[15][4] = R.raw.shizuru_regret;
        resourceIds[15][5] = R.raw.shizuru_victory;
        
        //猫剑
        resourceIds[16][0] = R.raw.tamaki_begin;
        resourceIds[16][1] = R.raw.tamaki_last;
        resourceIds[16][2] = R.raw.tamaki_lose;
        resourceIds[16][3] = R.raw.tamaki_praise;
        resourceIds[16][4] = R.raw.tamaki_regret;
        resourceIds[16][5] = R.raw.tamaki_victory;

        //姐法
        resourceIds[17][0] = R.raw.yori_begin;
        resourceIds[17][1] = R.raw.yori_last;
        resourceIds[17][2] = R.raw.yori_lose;
        resourceIds[17][3] = R.raw.yori_praise;
        resourceIds[17][4] = R.raw.yori_regret;
        resourceIds[17][5] = R.raw.yori_victory;

        //对不起
        resourceIds[18][0] = R.raw.yui_begin;
        resourceIds[18][1] = R.raw.yui_last;
        resourceIds[18][2] = R.raw.yui_lose;
        resourceIds[18][3] = R.raw.yui_praise;
        resourceIds[18][4] = R.raw.yui_regret;
        resourceIds[18][5] = R.raw.yui_victory;
        
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

    public boolean isDoNotPlay(){
        return doNotPlay;
    }

    public void setDoNotPlay(boolean b){
        this.doNotPlay = b;
    }

    //随机加载音效
    private void loadAudio(){
        int role = BasicTool.generateRandNumber(MUSIC_NUM);
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

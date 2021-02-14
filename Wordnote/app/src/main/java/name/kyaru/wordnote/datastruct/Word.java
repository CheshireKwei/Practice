package name.kyaru.wordnote.datastruct;

public class Word {
    private int id;
    private String en;
    private String cn;
    private long recordTime;

    public Word(){}

    public Word(String en, String cn, long rtime){
        this.en = en;
        this.cn = cn;
        this.recordTime = rtime;
    }

    public Word(int id, String en, String cn, long rtime){
        this(en, cn, rtime);
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}

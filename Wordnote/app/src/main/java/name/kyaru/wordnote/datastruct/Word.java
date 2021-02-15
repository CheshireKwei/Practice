package name.kyaru.wordnote.datastruct;

/* 单词的 Bean */
public class Word {
    private String en;
    private String cn;
    private long recordTime;

    public Word(){}

    public Word(String en, String cn, long rtime){
        this.en = en;
        this.cn = cn;
        this.recordTime = rtime;
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
}

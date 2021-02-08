package name.kyaru.clickx.struct;

public class PointData {
    private long time;
    private final int[] position = new int[2];

    public PointData(int x, int y, long time){
        position[0] = x;
        position[1] = y;
        this.time = time;
    }

    public int[] getPosition(){
        return position;
    }

    public long getTime(){
        return time;
    }
}

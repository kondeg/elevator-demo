package demo.kondeg.elevator;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class Request {

    int floorRequested;

    private Request(){}

    public Request(int floorRequested) {
        this.floorRequested = floorRequested;
    }

    public int getFloorRequested() {
        return floorRequested;
    }

    public void setFloorRequested(int floorRequested) {
        this.floorRequested = floorRequested;
    }
}

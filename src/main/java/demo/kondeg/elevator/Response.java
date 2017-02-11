package demo.kondeg.elevator;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class Response {

    enum ResponseCode {
        IDLE, PASSING, IDLE_SAME_FLOOR
    }

    int floorsAway;

    ResponseCode responseCode;

    private Response() {

    }

    public Response(int elevatorId) {
        this.elevatorId=elevatorId;
    }

    int elevatorId;


    public int getFloorsAway() {
        return floorsAway;
    }

    public void setFloorsAway(int floorsAway) {
        this.floorsAway = floorsAway;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(int elevatorId) {
        this.elevatorId = elevatorId;
    }

}

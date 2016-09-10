package utilities;

public class SnappedPoint {

    /********************************* LOCATION *********************************/
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /********************************* Original Index *********************************/

    private int originalIndex;

    public int getOriginalIndex() {
        return originalIndex;
    }

    public void setOriginalIndex(int originalIndex) {
        this.originalIndex = originalIndex;
    }

    /*********************************  PLACE ID *********************************/

    private String placeId;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}

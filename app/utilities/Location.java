package utilities;

public class Location {

    /*-------------------------------------- LATITUDE --------------------------------------*/
    private double latitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    /*-------------------------------------- LONGITUDE --------------------------------------*/
    private double longitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    /*-------------------------------------- CONTROLLERS --------------------------------------*/

    public Location(){}

    public Location(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }



}

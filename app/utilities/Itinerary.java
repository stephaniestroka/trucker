package utilities;

public class Itinerary {

    private final Location[] locations;
    private int currentPosition = 0;


    public Itinerary(Location[] locations) {
        this.locations = locations;
    }

    public Location nextLocation(){
        Location currentLocation = locations[currentPosition];
        currentPosition++;
        return currentLocation;
    }

    public boolean isCompleted() {
        return currentPosition >= locations.length;
    }
}

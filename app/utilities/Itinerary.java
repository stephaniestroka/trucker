package utilities;

public class Itinerary {

    private final Location[] locations;
    private int currentPosition = 0;


    public Itinerary(Location[] locations) {
        this.locations = locations;
    }

    public Location[] next100Locations() {
        int max = Math.min(locations.length - currentPosition, 100);
        Location[] next100Locations = new Location[max];
        for (int i = 0; i < max; i++) {
            next100Locations[i] = locations[currentPosition];
            currentPosition++;
        }
        return next100Locations;
    }

    public boolean isCompleted() {
        return currentPosition >= locations.length;
    }
}

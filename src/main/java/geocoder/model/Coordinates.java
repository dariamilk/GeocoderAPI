package geocoder.model;

import java.util.Objects;

public class Coordinates {
    private double lon;
    private double lat;
    public Coordinates () {};

    public Coordinates(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.lon, lon) == 0 && Double.compare(that.lat, lat) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lon, lat);
    }
}

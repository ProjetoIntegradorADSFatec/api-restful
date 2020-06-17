package api.restful.model.geojson;

import java.util.List;

public class Geometry {
    private String type;
    private List<List<List<Double>>> coordinates;

    public Geometry() {}

    public Geometry(String type, List<List<List<Double>>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<List<Double>>> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }
}
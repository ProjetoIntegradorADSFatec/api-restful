package api.restful.model.geojson;

import java.util.List;

public class Geojson {
    private String type;
    private List<Feature> features;

    public Geojson() {}

    public Geojson(String type, List<Feature> features) {
        this.type = type;
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
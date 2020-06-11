package api.restful.model.geojson;

public class Request {
    private Datetime dateTime;
    private String band;
    private Geojson geojson;

    public Request() {}

    public Request(Datetime dateTime, String band, Geojson geojson) {
        this.dateTime = dateTime;
        this.band = band;
        this.geojson = geojson;
    }

    public Datetime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Datetime dateTime) {
        this.dateTime = dateTime;
    }

    public String getBand() {
        return this.band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public Geojson getGeojson() {
        return this.geojson;
    }

    public void setGeojson(Geojson geojson) {
        this.geojson = geojson;
    }
}
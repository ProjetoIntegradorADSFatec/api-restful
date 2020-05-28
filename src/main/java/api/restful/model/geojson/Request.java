package api.restful.model.geojson;

public class Request {
    private String dateTime;
    private String band;
    private Geojson geojson;

    public Request() {}

    public Request(String dateTime, String band, Geojson geojson) {
        this.dateTime = dateTime;
        this.band = band;
        this.geojson = geojson;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
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
package api.restful.model.geojson;

public class Properties {
    private String projection;
    private String band;
    private Datetime datetime;

    public Properties() {}

    public Properties(String projection, String band, Datetime datetime) {
        this.projection = projection;
        this.band = band;
        this.datetime = datetime;
    }

    public String getProjection() {
        return this.projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public String getBand() {
        return this.band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public Datetime getDatetime() {
        return this.datetime;
    }

    public void setDatetime(Datetime datetime) {
        this.datetime = datetime;
    }


}
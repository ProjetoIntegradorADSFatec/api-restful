package api.restful.model.geojson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datetime {
    private String start;
    private String end;

    public Datetime() {
    }

    public Datetime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() throws ParseException {
        return new SimpleDateFormat("yyyy-mm-dd").parse(this.start);
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Date getEnd() throws ParseException {
        return new SimpleDateFormat("yyyy-mm-dd").parse(this.end);
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
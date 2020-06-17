package api.restful.model.catalog;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name="coordinate")
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projection;
    private double latitude;
    private double longitude;
    @ManyToOne
    @JoinColumn(name="catalog_id")
    private Catalog catalog;

    public Coordinate() {}

    public Coordinate(String projection, double latitude, double longitude, Catalog catalog) {
        this.projection = projection;
        this.latitude = latitude;
        this.longitude = longitude;
        this.catalog = catalog;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjection() {
        return this.projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
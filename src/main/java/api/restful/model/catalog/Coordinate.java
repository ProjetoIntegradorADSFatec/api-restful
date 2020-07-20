package api.restful.model.catalog;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;
import api.restful.model.views.Views;

@Entity
@Table(name="coordinate")
public class Coordinate {
    @Id
    @JsonView(Views.Internal.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.Public.class)
    private String projection;

    @JsonView(Views.Public.class)
    private double latitude;

    @JsonView(Views.Public.class)
    private double longitude;

    @ManyToOne
    @JsonView(Views.Internal.class)
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
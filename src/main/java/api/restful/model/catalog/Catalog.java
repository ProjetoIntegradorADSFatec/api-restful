package api.restful.model.catalog;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;
import api.restful.model.views.Views;

@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @JsonView(Views.Internal.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String description;

    @JsonView(Views.Public.class)
    private String band;

    @JsonView(Views.Public.class)
    private String dateTime;

    @JsonView(Views.Public.class)
    @OneToMany(targetEntity = Coordinate.class, mappedBy = "catalog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coordinate> coordinates;

    @JsonView(Views.Public.class)
    private String image;

    public Catalog() {}

    public Catalog(String name, String description, String band, String dateTime, List<Coordinate> coordinates, String image) {
        this.name = name;
        this.description = description;
        this.band = band;
        this.dateTime = dateTime;
        this.coordinates = coordinates;
        this.image = image;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBand() {
        return this.band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<Coordinate> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
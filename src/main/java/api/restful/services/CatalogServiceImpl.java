package api.restful.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.awt.Point;
import java.awt.Polygon;

import api.restful.model.catalog.*;
import api.restful.model.collection.*;
import api.restful.model.collection.Feature;
import api.restful.model.geojson.*;

@Service("CatalogService")
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;

    public List<Catalog> listCatalog() {
        try {
            List<Catalog> catalog_response = new ArrayList<Catalog>();
            List<Catalog> catalogs = (List<Catalog>) this.catalogRepository.findAll();
            List<Coordinate> coordinates = (List<Coordinate>) this.coordinateRepository.findAll();
            for (Catalog catalog : catalogs) {
                Catalog cat = new Catalog(
                    catalog.getName(),
                    catalog.getDescription(),
                    catalog.getBand(),
                    catalog.getDateTime(),
                    new ArrayList<Coordinate>(),
                    catalog.getImage()
                );
                cat.setId(catalog.getId());
                for (Coordinate coord : coordinates) {
                    if ( coord.getCatalog().getId().equals(catalog.getId())) {
                        Coordinate latlong = new Coordinate(
                            coord.getProjection(),
                            coord.getLatitude(),
                            coord.getLongitude(),
                            null
                         );
                        latlong.setId(coord.getId());
                        cat.getCoordinates().add(latlong);
                    }
                }
                catalog_response.add(cat);
            }
            return catalog_response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Catalog>();
        }
    }

    @Override
    public Item listItems() {
        try {
            Item item = new Item(new ArrayList<Feature>(), 0, 0, "FeatureCollection");
            List<Catalog> catalogs = (List<Catalog>) this.catalogRepository.findAll();
            List<Coordinate> coordinates = (List<Coordinate>) this.coordinateRepository.findAll();
            for (Catalog catalog : catalogs) {
                Feature feature = new Feature(
                    catalog.getId(),
                    new ArrayList<Asset>(),
                    new ArrayList<Double>(),
                    catalog.getName(),
                    new Geometry(
                        "Polygon",
                        new ArrayList<List<List<Double>>>()
                    ),
                    new Properties(),
                    catalog.getDescription()
                );
                feature.getProperties().setBand(catalog.getBand());
                feature.getAssets().add(new Asset(
                    catalog.getBand(),
                    catalog.getImage()
                ));
                feature.getGeometry().getCoordinates().add(new ArrayList<List<Double>>());
                for (Coordinate coord : coordinates) {
                    if ( coord.getCatalog().getId().equals(catalog.getId())) {
                        List<Double> latlong = new ArrayList<Double>();
                        latlong.add(new Double(coord.getLatitude()));
                        latlong.add(new Double(coord.getLongitude()));
                        feature.getGeometry().getCoordinates().get(0).add(latlong);
                        feature.getBbox().add(coord.getLongitude());
                        feature.getBbox().add(coord.getLatitude());
                        feature.getProperties().setProjection(coord.getProjection());
                        item.setNumberMatched(item.getNumberMatched() + 1);
                    }
                }
                feature.getProperties().setDatetime(new Datetime(catalog.getDateTime(),catalog.getDateTime()));
                item.getFeatures().add(feature);
            }
            item.setNumberReturned(item.getFeatures().size());
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return new Item();
        }
    }

    @Override
    public Item search(Geojson geojson) {
        Item item = this.listItems();
        Item result = new Item(new ArrayList<Feature>(), 0, 0, "FeatureCollection");
        int[] x = {};
        int[] y = {};
        try {
            for(List<Double> lonlat : geojson.getFeatures().get(0).getGeometry().getCoordinates().get(0)) {
                x = Arrays.copyOf(x, x.length + 1); x[x.length - 1] = lonlat.get(1).intValue();
                y = Arrays.copyOf(y, y.length + 1); y[y.length - 1] = lonlat.get(0).intValue();
            }
            Polygon polygon = new Polygon(x,y, x.length);
            for (Feature feature: item.getFeatures()) {
                for (List<Double> coord : feature.getGeometry().getCoordinates().get(0)) {
                    int lat = coord.get(0).intValue();
                    int lon = coord.get(1).intValue();
                    Point point = new Point(lat,lon);
                    if (
                        polygon.contains(point) &&
                            this.intervals(
                                geojson.getFeatures().get(0).getProperties().getDatetime().getStart(),
                                feature.getProperties().getDatetime().getEnd(),
                                geojson.getFeatures().get(0).getProperties().getDatetime().getEnd()
                            ) &&
                                feature.getProperties().getBand().toLowerCase().equals(geojson.getFeatures().get(0).getProperties().getBand().toLowerCase())
                    ) {
                        result.getFeatures().add(feature);
                        result.setNumberMatched(result.getNumberMatched() + 1);
                        break;
                    }
                }
            }
            result.setNumberReturned(result.getFeatures().size());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Item();
        }
    }

    @Override
    public Catalog findById(Long id) {
        List<Catalog> catalogs = this.listCatalog();
        Catalog result = new Catalog();
        for (Catalog catalog : catalogs) {
            if (catalog.getId().equals(id)) {
                result = catalog;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean remove(Catalog catalog) {
        try {
            this.catalogRepository.delete(catalog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Catalog catalog) {
        try {
            Catalog cat = new Catalog(
                catalog.getName(),
                catalog.getDescription(),
                catalog.getBand(),
                catalog.getDateTime(),
                new ArrayList<Coordinate>(),
                catalog.getImage()
            );
            this.catalogRepository.save(cat);
            for (Coordinate coordinate: catalog.getCoordinates()) {
                Coordinate coord = new Coordinate(
                    coordinate.getProjection(),
                    coordinate.getLatitude(),
                    coordinate.getLongitude(),
                    cat
                );
                cat.getCoordinates().add(coord);
            }
            this.coordinateRepository.saveAll(cat.getCoordinates());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean intervals(Date start, Date base, Date end) {
        if (
            (base.compareTo(start) > 0 && base.compareTo(end) < 0) ||
                (base.compareTo(start) == 0 || base.compareTo(end) == 0)
        ) {
            return true;
        } else {
            return false;
        }
    }
}
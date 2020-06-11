package api.restful.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.awt.Point;
import java.awt.Polygon;
import java.text.SimpleDateFormat;

import api.restful.model.Catalog;
import api.restful.model.CatalogRepository;
import api.restful.model.Coordinate;
import api.restful.model.CoordinateRepository;
import api.restful.model.geojson.Request;

@Service("CatalogService")
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Override
    public List<Catalog> list() {
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
    public List<Catalog> search(Request request) {
        List<Catalog> catalogs = this.list();
        List<Catalog> result = new ArrayList<Catalog>();
        int[] x = {};
        int[] y = {};
        try {
            for(List<Integer> lonlat : request.getGeojson().getFeatures().get(0).getGeometry().getCoordinates().get(0)) {
                x = Arrays.copyOf(x, x.length + 1); x[x.length - 1] = lonlat.get(1);
                y = Arrays.copyOf(y, y.length + 1); y[y.length - 1] = lonlat.get(0);
            }
            Polygon polygon = new Polygon(x,y, x.length);
            for (Catalog cat : catalogs) {
                for (Coordinate coord : cat.getCoordinates()) {
                    int lat = (int) coord.getLatitude();
                    int lon = (int) coord.getLongitude();
                    Point point = new Point(lat,lon);
                    if (
                        polygon.contains(point) &&
                            this.intervals(
                                request.getDateTime().getStart(),
                                new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(cat.getDateTime()),
                                request.getDateTime().getEnd()
                            )
                    ) {
                        result.add(cat);
                        break;
                    }
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Catalog>();
        }
    }

    @Override
    public Catalog findById(Long id) {
        List<Catalog> catalogs = this.list();
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
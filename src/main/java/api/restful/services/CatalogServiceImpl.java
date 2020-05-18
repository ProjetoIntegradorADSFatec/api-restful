package api.restful.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.ArrayList;

import api.restful.model.Catalog;
import api.restful.model.CatalogRepository;
import api.restful.model.Coordinate;
import api.restful.model.CoordinateRepository;

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
                for (Coordinate coord : coordinates) {
                    if ( coord.getCatalog().getId().equals(catalog.getId())) {
                        cat.getCoordinates().add(
                            new Coordinate(
                                coord.getProjection(),
                                coord.getLatitude(),
                                coord.getLongitude(),
                                null
                            )
                        );
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
    public boolean remove(Catalog catalog) {
        try {
            this.catalogRepository.delete(new Catalog());
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
}
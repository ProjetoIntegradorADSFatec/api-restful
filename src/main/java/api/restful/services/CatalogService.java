package api.restful.services;

import java.util.Date;
import java.util.List;

import api.restful.model.collection.Item;
import api.restful.model.catalog.Catalog;
import api.restful.model.geojson.Geojson;

public interface CatalogService {
    List<Catalog> listCatalog();
    Item listItems();
    Item search(Geojson geojson);
    Catalog findById(Long id);
    boolean remove(Catalog catalog);
    boolean add(Catalog catalog);
    boolean intervals(Date start, Date base, Date end);
}
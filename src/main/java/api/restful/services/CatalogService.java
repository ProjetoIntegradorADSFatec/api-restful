package api.restful.services;

import java.util.List;
import api.restful.model.Catalog;
import api.restful.model.geojson.Request;

public interface CatalogService {
    List<Catalog> list();
    List<Catalog> search(Request request);
    boolean remove(Catalog catalog);
    boolean add(Catalog catalog);
}
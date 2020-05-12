package api.restful.services;

import java.util.List;
import api.restful.model.Catalog;

public interface CatalogService {
    List<Catalog> list();
    boolean remove(Catalog Catalog);
    boolean add(Catalog Catalog);
}
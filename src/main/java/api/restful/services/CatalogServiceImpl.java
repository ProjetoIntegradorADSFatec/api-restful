package api.restful.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

import api.restful.model.Catalog;
import api.restful.model.CatalogRepository;

@Service("CatalogService")
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogRepository repository;

    @Override
    public List<Catalog> list() {
        try {
            return (List<Catalog>) this.repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Catalog>();
        }
    }

    @Override
    public boolean remove(Catalog Catalog) {
        try {
            this.repository.delete(Catalog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Catalog Catalog) {
        try {
            this.repository.save(Catalog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
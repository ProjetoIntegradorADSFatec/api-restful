package api.restful.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

import api.restful.model.Cache;
import api.restful.model.CacheRepository;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {
    @Autowired
    private CacheRepository repository;

    @Override
    public List<Cache> list() {
        try {
            return (List<Cache>) this.repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Cache>();
        }
    }

    @Override
    public Cache findOneById(Long id) {
        try {
            List<Cache> result = (List<Cache>) this.repository.findOneById(id);
            return result.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return new Cache();
        }
    }

    @Override
    public boolean remove(Cache cache) {
        try {
            this.repository.delete(cache);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Cache cache) {
        try {
            this.repository.save(cache);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
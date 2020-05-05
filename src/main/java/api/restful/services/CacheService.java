package api.restful.services;

import java.util.List;
import api.restful.model.Cache;

public interface CacheService {
    List<Cache> list();
    Cache findOneById(Long codigo);
    boolean remove(Cache Cache);
    boolean add(Cache Cache);
}
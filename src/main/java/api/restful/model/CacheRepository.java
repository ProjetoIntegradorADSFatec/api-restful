package api.restful.model;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.restful.model.Cache;

@Repository
public interface CacheRepository extends CrudRepository<Cache, Long> {
    @Query("select c from Cache c where c.id = :id")
    public List<Cache> findOneById(@Param("id") Long id);
}
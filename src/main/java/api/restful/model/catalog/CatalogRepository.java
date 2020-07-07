package api.restful.model.catalog;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Long> { }
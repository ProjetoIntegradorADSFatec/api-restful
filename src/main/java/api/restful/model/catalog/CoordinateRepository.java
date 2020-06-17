package api.restful.model.catalog;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import api.restful.model.catalog.Coordinate;

@Repository
public interface CoordinateRepository extends CrudRepository<Coordinate, Long> { }
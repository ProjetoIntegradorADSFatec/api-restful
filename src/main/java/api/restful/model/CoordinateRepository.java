package api.restful.model;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import api.restful.model.Coordinate;

@Repository
public interface CoordinateRepository extends CrudRepository<Coordinate, Long> {}
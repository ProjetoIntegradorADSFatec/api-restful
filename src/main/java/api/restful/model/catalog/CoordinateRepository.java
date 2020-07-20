package api.restful.model.catalog;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CoordinateRepository extends CrudRepository<Coordinate, Long> {
    @Query(value = "select cat from Coordinate coord inner join coord.catalog cat order by cat.id")
    List<Catalog> listAll();

    @Query(value = "select cat from Coordinate coord inner join coord.catalog cat where cat.id = :id order by cat.id")
    List<Catalog> find(Long id);

    @Query(value = "select cat from Coordinate coord inner join coord.catalog cat where cat.name = :name and cat.band = :band order by cat.id")
    List<Catalog> findByNameBand(String name, String band);
}
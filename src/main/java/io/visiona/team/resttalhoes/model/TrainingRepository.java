@Repository
public interface TrainingRepository extends JpaRepository<BaseTraining, Long>{
    List<BaseTraining> findAll();
}
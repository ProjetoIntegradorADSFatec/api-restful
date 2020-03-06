/* @Entity
@Table(name = "basetraining")
public class Customer implements Serializable {
    private static final long serialVersionUID = -2343243243242432341L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fid;
    @Column(name = "")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;


} */
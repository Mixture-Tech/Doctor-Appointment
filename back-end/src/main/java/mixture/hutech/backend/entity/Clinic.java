//package mixture.hutech.backend.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.sql.Timestamp;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@RequiredArgsConstructor
//@Builder
//public class Clinic {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "address")
//    private String address;
//
//    @Column(name = "phone")
//    private String phone;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Timestamp createdAt;
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Timestamp updatedAt;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Timestamp deletedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = new Timestamp(System.currentTimeMillis());
//        updatedAt = new Timestamp(System.currentTimeMillis());
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = new Timestamp(System.currentTimeMillis());
//    }
//
//    @PreRemove
//    protected void onDelete() {
//        deletedAt = new Timestamp(System.currentTimeMillis());
//    }
//
//    @OneToMany(mappedBy = "clinic" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Appointment> appointments = new HashSet<>();
//}

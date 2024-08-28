package pl.milosz.employees_database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "last_medical_exam_date", nullable = false)
    private LocalDate lastMedicalExamDate;

    @Column(name = "bhp_exam_date", nullable = false)
    private LocalDate bhpExamDate;

    @Column(name = "ppk", nullable = false)
    private Boolean ppk;

    @ManyToOne
    @JoinColumn(name = "insurance_id", referencedColumnName = "id")
    private Insurance insurance;

    @ManyToOne
    @JoinColumn(name = "multisport_id", referencedColumnName = "id")
    private MultiSport multisport;

    @Column(name = "remarks")
    private String remarks;
}

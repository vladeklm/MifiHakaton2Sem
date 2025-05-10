package it.globus.finaudit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operation_types")
public class OperationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isActual;

    @Column(nullable = false, unique = true)
    private String code;

    @ToString.Exclude
    @OneToMany(mappedBy = "operationType")
    private List<Operation> operations = new ArrayList<>();

}

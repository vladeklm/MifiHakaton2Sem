package it.globus.finaudit.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "operation_log_history")
public class OperationLogHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_id", nullable = false)
    private Long operationId;

    @Column(nullable = false, columnDefinition = "JSON")
    private String operationSnapshot;

    @Column(name = "modified_by_id", nullable = false)
    private Long modifiedById;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    public enum Action {
        CREATED, UPDATED, DELETED
    }

    @PrePersist
    protected void onCreate() {
        modifiedAt = LocalDateTime.now();
    }
}

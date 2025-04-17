package it.globus.finaudit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_category_id", nullable = false)
    private OperationCategory operationCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationType operationType;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_type_id", nullable = false)
    private ClientType clientType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_status_id", nullable = false)
    private OperationStatus operationStatus;

    @Column(nullable = false)
    private LocalDateTime dateTimeOperation;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "bank_from_id")
    private Long bankFromId;

    @Column(name = "bank_to_id")
    private Long bankToId;

    @Column(name = "bank_recipient_account_id")
    private Long bankRecipientAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Pattern(regexp = "^(8|\\+7)\\d{10}$",
            message = "Телефон должен начинаться с 8 или +7 и содержать 11 цифр (например: 89991234567 или +79991234567)")
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "\\d{11}", message = "ИНН должен содержать только 11 цифр")
    private String inn;

    @Column(length = 500)
    @Size(max = 500, message = "Комментарий не должен превышать 500 символов")
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
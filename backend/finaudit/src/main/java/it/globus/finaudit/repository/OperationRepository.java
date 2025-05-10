package it.globus.finaudit.repository;

import it.globus.finaudit.DTO.AccountInfoDTO;
import it.globus.finaudit.entity.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> , JpaSpecificationExecutor<Operation> {

    @EntityGraph("Operation.withAssociations")
    List<Operation> findAll(Specification<Operation> spec);

    Page<Operation> findAllByClient_User_Id(Long userId, Pageable pageable);

    @Query("SELECT new it.globus.finaudit.DTO.AccountInfoDTO(ba.number, ba.bank.name, COUNT(o.id)) " +
            "FROM Operation o " +
            "LEFT JOIN BankAccount ba ON (:isContragentAccount = true AND ba.id = o.bankRecipientAccount.id) OR " +
            "(:isContragentAccount = false AND ba.id = o.bankAccount.id) " +
            "WHERE o.client.id = :clientId " +
            "GROUP BY ba.number, ba.bank.name " +
            "ORDER BY COUNT(o.id) DESC")
    List<AccountInfoDTO> findAccountsByClientAndType(@Param("clientId") Long clientId,
                                                     @Param("isContragentAccount") boolean isContragentAccount);
}

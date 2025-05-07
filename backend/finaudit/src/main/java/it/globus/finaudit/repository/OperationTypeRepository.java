package it.globus.finaudit.repository;

import it.globus.finaudit.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
    OperationType findByName(String name);
}
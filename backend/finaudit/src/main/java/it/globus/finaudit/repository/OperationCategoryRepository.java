package it.globus.finaudit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.globus.finaudit.entity.OperationCategory;

@Repository
public interface OperationCategoryRepository extends JpaRepository<OperationCategory, Long> {
}
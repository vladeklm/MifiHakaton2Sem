package it.globus.finaudit.repository;

import it.globus.finaudit.entity.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonTypeRepository extends JpaRepository<PersonType, Long> {
}

package it.globus.finaudit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.globus.finaudit.entity.ClientType;

@Repository
public interface ClientTypeRepository extends JpaRepository<ClientType, Long> {
}

package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.TransactionDTO;
import it.globus.finaudit.entity.Transaction;
import it.globus.finaudit.mapper.TransactionMapper;
import it.globus.finaudit.repository.TransactionRepository;
import it.globus.finaudit.specification.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    public List<TransactionDTO> getFilteredTransactions(
            String type,
            String status,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        Specification<Transaction> spec = Specification
                .where(TransactionSpecification.hasType(type))
                .and(TransactionSpecification.hasStatus(status))
                .and(TransactionSpecification.dateBetween(startDate, endDate));

        return repository.findAll(spec).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        Transaction entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }
}

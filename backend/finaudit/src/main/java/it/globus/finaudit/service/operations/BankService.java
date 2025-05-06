package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.BankDTO;
import it.globus.finaudit.entity.Bank;
import it.globus.finaudit.mapper.BankMapper;
import it.globus.finaudit.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository repository;
    private final BankMapper mapper;

    public List<BankDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public BankDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Банк не найден")));
    }

    @Transactional
    public BankDTO create(BankDTO dto) {
        Bank bank = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(bank));
    }

    @Transactional
    public BankDTO update(Long id, BankDTO dto) {
        Bank bank = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Банк не найден"));
        mapper.updateEntity(dto, bank);
        return mapper.toDTO(repository.save(bank));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

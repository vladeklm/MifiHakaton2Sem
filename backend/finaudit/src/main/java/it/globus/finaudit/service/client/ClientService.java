package it.globus.finaudit.service.client;


import it.globus.finaudit.DTO.RegisterDTO;
import it.globus.finaudit.entity.Client;
import it.globus.finaudit.entity.User;
import it.globus.finaudit.mapper.ClientMapper;
import it.globus.finaudit.repository.ClientRepository;
import it.globus.finaudit.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserService userService;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, UserService userService, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.userService = userService;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public void registerClient(RegisterDTO registerDTO) {
        User user = userService.registerUser(registerDTO);
        Client client = clientMapper.toClient(registerDTO);
        client.setUser(user);
        clientRepository.save(client);
    }
}

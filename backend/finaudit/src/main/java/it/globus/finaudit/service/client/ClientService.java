package it.globus.finaudit.service.client;


import it.globus.finaudit.DTO.AuthenticationDTO;
import it.globus.finaudit.entity.Client;
import it.globus.finaudit.entity.User;
import it.globus.finaudit.repository.ClientRepository;
import it.globus.finaudit.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserService userService;

    public ClientService(ClientRepository clientRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.userService = userService;
    }

    @Transactional
    public void registerClient(AuthenticationDTO authenticationDTO){
        User user = userService.registerUser(authenticationDTO);
        Client client =new Client();
        client.setUser(user);
        clientRepository.save(client);
    }

}

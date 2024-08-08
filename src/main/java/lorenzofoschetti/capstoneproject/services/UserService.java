package lorenzofoschetti.capstoneproject.services;

import jakarta.transaction.Transactional;
import lorenzofoschetti.capstoneproject.entities.Order;
import lorenzofoschetti.capstoneproject.entities.User;
import lorenzofoschetti.capstoneproject.exceptions.BadRequestException;
import lorenzofoschetti.capstoneproject.exceptions.NotFoundException;
import lorenzofoschetti.capstoneproject.payloads.NewUserPayload;
import lorenzofoschetti.capstoneproject.repositories.OrderRepository;
import lorenzofoschetti.capstoneproject.repositories.UserRepository;
import lorenzofoschetti.capstoneproject.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private MailgunSender mailgunSender;


    public Page<User> getUsers(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

    @Transactional
    public User save(NewUserPayload body) {
        // 1. Verifico se l'email è già in uso
        this.userRepository.findByEmail(body.email()).ifPresent(
                // 1.1 Se lo è triggero un errore
                user -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );

        // 2. Altrimenti creiamo un nuovo oggetto User e oltre a prendere i valori dal body, aggiungiamo l'avatarURL (ed eventuali altri campi server-generated)
        User newUser = new User(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()));

        Order cart = new Order();
        cart.setUser(newUser);


        newUser.setOrder(cart);

        orderRepository.save(cart);

//        return userRepository.save(user);
//        // 3. Poi salviamo lo user
//        User saved = userRepository.save(newUser);
//        mailgunSender.sendRegistrationEmail(saved);

        return userRepository.save(newUser);
    }

    public User findById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(UUID userId, NewUserPayload modifiedUser) {
        User found = this.findById(userId);
        found.setName(modifiedUser.name());
        found.setSurname(modifiedUser.surname());
        found.setEmail(modifiedUser.email());
        found.setPassword(bcrypt.encode(modifiedUser.password()));

        return this.userRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        this.userRepository.delete(found);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
}

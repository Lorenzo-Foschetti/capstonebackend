package lorenzofoschetti.capstoneproject.services;

import lorenzofoschetti.capstoneproject.entities.Order;
import lorenzofoschetti.capstoneproject.exceptions.NotFoundException;
import lorenzofoschetti.capstoneproject.repositories.BottleRepository;
import lorenzofoschetti.capstoneproject.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BottleRepository bottleRepository;

    public void addBottle(UUID orderId, UUID bottleId) {
        Order cart = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));
        cart.getBottles().add(bottleRepository.findById(bottleId).orElseThrow(() -> new NotFoundException(bottleId)));
        orderRepository.save(cart);
    }

    public void removeBottle(UUID cartId, UUID bottleId) {
        Order cart = orderRepository.findById(cartId).orElseThrow(() -> new NotFoundException(cartId));
        cart.getBottles().remove(bottleRepository.findById(bottleId).orElseThrow(() -> new NotFoundException(bottleId)));
        orderRepository.save(cart);
    }

    public Optional<Order> findCartByUserId(UUID userId) {
        return orderRepository.findByUserId(userId);
    }
}

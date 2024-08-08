package lorenzofoschetti.capstoneproject.controllers;

import lorenzofoschetti.capstoneproject.entities.Order;
import lorenzofoschetti.capstoneproject.repositories.OrderRepository;
import lorenzofoschetti.capstoneproject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/add/{bottleId}/{orderId}")
    public void addBottle(@PathVariable UUID orderId, @PathVariable UUID bottleId) {
        orderService.addBottle(orderId, bottleId);

    }

    @PostMapping("/remove/{bottleId}/{cartId}")
    public void deleteBottleFromCart(@PathVariable UUID cartId, @PathVariable UUID bottleId) {


        orderService.removeBottle(cartId, bottleId);

    }

    @GetMapping("/user/{userId}")
    public Optional<Order> findCartByUserId(@PathVariable UUID userId) {

        return orderService.findCartByUserId(userId);
    }
}

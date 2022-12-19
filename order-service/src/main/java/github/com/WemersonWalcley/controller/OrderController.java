package github.com.WemersonWalcley.controller;

import github.com.WemersonWalcley.DTO.OrderCreatedEventDTO;
import github.com.WemersonWalcley.entity.Order;
import github.com.WemersonWalcley.repository.OrderRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/v1/orders")
public class OrderController {

    @Autowired
    private OrderRepository orders;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping
    public Order create(@RequestBody Order order) {
        orders.save(order);
        OrderCreatedEventDTO event = new OrderCreatedEventDTO(order.getId(), order.getValue());
        rabbitTemplate.convertAndSend("orders.v1.order-created.generate-cashback", event);
        rabbitTemplate.convertAndSend("orders.v1.order-created.send-notification", event);
        return order;
    }

    @GetMapping
    public Collection<Order> list() {
        return orders.findAll();
    }

    @GetMapping("{id}")
    public Order findById(@PathVariable Long id) {
        return orders.findById(id).orElseThrow();
    }

    @PutMapping("{id}/pay")
    public Order pay(@PathVariable Long id) {
        Order order = orders.findById(id).orElseThrow();
        order.markAsPaid();
        return orders.save(order);
    }
}

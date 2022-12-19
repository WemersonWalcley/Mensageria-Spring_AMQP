package github.com.WemersonWalcley;

import github.com.WemersonWalcley.DTO.OrderCreatedEventDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    @RabbitListener(queues = "orders.v1.order-created")
    public void onOrderCreated(OrderCreatedEventDTO event){
        System.out.println("------------------------------------");
        System.out.println("Message ID: " + event.getId());
        System.out.println("Message value : " + event.getValue());
    }
}

package github.com.WemersonWalcley.repository;

import github.com.WemersonWalcley.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

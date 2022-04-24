package showcase;

import order.Order;

import java.util.Set;

public interface ShowOrders {

    void showOrder(Order order);

    void showOrders(Set<Order> orders);
}

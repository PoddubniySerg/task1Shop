package order;

import product.Product;

import java.time.LocalDateTime;
import java.util.Map;

public class OrderImpl implements Order, Comparable<OrderImpl> {

    private static int count = 1;

    private final Map<Product, Integer> productMap;

    private final int id;
    private final LocalDateTime localDateTime;

    public OrderImpl(Map<Product, Integer> productMap) {
        this.productMap = productMap;
        this.id = count;
        this.localDateTime = LocalDateTime.now();
        count++;
    }

    @Override
    public String toString() {
        return String.format("Заказ №%03d создан: %s", this.id, this.localDateTime);
    }

    @Override
    public int hashCode() {
        return this.id + this.localDateTime.hashCode() + this.productMap.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !obj.getClass().equals(this.getClass())) return false;
        OrderImpl order = (OrderImpl) obj;
        return this.id == order.id && this.localDateTime.equals(order.localDateTime);
    }

    @Override
    public int compareTo(OrderImpl order) {
        if (this.id != order.id) return this.id - order.id;
        return this.localDateTime.compareTo(order.localDateTime);
    }

    @Override
    public Map<Product, Integer> getProducts() {
        return this.productMap;
    }
}
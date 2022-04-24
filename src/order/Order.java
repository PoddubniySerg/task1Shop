package order;

import product.Product;

import java.util.Map;

public interface Order {
    Map<Product, Integer> getProducts();
}

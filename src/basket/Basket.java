package basket;

import product.Product;

import java.util.Map;
import java.util.Set;

public interface Basket {

    Map<Product, Integer> getOrder();

    Set<Product> getProductsInBasket();

    void addToBasket(Product product, Integer quantity);

    void removeFromBasket(Product product);
}
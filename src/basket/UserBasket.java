package basket;

import product.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserBasket implements Basket {
    private final Map<Product, Integer> basket;

    public UserBasket() {
        basket = new HashMap<>();
    }

    @Override
    public Map<Product, Integer> getOrder() {
        return this.basket;
    }

    @Override
    public Set<Product> getProductsInBasket() {
        return this.basket.keySet();
    }

    @Override
    public void addToBasket(Product product, Integer quantity) {
        if (product == null || quantity == null || quantity < 1) return;
        this.basket.put(product, quantity);
    }

    @Override
    public void removeFromBasket(Product product) {
        if (product == null || !this.basket.containsKey(product)) return;
        this.basket.remove(product);
    }
}
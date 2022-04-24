package storage;

import product.Product;

import java.util.Set;

public interface Storage {

    Set<Product> getAllProducts();

    Set<Product> getAvailableProducts();
}

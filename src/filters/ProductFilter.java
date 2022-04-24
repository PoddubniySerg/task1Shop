package filters;

import product.Product;

import java.math.BigDecimal;
import java.util.Set;

public interface ProductFilter {

    Set<Product> filterByKeyWords(String[] keyWords, Set<Product> productStream);

    Set<Product> filterByManufacturer(String manufacturer, Set<Product> productStream);

    Set<Product> filterByPrice(BigDecimal priceMin, BigDecimal priceMax, Set<Product> productStream);

    Set<Product> filterByRating(int rateMin, Set<Product> productStream);
}
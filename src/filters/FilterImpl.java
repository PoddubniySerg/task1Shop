package filters;

import product.Product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterImpl implements ProductFilter {

    @Override
    public Set<Product> filterByKeyWords(String[] keyWords, Set<Product> products) {
        if (keyWords.length > 0) {
            products = products.stream()
                    .filter(product -> {
                        for (String keyWord : keyWords) {
                            if (product.getName().toUpperCase().contains(keyWord.toUpperCase())) return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toSet());
        }
        return products;
    }

    @Override
    public Set<Product> filterByManufacturer(String manufacturer, Set<Product> products) {
        if (manufacturer == null || manufacturer.equals("")) return products;
        return products.stream()
                .filter(product -> product.getManufacturer().getName().toUpperCase().contains(manufacturer.toUpperCase()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Product> filterByPrice(BigDecimal priceMin, BigDecimal priceMax, Set<Product> products) {
        if (priceMin == null
                || priceMax == null
                || priceMin.compareTo(BigDecimal.valueOf(0)) < 0
                || priceMax.compareTo(BigDecimal.valueOf(0)) < 0
                || priceMin.compareTo(priceMax) > 0) {
            return products;
        }
        return products.stream()
                .filter(product
                        -> product.getPrice().compareTo(priceMin) >= 0 && product.getPrice().compareTo(priceMax) <= 0)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Product> filterByRating(int rateMin, Set<Product> products) {
        if (rateMin <= 0) return products;
        return products.stream().filter(product -> product.getRate() >= rateMin).collect(Collectors.toSet());
    }
}

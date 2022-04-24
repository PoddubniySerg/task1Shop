package storage;

import enums.MeasureUnit;
import manufacturer.Manufacturer;
import product.Food;
import product.Product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultStorage implements Storage {

    private static DefaultStorage defaultStorage;
    private final Set<Product> products;

    private DefaultStorage() {
        this.products = new HashSet<>();
        int count = this.addManufacturer1(1, new Manufacturer("Московский комбинат"));
        count = this.addManufacturer2(count, new Manufacturer("Lavazza"));
        count = this.addManufacturer3(count, new Manufacturer("Простоквашино"));
        this.addManufacturer4(count, new Manufacturer("Мелькрукк"));
        defaultStorage = this;
    }

    public static Storage getStorage() {
        if (defaultStorage == null) return new DefaultStorage();
        return defaultStorage;
    }

    @Override
    public Set<Product> getAllProducts() {
        return this.products;
    }

    @Override
    public Set<Product> getAvailableProducts() {
        return this.products.stream().filter(product -> product.getQuantityProduct() > 0).collect(Collectors.toSet());
    }

    private int addManufacturer1(int count, Manufacturer manufacturer) {
        Set<Product> productSet = new HashSet<>();
        productSet.add(new Food(
                String.format("%03d", count++),
                "Хлеб белый",
                MeasureUnit.PIECES,
                manufacturer,
                10,
                BigDecimal.valueOf(48.00)));
        productSet.add(new Food(
                String.format("%03d", count++),
                "Хлеб заварной",
                MeasureUnit.PIECES,
                manufacturer,
                4,
                BigDecimal.valueOf(40.00)));
        productSet.add(new Food(
                String.format("%03d", count++),
                "Хлеб бородинский",
                MeasureUnit.PIECES,
                manufacturer,
                10,
                BigDecimal.valueOf(30.00)));
        this.products.addAll(productSet);
        return count;
    }

    private int addManufacturer2(int count, Manufacturer manufacturer) {
        Set<Product> productSet = new HashSet<>();
        productSet.add(new Food(
                String.format("%03d", count++),
                "Кофе зерновой 250г",
                MeasureUnit.PACKAGES,
                manufacturer,
                10,
                BigDecimal.valueOf(299.00)));
        productSet.add(new Food(
                String.format("%03d", count++),
                "Кофе зерновой 1000г",
                MeasureUnit.PACKAGES,
                manufacturer,
                10,
                BigDecimal.valueOf(1187.00)));
        productSet.add(new Food(
                String.format("%03d", count++),
                "Кофе в капсулах",
                MeasureUnit.PACKAGES,
                manufacturer,
                10,
                BigDecimal.valueOf(1450.00)));
        this.products.addAll(productSet);
        return count;
    }

    private int addManufacturer3(int count, Manufacturer manufacturer) {
        Set<Product> productSet = new HashSet<>();
        productSet.add(new Food(
                String.format("%03d", count++),
                "Молоко 1л",
                MeasureUnit.PACKAGES,
                manufacturer,
                10,
                BigDecimal.valueOf(89.00)));
        productSet.add(new Food(
                String.format("%03d", count++),
                "Творог 180г",
                MeasureUnit.PACKAGES,
                manufacturer,
                10,
                BigDecimal.valueOf(149.00)));
        productSet.add(new Food(
                String.format("%03d", count++),
                "Сметана 20% 180г",
                MeasureUnit.PACKAGES,
                manufacturer,
                10,
                BigDecimal.valueOf(99.00)));
        this.products.addAll(productSet);
        return count;
    }

    private void addManufacturer4(int count, Manufacturer manufacturer) {
        Set<Product> productSet = new HashSet<>();
        productSet.add(new Food(
                String.format("%03d", count++),
                "Сахар",
                MeasureUnit.KILOGRAMMS,
                manufacturer,
                10,
                BigDecimal.valueOf(89.00)));
        productSet.add(new Food(
                String.format("%03d", count++),
                "Мука 2кг",
                MeasureUnit.PACKAGES,
                manufacturer,
                10,
                BigDecimal.valueOf(64.00)));
        productSet.add(new Food(
                String.format("%03d", count),
                "Крупа гречневая",
                MeasureUnit.PACKAGES,
                manufacturer,
                10,
                BigDecimal.valueOf(99.00)));
        this.products.addAll(productSet);
    }
}
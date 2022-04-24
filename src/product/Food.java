package product;

import enums.MeasureUnit;
import manufacturer.Manufacturer;

import java.math.BigDecimal;

public class Food implements Product, Comparable<Product> {

    private final String id;
    private final String name;
    private final MeasureUnit measureUnit;
    private final Manufacturer manufacturer;
    private final int[] rate;
    private int quantity;
    private final BigDecimal price;


    public Food(String id, String name, MeasureUnit measureUnit, Manufacturer manufacturer, int quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.measureUnit = measureUnit;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
        this.rate = new int[5];
    }

    @Override
    public int getQuantityProduct() {
        return this.quantity;
    }

    @Override
    public MeasureUnit getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public void setQuantityProduct(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void setRate(int rate) {
        this.rate[rate - 1] = this.rate[rate - 1] + 1;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Manufacturer getManufacturer() {
        return this.manufacturer;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public double getRate() {
        int countRates = 0;
        int sumRates = 0;
        for (int i = 0; i < this.rate.length; i++) {
            sumRates = sumRates + this.rate[i] * (i + 1);
            countRates = countRates + this.rate[i];
        }
        return (double) sumRates / countRates;
    }

    @Override
    public String getProductCode() {
        return this.id;
    }

    @Override
    public int compareTo(Product product) {
        return this.name.compareTo(product.getName());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() + this.name.hashCode() + this.manufacturer.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !obj.getClass().equals(this.getClass())) return false;
        Food food = (Food) obj;
        return this.name.equals(food.name) && this.id.equals(food.id) && this.manufacturer.equals(food.manufacturer);
    }
}
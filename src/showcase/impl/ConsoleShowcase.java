package showcase.impl;

import product.Product;
import showcase.Showcase;

import java.util.*;

public class ConsoleShowcase implements Showcase {

    private void print(String msg, Set<Product> productsSet) {
        System.out.println(msg);
        Set<Product> products = new TreeSet<>(productsSet);
        System.out.println("Наименование товара   |\tКод товара   |\t    Производитель   |\tРейтинг   |\tЕдиница продажи   |\tДоступно   |\tЦена за ед.");
        for (Product product : products) {
            System.out.printf("%20s", product.getName());
            System.out.printf("%12s", product.getProductCode());
            System.out.printf("%26s", product.getManufacturer().getName());
            System.out.printf("%12.2f", product.getRate());
            System.out.printf("%18s", product.getMeasureUnit());
            System.out.printf("%16d", product.getQuantityProduct());
            System.out.printf("%18s", product.getPrice());
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void showProducts(Set<Product> productSet) {
        String msg = "Список доступных к покупке товаров:";
        this.print(msg, productSet);
    }
}
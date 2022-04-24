package showcase.impl;

import basket.Basket;
import product.Product;
import showcase.ShowBasket;

import java.math.BigDecimal;

public class ShowBasketImpl implements ShowBasket {

    @Override
    public void showBasket(Basket basket) {
        System.out.println("Наименование товара   |\t" +
                "Код товара   |\t" +
                "Единица продажи   |\t" +
                "Количество   |\t" +
                "Цена за ед.   |\t" +
                "Сумма");
        BigDecimal sum = BigDecimal.valueOf(0);
        if (!basket.getProductsInBasket().isEmpty()) {
            for (Product product : basket.getOrder().keySet()) {
                sum = sum.add(product.getPrice().multiply(BigDecimal.valueOf(basket.getOrder().get(product))));
                System.out.printf("%20s", product.getName());
                System.out.printf("%12s", product.getProductCode());
                System.out.printf("%18s", product.getMeasureUnit());
                System.out.printf("%16d", basket.getOrder().get(product));
                System.out.printf("%18s", product.getPrice());
                System.out.printf("%18s", product.getPrice().multiply(BigDecimal.valueOf(basket.getOrder().get(product))));
                System.out.println();
            }
        }
        System.out.println("Итого: " + sum + " руб.\n\n");
    }
}

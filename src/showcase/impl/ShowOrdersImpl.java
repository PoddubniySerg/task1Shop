package showcase.impl;

import order.Order;
import product.Product;
import showcase.ShowOrders;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

public class ShowOrdersImpl implements ShowOrders {

    @Override
    public void showOrder(Order order) {
        System.out.println("Наименование товара   |\t" +
                "Код товара   |\t" +
                "Единица продажи   |\t" +
                "Количество   |\t" +
                "Цена за ед.   |\t" +
                "Сумма");
        BigDecimal sum = BigDecimal.valueOf(0);
        if (!order.getProducts().isEmpty()) {
            for (Product product : order.getProducts().keySet()) {
                sum = sum.add(product.getPrice().multiply(BigDecimal.valueOf(order.getProducts().get(product))));
                System.out.printf("%20s", product.getName());
                System.out.printf("%12s", product.getProductCode());
                System.out.printf("%18s", product.getMeasureUnit());
                System.out.printf("%16d", order.getProducts().get(product));
                System.out.printf("%18s", product.getPrice());
                System.out.printf("%18s",
                        product.getPrice().multiply(BigDecimal.valueOf(order.getProducts().get(product))));
                System.out.println();
            }
        }
        System.out.println("Итого: " + sum + " руб.\n\n");
    }

    @Override
    public void showOrders(Set<Order> orders) {
        System.out.println("Список созданных заказов:");
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println("\nНажмите enter для возвращения к покупкам");
        new Scanner(System.in).nextLine();
    }
}

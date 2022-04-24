# **Пример применения принципов хорошего кода в программе**

## **Избегание магических чисел:**
Класс ConsoleShowcase, строки 35-48

```java
public class ConsoleShowcase implements Showcase {
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
}
```

## **DRY:**
 Класс Main, строки 239-250. Повторяющаяся операция парсинга данных от пользователя вынесена в отдельный метод parseIntFromInput():
```java
public class Main {
    
    public static int parseIntFromInput(String msg, Scanner scanner) {
        int value;
        while (true) {
            System.out.print(msg);
            try {
                value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException exception) {
                System.out.println("Введено некорректное значение");
            }
        }
    }
}
```

## **SOLID**

***Single Responsibility Principle***

Функции фильтрации товаров и вывода списка товаров разделены между интерфейсами Showcase и ProductFilter

```java
public interface Showcase {
    void showProducts(Set<Product> productSet);

    void showBasket(Basket basket);

    void showOrder(Order order);

    void showOrders(Set<Order> orders);
}



public interface ProductFilter {
    Set<Product> filterByKeyWords(String[] keyWords, Set<Product> productStream);

    Set<Product> filterByManufacturer(String manufacturer, Set<Product> productStream);

    Set<Product> filterByPrice(BigDecimal priceMin, BigDecimal priceMax, Set<Product> productStream);

    Set<Product> filterByRating(int rateMin, Set<Product> productStream);
}

```
***Open-Closed Principle***

Использование интерфейса Product вместо класса Food в коде позволит расширить ассортимент товаров, кроме класса Food, при необходимости. В классе DefaultStorage в строках 42-147 составляется множество элементов типа Product путем добавления новых объектов типа Food.
```java
public interface Product{
    ....
}


public class Food implements Product, Comparable<Product> {
    ...
}

public class DefaultStorage implements Storage {

    private static DefaultStorage defaultStorage;
    private final Set<Product> products;

    private int addManufacturer1(int count, Manufacturer manufacturer) {
        Set<Product> productSet = new HashSet<>();
        productSet.add(new Food(
                String.format("%03d", count++),
                "Хлеб белый",
                MeasureUnit.PIECES,
                manufacturer,
                10,
                BigDecimal.valueOf(48.00)));
                .........
                .........
    }
}
```
***Interface Segregation Principle***

Функции вывода в консоль разных сущностей разделены между интерфейсами:

```java
public interface ShowBasket {

    void showBasket(Basket basket);
}



public interface Showcase {
    void showProducts(Set<Product> productSet);
}



public interface ShowOrders {

    void showOrder(Order order);

    void showOrders(Set<Order> orders);
}

```

***Dependency Inversion Principle***

Использование в строке 192 класса Main интерфейса ShowBasket не зависит от конкретной реализации метода void showBasket(Basket basket):
```java
public interface ShowBasket {

    void showBasket(Basket basket);
}


class Main {

    public static Order showBasket(Basket userBasket, Scanner scanner) {
        ......
        showBasket.showBasket(userBasket);
        ......
    }
}
```
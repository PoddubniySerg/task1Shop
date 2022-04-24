import basket.Basket;
import basket.UserBasket;
import filters.FilterImpl;
import order.Order;
import order.OrderImpl;
import product.Product;
import showcase.impl.ConsoleShowcase;
import filters.ProductFilter;
import showcase.ShowBasket;
import showcase.ShowOrders;
import showcase.Showcase;
import showcase.impl.ShowBasketImpl;
import showcase.impl.ShowOrdersImpl;
import storage.DefaultStorage;
import storage.Storage;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Storage storage = DefaultStorage.getStorage();
        Showcase showcase = new ConsoleShowcase();
        start(showcase, storage.getAvailableProducts(), storage);
        userDialog(showcase, storage);
    }

    public static void start(Showcase showcase, Set<Product> productSet, Storage storage) {
        showcase.showProducts(productSet);
        System.out.println(
                "Выберите действие:\n"
                        + "1. Применить фильтр\n"
                        + "2. Добавить товар в корзину\n"
                        + "3. Оценить товар\n"
                        + "4. Перейти в корзину\n"
                        + "5. Список заказов\n" +
                        (productSet.size() == storage.getAvailableProducts().size() ? "" : "6. Сбросить фильтр\n")
                        + "exit - выйти из программы");
    }

    public static void userDialog(Showcase showcase, Storage storage) {
        ShowOrders showOrders = new ShowOrdersImpl();
        Basket userBasket = new UserBasket();
        Set<Order> orders = new HashSet<>();
        Set<Product> productSet = storage.getAvailableProducts();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            switch (scanner.nextLine()) {
                case "1":
                    productSet = useFilters(showcase, scanner, storage);
                    break;
                case "2":
                    System.out.println(
                            addToBasket(storage.getAvailableProducts(), userBasket, scanner) ?
                                    "Товар добавлен в корзину\n" : "Товар не добавлен в корзину\n");
                    break;
                case "3":
                    rateTheProduct(scanner, storage);
                    break;
                case "4":
                    Order order = showBasket(userBasket, scanner);
                    if (order != null) {
                        orders.add(order);
                        userBasket = new UserBasket();
                    }
                    break;
                case "5":
                    showOrders.showOrders(orders);
                    break;
                case "6":
                    productSet = storage.getAvailableProducts();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Введена некорректная операция");
                    break;
            }
            productSet = productSet.stream()
                    .filter(product -> product.getQuantityProduct() > 0)
                    .collect(Collectors.toSet());
            start(showcase, productSet, storage);
        }
    }

    public static Set<Product> useFilters(Showcase showcase, Scanner scanner, Storage storage) {
        ProductFilter filter = new FilterImpl();
        Set<Product> products = storage.getAvailableProducts();
        while (true) {
            System.out.println("Выберите фильтр:\n" +
                    "1. По ключевым словам\n" +
                    "2. По производителю\n" +
                    "3. По цене\n" +
                    "4. По рейтингу\n" +
                    "5. Отменить");
            switch (scanner.nextLine()) {
                case "1":
                    System.out.print("Введите ключевые слова через пробел: ");
                    products = filter.filterByKeyWords(scanner.nextLine().split(" "), products);
                    break;
                case "2":
                    System.out.print("Введите название производителя: ");
                    products = filter.filterByManufacturer(scanner.nextLine(), products);
                    break;
                case "3":
                    products = filterByPrice(products, filter, scanner);
                    break;
                case "4":
                    String msg = "Введите число от 1 до 5 включительно для выбора минимального рейтинга: ";
                    products = filter.filterByRating(parseIntFromInput(msg, scanner), products);
                    break;
                case "5":
                    return products;
                default:
                    System.out.println("Неизвестное действие. Попробуйте снова.");
                    break;
            }
            if (returnToUpperMenu(products, showcase, scanner)) {
                return products;
            }
        }
    }

    public static Set<Product> filterByPrice(Set<Product> products, ProductFilter showcase, Scanner scanner) {
        String msgMinPrice = "Введите минимальную цену в виде целого числа: ";
        String msgMaxPrice = "Введите максимальную цену в виде целого числа: ";
        BigDecimal priceMin = BigDecimal.valueOf(parseIntFromInput(msgMinPrice, scanner));
        if (priceMin.compareTo(BigDecimal.valueOf(0)) >= 0) {
            BigDecimal priceMax = BigDecimal.valueOf(parseIntFromInput(msgMaxPrice, scanner));
            products = showcase.filterByPrice(priceMin, priceMax, products);
        }
        return products;
    }

    public static boolean returnToUpperMenu(Set<Product> products, Showcase showcase, Scanner scanner) {
        showcase.showProducts(products);
        while (true) {
            System.out.println("Выберите действие:\n" +
                    "1. Добавить фильтр\n" +
                    "2. Вернуться в предыдущее меню");
            switch (scanner.nextLine()) {
                case "1":
                    return false;
                case "2":
                    return true;
                default:
                    System.out.println("Неизвестная команда");
            }
        }
    }

    public static void rateTheProduct(Scanner scanner, Storage storage) {
        Product product = getProductById(scanner, storage.getAllProducts());
        if (product != null) {
            String msg = "Введите число от 1 до 5 включительно, для оценки: ";
            int rate = parseIntFromInput(msg, scanner);
            if (rate < 1 || rate > 5) {
                System.out.println("Такой оценки нет в рейтинге");
                return;
            }
            product.setRate(rate);
        }
    }

    public static Order showBasket(Basket userBasket, Scanner scanner) {
        ShowBasket showBasket = new ShowBasketImpl();
        showBasket.showBasket(userBasket);
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Оформить заказ\n" +
                    "2. Изменить количество товара\n" +
                    "3. Удалить товар из корзины\n" +
                    "4. Вернуться в предыдущее меню");
            switch (scanner.nextLine()) {
                case "1":
                    if (userBasket.getProductsInBasket().isEmpty()) {
                        System.out.println("Пустой заказ не может быть создан");
                        break;
                    }
                    return newOrder(userBasket);
                case "2":
                    addToBasket(userBasket.getProductsInBasket(), userBasket, scanner);
                    showBasket.showBasket(userBasket);
                    break;
                case "3":
                    Product product = getProductById(scanner, userBasket.getProductsInBasket());
                    userBasket.removeFromBasket(product);
                    showBasket.showBasket(userBasket);
                    break;
                case "4":
                    return null;
                default:
                    System.out.println("Операция неизвестна. Попробуйте снова.");
            }
        }
    }

    public static Order newOrder(Basket userBasket) {
        for (Product product : userBasket.getProductsInBasket()) {
            product.setQuantityProduct(product.getQuantityProduct() - userBasket.getOrder().get(product));
        }
        return new OrderImpl(userBasket.getOrder());
    }

    public static boolean addToBasket(Set<Product> productSet, Basket userBasket, Scanner scanner) {
        Product product = getProductById(scanner, productSet);
        if (product == null) return false;
        String msg = "Введите количество: ";
        int quantity = parseIntFromInput(msg, scanner);
        if (quantity > product.getQuantityProduct()) {
            System.out.println("Нужного количества нет на складе\n");
            return false;
        }
        if (quantity <= 0) {
            if (userBasket.getProductsInBasket().contains(product)) {
                userBasket.removeFromBasket(product);
            }
        } else {
            userBasket.addToBasket(product, quantity);
        }
        return true;
    }

    public static Product getProductById(Scanner scanner, Set<Product> productSet) {
        System.out.print("Введите код товара: ");
        String input = scanner.nextLine();
        for (Product product : productSet) {
            if (product.getProductCode().equals(input)) {
                return product;
            }
        }
        return null;
    }

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
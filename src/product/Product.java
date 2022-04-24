package product;

import enums.MeasureUnit;
import manufacturer.Manufacturer;

import java.math.BigDecimal;

public interface Product {
    int getQuantityProduct();//получить количество

    MeasureUnit getMeasureUnit();//единица измерения

    void setQuantityProduct(int quantity);//изменить доступное количество

    String getName();//получить наименование

    Manufacturer getManufacturer();//производитель

    BigDecimal getPrice();//стоимость единицы товара

    double getRate();//рейтинг

    String getProductCode();//код товара

    void setRate(int rate);
}
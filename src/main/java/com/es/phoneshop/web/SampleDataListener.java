package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

public class SampleDataListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        getSampleProducts().forEach(productDao::save);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private List<Product> getSampleProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        LinkedList<PriceHistory> priceHistories = new LinkedList<>();
        priceHistories.add(new PriceHistory("1 Set 2018", new BigDecimal(100)));
        priceHistories.add(new PriceHistory("10 Oct 2018", new BigDecimal(110)));
        priceHistories.add(new PriceHistory("10 Jan 2019", new BigDecimal(150)));
        result.add(new Product(null, "sgs", "Samsung Galaxy S", usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",
                priceHistories, priceHistories.getLast().getPrice()));
        LinkedList<PriceHistory> priceHistories1 = new LinkedList<>();
        priceHistories1.add(new PriceHistory("20 Jan 2019", new BigDecimal(200)));
        result.add(new Product(null, "sgs2", "Samsung Galaxy S II", usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"
                , priceHistories1, priceHistories1.getLast().getPrice()));
        LinkedList<PriceHistory> priceHistories2 = new LinkedList<>();
        priceHistories2.add(new PriceHistory("25 Jan 2019", new BigDecimal(300)));
        result.add(new Product(null, "sgs3", "Samsung Galaxy S III", usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"
                , priceHistories2, priceHistories2.getLast().getPrice()));
        result.add(new Product(null, "iphone", "Apple iPhone", usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"
                , priceHistories, new BigDecimal(200)));
        result.add(new Product(null, "iphone6", "Apple iPhone 6", usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"
                , priceHistories1, new BigDecimal(1000)));
        result.add(new Product(null, "htces4g", "HTC EVO Shift 4G", usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"
                , priceHistories2, new BigDecimal(320)));
        result.add(new Product(null, "sec901", "Sony Ericsson C901", usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"
                , priceHistories, new BigDecimal(420)));
        result.add(new Product(null, "xperiaxz", "Sony Xperia XZ", usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"
                , priceHistories1, new BigDecimal(120)));
        result.add(new Product(null, "nokia3310", "Nokia 3310", usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"
                , priceHistories2, new BigDecimal(70)));
        result.add(new Product(null, "palmp", "Palm Pixi", usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"
                , priceHistories, new BigDecimal(170)));
        result.add(new Product(null, "simc56", "Siemens C56", usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"
                , priceHistories2, new BigDecimal(70)));
        result.add(new Product(null, "simc61", "Siemens C61", usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"
                , priceHistories, new BigDecimal(80)));
        result.add(new Product(null, "simsxg75", "Siemens SXG75", usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"
                , priceHistories2, new BigDecimal(150)));

        return result;
    }
}

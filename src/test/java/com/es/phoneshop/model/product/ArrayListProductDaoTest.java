package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;


public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product product;
    private Product productSave;
    private Currency usd;
    private LinkedList<PriceHistory> priceHistories;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        usd = Currency.getInstance("USD");
        priceHistories = new LinkedList<>();
        priceHistories.add(new PriceHistory("1 Set 2018",new BigDecimal(100)));
        product = new Product(3L, "sgs3", "Samsung Galaxy S III", usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"
        ,priceHistories,priceHistories.getLast().getPrice());
        productSave = new Product(null, "sgs", "Samsung Galaxy S", usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"
        ,priceHistories,priceHistories.getLast().getPrice());
        getSampleProducts().forEach(productDao::save);

    }

    @Test
    public void testFindProducts() {
        List<Product> productList = productDao.findProducts();
        assertFalse(productList.isEmpty());
    }

    @Test
    public void  testFindProductById() {
        assertEquals(Optional.of(product),productDao.getProduct(3L));
    }

    @Test
    public void testDeleteProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(4L);
        List<Product> productList = productDao.findProducts();
        assertEquals(sizeBefore-1,productList.size());
    }

    @Test
    public void testSaveProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.save(productSave);
        List<Product> productList = productDao.findProducts();
        assertEquals(sizeBefore + 1,productList.size());
    }


    @Test
    public void findProductsByParameters(){
        List<Product> productList = productDao.findProducts("S","price","asc");
        assertFalse(productList.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductException() {
        productDao.save(product);
    }

        private List<Product> getSampleProducts() {
        LinkedList<PriceHistory> priceHistories = priceHistories = new LinkedList<>();
        priceHistories.add(new PriceHistory("1 Set 2018",new BigDecimal(100)));
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        result.add(new Product(null, "sgs", "Samsung Galaxy S", usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"
        ,priceHistories,priceHistories.getLast().getPrice()));
        result.add(new Product(null, "sgs2", "Samsung Galaxy S II", usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"
        ,priceHistories,priceHistories.getLast().getPrice()));
        result.add(new Product(null, "sgs3", "Samsung Galaxy S III", usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"
        ,priceHistories,priceHistories.getLast().getPrice()));
        result.add(new Product(null, "iphone", "Apple iPhone", usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"
        ,priceHistories,priceHistories.getLast().getPrice()));

        return result;
    }
}

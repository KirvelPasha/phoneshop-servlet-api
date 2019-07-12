package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;


public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product product;
    private Product productSave;
    private Currency usd;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        usd = Currency.getInstance("USD");
        product = new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productSave = new Product(null, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
    }

    @Test
    public void testFindProducts() {
        List<Product> productList = productDao.findProducts();
        assertFalse(productList.isEmpty());
    }

    @Test
    public void  testFindProductById() {
        assertEquals(product,productDao.getProduct(3L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindProductByIdException() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testDeleteProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(4L);
        List<Product> productList = productDao.findProducts();
        assertEquals(sizeBefore - 1,productList.size());
    }

    @Test
    public void testSaveProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.save(productSave);
        List<Product> productList = productDao.findProducts();
        assertEquals(sizeBefore + 1,productList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductException() {
        productDao.save(product);
    }
}

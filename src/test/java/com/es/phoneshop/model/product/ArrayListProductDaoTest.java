package com.es.phoneshop.model.product;

import com.es.phoneshop.enums.SortBy;
import com.es.phoneshop.enums.SortingOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;
    @InjectMocks
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() {
        setupProduct(product1, 1L, 1, "Samsung Galaxy S", new BigDecimal(100));
        setupProduct(product2, 2L, 1, "Apple iPhone", new BigDecimal(200));
        setupProduct(product3, 3L, 0, "Siemens C56", new BigDecimal(300));
        setupProduct(product4, null, 1, "Samsung Galaxy S III", new BigDecimal(200));
        productDao.setProducts(new ArrayList<>(Arrays.asList(product1, product2, product3)));
    }

    private void setupProduct(Product product, Long id, int stock, String description, BigDecimal lastPrice) {
        when(product.getId()).thenReturn(id);
        when(product.getStock()).thenReturn(stock);
        when(product.getDescription()).thenReturn(description);
        when(product.getLastPrice()).thenReturn(lastPrice);
    }

    @Test
    public void testFindProducts() {
        assertSame(product1, productDao.findProducts().get(0));
        assertSame(product2, productDao.findProducts().get(1));
    }

    @Test
    public void testFindProductsByQuery() {
        int size = productDao.findProducts("Apple iPhone").size();
        assertSame(product2, productDao.findProducts("Apple iPhone").get(0));
        assertEquals(1, size);
    }

    @Test
    public void testFindProductByDescriptionAsc() {
        List<Product> result = productDao.findProducts("a s", SortBy.DESCRIPTION,
                SortingOrder.ASC);

        assertEquals(2, result.size());
        assertSame(product2, result.get(0));
        assertSame(product1, result.get(1));
    }

    @Test
    public void testFindProductByDescriptionDesc() {
        List<Product> result = productDao.findProducts("a s", SortBy.DESCRIPTION,
                SortingOrder.DESC);

        assertEquals(2, result.size());
        assertSame(product1, result.get(0));
        assertSame(product2, result.get(1));
    }

    @Test
    public void testFindProductByPriceAsc() {
        List<Product> result = productDao.findProducts("a s", SortBy.PRICE,
                SortingOrder.ASC);

        assertEquals(2, result.size());
        assertSame(product1, result.get(0));
        assertSame(product2, result.get(1));
    }

    @Test
    public void testFindProductByPriceDesc() {
        List<Product> result = productDao.findProducts("a s", SortBy.PRICE,
                SortingOrder.DESC);

        assertEquals(2, result.size());
        assertSame(product2, result.get(0));
        assertSame(product1, result.get(1));
    }

    @Test
    public void testFindProductsWithoutSortBy() {
        List<Product> result = productDao.findProducts(" ", null, null);

        assertSame(product1, productDao.findProducts().get(0));
        assertSame(product2, productDao.findProducts().get(1));
    }

    @Test
    public void testFindProductById() {
        assertEquals(Optional.of(product3), productDao.getProduct(3L));
    }

    @Test
    public void testDeleteProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(product1.getId());
        List<Product> productList = productDao.findProducts();
        assertEquals(sizeBefore - 1, productList.size());
    }

    @Test
    public void testSaveProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.save(product4);
        List<Product> productList = productDao.findProducts();
        assertEquals(sizeBefore + 1, productList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductException() {
        productDao.save(product1);
    }
}

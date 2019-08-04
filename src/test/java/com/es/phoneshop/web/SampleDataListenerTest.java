package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SampleDataListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private SampleDataListener sampleDataListener;

    @Test
    public void contextInitialized() {
        sampleDataListener.contextInitialized(servletContextEvent);
        verify(productDao, times(13)).save(any(Product.class));
    }
}

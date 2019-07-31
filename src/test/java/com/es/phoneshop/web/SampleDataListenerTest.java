package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContextEvent;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SampleDataListenerTest {

    @Mock
    private ServletContextEvent servletContextEvent;

    @InjectMocks
    private SampleDataListener sampleDataListener;

    @Test
    public void contextInitialized() {
        sampleDataListener.contextInitialized(servletContextEvent);
        assertEquals(12, ArrayListProductDao.getInstance().findProducts().size());
    }
}

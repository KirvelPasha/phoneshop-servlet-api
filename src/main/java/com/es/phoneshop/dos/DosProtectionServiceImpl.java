package com.es.phoneshop.dos;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DosProtectionServiceImpl implements DosProtectionService {
    private static DosProtectionServiceImpl instance = new DosProtectionServiceImpl();


    private static final long MAX_REQUEST_COUNT = 20;
    private static long TEN_MINUTES = 60 * 10000;
    private Date saved = new Date();
    private Map<String, AtomicLong> countMap = Collections.synchronizedMap(new HashMap<>());

    private DosProtectionServiceImpl() {

    }

    public static DosProtectionServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        if (System.currentTimeMillis() - saved.getTime() > TEN_MINUTES) {
            countMap.clear();
            saved.setTime(System.currentTimeMillis());
        }
        AtomicLong count = countMap.get(ip);
        if (count == null) {
            count = new AtomicLong(1);
            countMap.put(ip, count);
        }

        return count.incrementAndGet() < MAX_REQUEST_COUNT;
    }
}

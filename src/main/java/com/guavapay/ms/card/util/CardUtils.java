package com.guavapay.ms.card.util;

import java.util.concurrent.ThreadLocalRandom;

public class CardUtils {

    public static Long buildRandomCardNumber() {
        long smallest = 1000_0000_0000_0000L;
        long biggest =  9999_9999_9999_9999L;

        return ThreadLocalRandom.current().nextLong(smallest, biggest+1);
    }
}

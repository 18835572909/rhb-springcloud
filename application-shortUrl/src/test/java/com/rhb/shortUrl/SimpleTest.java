package com.rhb.shortUrl;

import com.rhb.shortUrl.util.Base62Utils;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: rhb
 * @date: 2023/11/29 18:58
 * @description:
 */
public class SimpleTest {

    @Test
    public void base62UtilT1(){
        long id = 100;
        String shortUrl = Base62Utils.idToShortKey(id);
        long shortId = Base62Utils.shortKeyToId(shortUrl);

        System.out.println(shortUrl);
        System.out.println(shortId);
    }

    @Test
    public void optionalUser(){
        Optional<Integer> maxValue = Optional.ofNullable(10);

        System.out.println(maxValue.isPresent());

        maxValue.ifPresent(System.out::println);

        Integer int1 = maxValue.orElse(Integer.MAX_VALUE);
        Integer int2 = maxValue.orElseGet(() -> Integer.MIN_VALUE);
//        Integer int3 = maxValue.orElseThrow(() -> new RuntimeException("Optional Value Is Null"));
        System.out.println("int1:"+int1);
        System.out.println("int2:"+int2);
//        System.out.println("int3:"+int3);

        maxValue.filter(int0 -> int0>0).ifPresent(System.out::println);
        maxValue.map(int0 -> int0.toString()).ifPresent(System.out::println);
    }

    @Test
    public void parallalUse(){
        Stream<Integer> stream = Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        Integer reduce = stream
                .parallel()
                .peek(thread -> System.out.println(Thread.currentThread().getName()))
                .filter(ele->ele>5)
                .reduce(0, (result, ele) -> result = result + ele);

        System.out.println(reduce);
    }

}

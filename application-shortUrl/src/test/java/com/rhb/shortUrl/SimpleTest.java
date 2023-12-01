package com.rhb.shortUrl;

import com.rhb.shortUrl.util.Base62Utils;
import org.junit.Test;

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

}

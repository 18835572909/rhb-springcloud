package com.rhb.shortUrl.ctrl;

import com.rhb.shortUrl.common.ResponseResult;
import com.rhb.shortUrl.service.ShortUrlService;
import com.rhb.shortUrl.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;

/**
 * @author: rhb
 * @date: 2023/12/1 10:37
 * @description:
 */
@RestController
public class ShortController {

    @Resource
    ShortUrlService shortUrlService;

    @GetMapping("/toShortUrl")
    public ResponseResult toShortUrl(String url){
        return ResultUtil.success(shortUrlService.encode(url));
    }

    @GetMapping("/shortUrl/{shortUrl}")
    public RedirectView shortUrlReq(@PathVariable("shortUrl") String shortUrl){
        return new RedirectView(shortUrlService.decode(shortUrl));
    }

}

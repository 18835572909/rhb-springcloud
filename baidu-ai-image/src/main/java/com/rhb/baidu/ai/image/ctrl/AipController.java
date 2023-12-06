package com.rhb.baidu.ai.image.ctrl;

import cn.hutool.core.io.FileUtil;
import com.rhb.baidu.ai.image.util.AipUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author: rhb
 * @date: 2023/12/6 9:07
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/aip")
public class AipController {

    @PostMapping("/upload")
    public String imgToText(MultipartFile file){
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();

        //获取文件的类型
        String type = FileUtil.extName(originalFilename);
        log.info("文件类型是：" + type);

        //获取文件大小
        long size = file.getSize();
        log.info("文件大小是：" + size);

        File imageFile = new File("d://" + System.currentTimeMillis() + "_" + originalFilename);
        try {
            file.transferTo(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = AipUtil.imgToText(imageFile.getPath());
        JSONObject json = Optional.ofNullable(jsonObject).orElse(new JSONObject());
        JSONArray results = json.getJSONArray("words_result");

        StringBuffer sb = new StringBuffer();
        for (int i=0;i<results.length();i++){
            JSONObject jsonObject1 = results.getJSONObject(i);
            String words = jsonObject1.getString("words");
            sb.append("\r\n").append(words);
        }

        imageFile.deleteOnExit();

        return sb.toString();
    }


}

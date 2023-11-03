package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Function：
 * Author：Peilongyu Wang
 * Date：2023/10/13 2:41
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${ip:localhost}")
    String ip;
    @Value("${server.port}")
    String port;
    private static final String ROOT_DIR = System.getProperty("user.dir")+File.separator+"files"; //files directory

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();     //original file name
        // Ex: picture.png
        String mainName = FileUtil.mainName(originalFilename);  //picture
        String extName = FileUtil.extName(originalFilename);  //png
        if(!FileUtil.exist(ROOT_DIR)){
            FileUtil.mkdir(ROOT_DIR);   //If the parent directory of the current file does not exist, create it
        }

        if(FileUtil.exist(ROOT_DIR + File.separator + originalFilename)){  //If the currently uploaded file already exists, rename
            originalFilename = mainName + "_" + System.currentTimeMillis() + "." + extName;
        }

        File saveFile = new File(ROOT_DIR + File.separator + originalFilename);
        file.transferTo(saveFile);  //save file to local storage
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);    //return download link
    }
    @AuthAccess
    @GetMapping ("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        String filePath = ROOT_DIR + File.separator + fileName;
        if(!FileUtil.exist(filePath)){
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();

    }
}
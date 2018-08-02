package com.suye.iblog.controller;

import com.suye.iblog.component.Response;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${fileAddress}")
    private String fileAddress;

    @PostMapping("/upload")
    public ResponseEntity<Response> uploadFile(@RequestParam("uploadFile") MultipartFile file){
        InputStream inputStream=null;
        OutputStream outputStream=null;
        StringBuffer sb=null;
        try {
            inputStream=file.getInputStream();
            String fileName=file.getOriginalFilename();
            File newFile=new File(fileAddress,fileName);
            if (!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs();
            }
            if (newFile.exists()){
                //解决文件同名
                int cnt = 1;
                while(newFile.exists()){
                     sb = new StringBuffer(fileName);
                    sb.insert(sb.lastIndexOf("."), "("+cnt+")");
                    newFile = new File(fileAddress,sb.toString());
                    cnt++;
                }
            }
            outputStream=new FileOutputStream(newFile);
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(new Response(false,"失败了",null));
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok().body(new Response(true,"成功了","/images/"+sb));
        //return ;
    }

    @GetMapping("/")
    public String hello(){
        System.out.println(fileAddress);
        return "/userspace/file";
    }
}

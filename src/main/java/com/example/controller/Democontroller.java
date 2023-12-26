package com.example.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author banana
 * @create 2023-10-06 21:57
 */
@RestController
@RequestMapping("/demo")
public class Democontroller {

    @PostMapping("/excel/test")
    public void excelTest(MultipartFile file){


    }

}

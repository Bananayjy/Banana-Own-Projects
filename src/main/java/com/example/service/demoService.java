package com.example.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author banana
 * @create 2023-12-05 18:51
 */
public interface demoService {

    void solve(Long id, HttpServletRequest request, HttpServletResponse respons);

    void solve2(Long id);
}

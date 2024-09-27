package com.example.custom_log.method3;

import org.springframework.stereotype.Component;

/**
 * @author banana
 * @create 2024-09-25 19:49
 */
@Component
public class getDataImpl implements getData {



    @Override
    public <T> T getOldData() {

        return getData.super.getOldData();
    }

    @Override
    public <T> T getNewData() {


        return getData.super.getNewData();
    }
}

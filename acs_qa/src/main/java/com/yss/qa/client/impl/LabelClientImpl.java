package com.yss.qa.client.impl;

import com.yss.common.entity.Result;
import com.yss.common.entity.StatusCode;
import com.yss.qa.client.LabelClient;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR, "熔断器启动了");
    }
}

package com.vendor.controller;

import com.vendor.utils.ApiResponse;
import com.vendor.utils.DataNotFoundException;
import com.vendor.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandler {

    private static final Log LOGGER = LogFactory.getLog(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = RuntimeException.class)
    //@ResponseBody
    public void handler(Exception e, HttpServletResponse response) {
        if (e instanceof DataNotFoundException) {
           response.setStatus(404);
            try {
                ApiResponse apiResponse = new ApiResponse(((DataNotFoundException) e).getErrorCode(),e.getMessage());
                response.getWriter().append(GsonUtils.ToJson(apiResponse,ApiResponse.class));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            response.setStatus(500);
            try {
                ApiResponse apiResponse = new ApiResponse("4000",e.getMessage());
                response.getWriter().append(GsonUtils.ToJson(apiResponse,ApiResponse.class));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}

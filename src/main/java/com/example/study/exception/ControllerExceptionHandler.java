package com.example.study.exception;

import com.example.study.entity.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller层异常捕获统一处理
 *
 * @author zk
 * @since 2019/10/18
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {



    /**
     * 参数不合法
     *
     * @return response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public JsonResponse handlerException(IllegalArgumentException exception) {
        return JsonResponse.failure("参数不合法：" + exception.getMessage());
    }


    /**
     * exception
     *
     * @return response
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public JsonResponse handlerException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return JsonResponse.failure("系统异常:" + exception.getMessage());
    }


    /**
     * exception
     *
     * @return response
     */
    @ExceptionHandler({CustomException.class})
    @ResponseBody
    public JsonResponse handlerException(CustomException exception) {
        log.error(exception.getMessage(), exception);
        return JsonResponse.failure("系统异常:" + exception.getMessage());
    }
}

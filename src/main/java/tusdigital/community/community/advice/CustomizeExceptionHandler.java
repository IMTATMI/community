package tusdigital.community.community.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import tusdigital.community.community.exception.CustomizeErrorCode;
import tusdigital.community.community.exception.CustomizeException;
import tusdigital.community.community.vo.ResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理错误
 * 跳转错误页面
 * 分两种 传递方式  页面传递  json传递
 * 页面传递 跳转错误页面并显示信息
 * json传递 不显示错误页面 但是传递错误信息
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {

        String contentType = request.getContentType();
        //json 和网页请求的contentType不同 由此来区分 区分的原因在于json作用并不是报错，而是用户做了不规范操作，这时候不应做跳转操作
        //区分 是json 且是自定义异常/不是自定义异常
        //     不是json 且是自定义异常/不是自定义异常
        if ("application/json".equals(contentType)) {
//            System.out.println("是json");
            ResultVo resultVo;
            if(e instanceof CustomizeException){
                resultVo = ResultVo.errorOf((CustomizeException)e);
            }else {
                resultVo = ResultVo.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            // 返回页面和返回Json不能同时存在   直接手写信息给json
            try {

                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultVo));
                writer.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
            return null;
        }else{

            if(e instanceof CustomizeException){
                model.addAttribute("message", e.getMessage());
            }else {
                System.out.println("执行到了非json的错误异常");
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }

//        HttpStatus status = getStatus(request);
//        if(ex instanceof CustomizeException){
//            //自己手动抛错才会走这里
//            model.addAttribute("message", ex.getMessage());
//        }else {
//            model.addAttribute("message","你怎么回事小老弟");
//        }
//
//        return new ModelAndView("error");
    }

//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }


}

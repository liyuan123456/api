package com.li.api.core;

import com.li.api.core.exceptionCodeConfiguration.HttpExceptionCodeConfiguration;
import com.li.api.exception.ForbiddenException;
import com.li.api.exception.HttpExcpetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author 黎源
 * @date 2020/11/13 14:15
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    @Qualifier(value = "httpExceptionCodeConfiguration")
    private HttpExceptionCodeConfiguration exceptionCode;

    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public UnifyResponse ExceptionHandler(HttpServletRequest req, Exception e) {
        String reqUrl = req.getRequestURI();
        String method = req.getMethod();
        return UnifyResponse.builder().code(999).message("服务器异常").request(method + " " + reqUrl).build();
    }

    @ExceptionHandler(value = HttpExcpetion.class)
    public ResponseEntity<UnifyResponse> HttpExceptionHandler(HttpServletRequest req, HttpExcpetion e) {
        String reqUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse unifyResponse = UnifyResponse.builder()
                .code(e.getCode())
                .message(exceptionCode.getMessage(e.getCode()))
                .request(method + " " + reqUrl)
                .build();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus status = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> r = new ResponseEntity(unifyResponse, header, status);
        return r;
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public UnifyResponse MethodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        String reqUrl = req.getRequestURI();
        String method = req.getMethod();
        //返回所有的异常信息
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return UnifyResponse.builder()
                .code(10001)
                .message(formatAllErrorsToMessageByList(errors))
                .request(method + " " + reqUrl)
                .build();
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public UnifyResponse ConstraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
        String reqUrl = req.getRequestURI();
        String method = req.getMethod();
        Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
        return UnifyResponse.builder()
                .code(10001)
                .message(formatAllErrorsToMessageBySet(errors))
                .request(method + " " + reqUrl)
                .build();
    }

    private String formatAllErrorsToMessageBySet(Set<? extends ConstraintViolation> errors) {
        StringBuffer message = new StringBuffer();
        errors.forEach(error -> {
            message.append(error.getMessage()).append(";");
        });
        return message.toString();
    }

    private String formatAllErrorsToMessageByList(List<ObjectError> errors) {
        StringBuffer message = new StringBuffer();
        errors.forEach(error -> {
            message.append(error.getDefaultMessage()).append(";");
        });
        return message.toString();
    }
}

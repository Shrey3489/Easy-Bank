package com.easyBank.accounts.Excepction;

import com.easyBank.accounts.Dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExcepctionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalExcepction(Exception customerAlreadyExistExcepction, WebRequest webRequest)
    {
        ErrorResponseDto moErrorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                customerAlreadyExistExcepction.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(moErrorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerAlreadyExistExcepction.class)
    public ResponseEntity<ErrorResponseDto> handleCntomerAlreadyExistExcepction(CustomerAlreadyExistExcepction customerAlreadyExistExcepction, WebRequest webRequest)
    {
        ErrorResponseDto moErrorResponseDto = new ErrorResponseDto(
         webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                customerAlreadyExistExcepction.getMessage(),
                LocalDateTime.now()
                );
        return new ResponseEntity<>(moErrorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundExcepction.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundExcepction resourceNotFoundExcepction, WebRequest webRequest)
    {
        ErrorResponseDto moErrorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                resourceNotFoundExcepction.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(moErrorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)
    {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);

    }
}

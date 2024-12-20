package ru.yandex.practicum.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.dto.error.ErrorResponse;

import java.io.IOException;

public class ErrorHandler {
    private final ObjectMapper objectMapper;

    public ErrorHandler() {
        this.objectMapper = new ObjectMapper();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({ProductInShoppingCartLowQuantityInWarehouse.class, NoProductsInShoppingCartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleProductInShoppingCartLowQuantityInWarehouse(Exception ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotAuthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleNotAuthorizedUserException(final NotAuthorizedUserException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFeignException(final FeignException ex) {
        try {
            ErrorResponse errorResponse = objectMapper.readValue(ex.contentUTF8(), ErrorResponse.class);
            return new ErrorResponse(errorResponse.getError(), HttpStatus.BAD_REQUEST.value());
        } catch (IOException e) {
            return new ErrorResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @ExceptionHandler(RetryableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRetryableException(final RetryableException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

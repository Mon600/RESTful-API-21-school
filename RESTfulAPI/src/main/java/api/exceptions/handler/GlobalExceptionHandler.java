package api.exceptions.handler;


import api.exceptions.*;
import api.exceptions.DTO.ErrorDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            SupplierNotFoundException.class,
            ClientNotFoundException.class,
            AddressNotFoundException.class,
            ProductNotFoundException.class,
            ImageNotFoundException.class
    })
    @ApiResponse(
            description = "Not found",
            responseCode = "404",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(
                            name = "404 Not Found",
                            value = "{\"code\": 404, \"message\": \"Поставщик с ID 15 не найден в базе данных\", \"date\": \"2026-09-02-17:05\"}"
                    )
            )
    )
    public ResponseEntity<ErrorDTO> handleNotFound(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpStatus.NOT_FOUND, ex.getMessage()));
    }



    @ExceptionHandler(IOException.class)
    @ApiResponse(
            description = "Internal Error",
            responseCode = "500",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(
                            name = "500 Internal Error",
                            value = "{\"code\": 500, \"message\": \"Ошибка при загрузке изображения\", \"date\": \"2026-09-02-17:05\"}"
                    )
            )
    )
    public ResponseEntity<ErrorDTO> handleIOException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid image"));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    @ApiResponse(
            description = "Bad request",
            responseCode = "400",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(
                            name = "400 Bad request",
                            value = "{\"code\": 400, \"message\": \"Введены неверные данные\", \"date\": \"2026-09-02-17:05\"}"
                    )
            )
    )
    public ResponseEntity<ErrorDTO> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ApiResponse(
            description = "Bad request",
            responseCode = "409",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(
                            name = "409 Conflict",
                            value = "{\"code\": 409, \"message\": \"Такой пользователь уже существует\", \"date\": \"2026-09-02-17:05\"}"
                    )
            )
    )
    public  ResponseEntity<ErrorDTO> handleIntegrityError(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorDTO(HttpStatus.CONFLICT, "Duplicate entity combination name + supplier"));
    }
}

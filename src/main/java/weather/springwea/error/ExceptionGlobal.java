package weather.springwea.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
@ControllerAdvice
public class ExceptionGlobal {

    private static final Logger LOGGER =
            LogManager.getLogger(ExceptionGlobal.class);
    /**
     * Обрабатывает исключение типа HttpClientErrorException
     * и возвращает ответ с кодом состояния 400.
     * @param ex      исключение типа HttpClientErrorException,
     *                которое нужно обработать
     * @param request запрос, который вызвал исключение
     * @return ответ с кодом состояния 400 Bad Request
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(
            final HttpClientErrorException ex,
            final WebRequest request) {
        LOGGER.error("400 Bad Request");
        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body("400 Bad Request");
    }

    /**
     * Обрабатывает исключение типа HttpRequestMethodNotSupportedException
     * и возвращает ответ с кодом состояния 405.
     *
     * @param ex      исключение типа HttpRequestMethodNotSupportedException,
     *                которое нужно обработать
     * @param request запрос, который вызвал исключение
     * @return ответ с кодом состояния 405 Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException ex,
            final WebRequest request) {
        LOGGER.error("405 Method Not Allowed");
        return ResponseEntity.status(
                HttpStatus.METHOD_NOT_ALLOWED).body("405 Method Not Allowed");
    }

    /**
     * Обрабатывает исключение типа RuntimeException
     * и возвращает ответ с кодом состояния 500.
     *
     * @param ex      исключение типа RuntimeException, которое нужно обработать
     * @param request запрос, который вызвал исключение
     * @return ответ с кодом состояния 500 Internal Server Error
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(
            final RuntimeException ex,
            final WebRequest request) {
        LOGGER.error("500 Internal Server Error");
        return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR).body(
                "500 Internal Server Error");
    }

}

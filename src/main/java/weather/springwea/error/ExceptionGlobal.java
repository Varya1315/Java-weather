package weather.springwea.error;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


    @ControllerAdvice
    public class ExceptionGlobal {

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex, WebRequest request) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null));
        }

        @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
        public ResponseEntity<ApiResponse<String>> handleNotFoundException(ChangeSetPersister.NotFoundException ex, WebRequest request) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ex.getMessage(), null));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<String>> handleGlobalException(Exception ex, WebRequest request) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("An internal server error occurred", null));
        }
    }


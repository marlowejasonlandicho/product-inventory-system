package exam.product.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import exam.product.dto.ApiResponse;
import exam.product.dto.ApiStatus;
import exam.product.dto.ProductDto;
import jakarta.el.MethodNotFoundException;

/**
 * 
 * <p>
 * REST API wide exception handler for the Product Inventory System
 * </p>
 * 
 * 
 * @author Marlowe Landicho
 *
 * @version
 * 
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> handleRuntimeException(RuntimeException rex) {
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();
		apiResponse.setApiStatus(ApiStatus.FAILED);
		apiResponse.setMessage(rex.getMessage());
		return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodNotFoundException.class)
	protected ResponseEntity<Object> handleMethodNotFoundException(MethodNotFoundException mnfex) {
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();
		apiResponse.setApiStatus(ApiStatus.FAILED);
		apiResponse.setMessage(mnfex.getMessage());
		return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();
		apiResponse.setApiStatus(ApiStatus.FAILED);
		apiResponse.setMessage(ex.getMessage());
		return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();
		apiResponse.setApiStatus(ApiStatus.FAILED);
		apiResponse.setMessage(ex.getMessage());
		return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodNotAllowedException.class)
	protected ResponseEntity<ApiResponse> handleMethodNotAllowedException(MethodNotAllowedException mnaex) {
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();
		apiResponse.setApiStatus(ApiStatus.FAILED);
		apiResponse.setMessage(mnaex.getMessage());
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<ApiResponse> handleNullPointerException(NullPointerException npex) {
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();
		apiResponse.setApiStatus(ApiStatus.FAILED);
		apiResponse.setMessage(npex.getMessage());
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	ResponseEntity<ApiResponse> productNotFoundHandler(ProductNotFoundException ex) {
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();
		apiResponse.setApiStatus(ApiStatus.NOT_FOUND);
		apiResponse.setMessage(ex.getMessage());
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

}

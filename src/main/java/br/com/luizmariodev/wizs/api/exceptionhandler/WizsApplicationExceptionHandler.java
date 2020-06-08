package br.com.luizmariodev.wizs.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.luizmariodev.wizs.domain.exception.BusinessException;
import br.com.luizmariodev.wizs.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class WizsApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	
	private final MessageSource messageSource;
	private static final String MASEGE_DATA_INVALID = "Invalid data fills in correctly and resent";
	
	public WizsApplicationExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<?> handlerEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		var problem = createProblemDetail(TypeProblem.ENTITY_NOT_FOUND, status, ex.getMessage())
						.mensageUser(ex.getMessage())
						.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);				
	}
	
	@ExceptionHandler({BusinessException.class})
	public ResponseEntity<?> handlerEntityNotFoundException(BusinessException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		var problem = createProblemDetail(TypeProblem.ENTITY_NOT_FOUND, status, ex.getMessage())
				.mensageUser(ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);				
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		var statusResponse = HttpStatus.BAD_REQUEST;
				
		var bindResult = ex.getBindingResult();
		List<Problem.Property> propriedades = bindResult.getFieldErrors().stream()
													.map(FieldError ->  {
														String mensageUser = messageSource.getMessage(FieldError, LocaleContextHolder.getLocale());
														
														return Problem.Property.builder()
																.name(FieldError.getField())
																.mensageUser(mensageUser)
																.build();
													})
													.collect(Collectors.toList());
		
		var problem = createProblemDetail(TypeProblem.ERROR_BUSINESS, statusResponse, MASEGE_DATA_INVALID)		
				.mensageUser(MASEGE_DATA_INVALID)
				.properties(propriedades)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, statusResponse, request);
	}

	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
					.status(status.value())
					.title(status.getReasonPhrase())
					.timestamp(LocalDateTime.now())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.status(status.value())
					.detail((String) body)
					.timestamp(LocalDateTime.now())
					.build();
		} 
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemDetail(TypeProblem typeProblem, HttpStatus status, String detail){
		return new Problem.ProblemBuilder()
					.status(status.value())
					.type(typeProblem.getPath())
					.title(typeProblem.getTitle())
					.detail(detail)
					.timestamp(LocalDateTime.now());
	}
}

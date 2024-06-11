package com.vilan.pablo.technology.store.rest.common;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vilan.pablo.technology.store.model.exceptions.DuplicateInstanceException;
import com.vilan.pablo.technology.store.model.exceptions.IncorrectLoginException;
import com.vilan.pablo.technology.store.model.exceptions.InstanceNotFoundException;
import com.vilan.pablo.technology.store.model.exceptions.PermissionException;
import com.vilan.pablo.technology.store.rest.dtos.ErrorsDto;
import com.vilan.pablo.technology.store.rest.dtos.FieldErrorDto;


@ControllerAdvice
public class CommonControllerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        
        List<FieldErrorDto> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
                .filter(error -> error.getDefaultMessage() != null)
                .map(error -> new FieldErrorDto(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        String globalError = exception.getBindingResult().getGlobalErrors().stream()
                .filter(error -> error.getDefaultMessage() != null)
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse(null);

        ErrorsDto errorsDto = new ErrorsDto();
        if (!fieldErrors.isEmpty()) {
            errorsDto.setFieldErrors(fieldErrors);
        }
        if (globalError != null) {
            errorsDto.setGlobalError(globalError);
        }

        return errorsDto;
    }

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleMethodArgumentNotValidException(Exception exception) {
				
		String errorMessage = exception.getMessage();
		
		return new ErrorsDto(errorMessage);
	    
	}
	
	@ExceptionHandler(InstanceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleInstanceNotFoundException(InstanceNotFoundException exception) {
		
		String errorMessage = exception.getKey().toString();

		return new ErrorsDto(errorMessage);
		
	}
	
	@ExceptionHandler(DuplicateInstanceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleDuplicateInstanceException(DuplicateInstanceException exception) {
		
		String errorMessage = exception.getKey().toString();

		return new ErrorsDto(errorMessage);
		
	}
	
	@ExceptionHandler(PermissionException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorsDto handlePermissionException(PermissionException exception) {
		
		String errorMessage = exception.getMessage();

		return new ErrorsDto(errorMessage);
		
	}

	@ExceptionHandler(IncorrectLoginException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectLoginException(IncorrectLoginException exception) {
		
		String errorMessage = exception.getMessage();

		return new ErrorsDto(errorMessage);
		
	}

}

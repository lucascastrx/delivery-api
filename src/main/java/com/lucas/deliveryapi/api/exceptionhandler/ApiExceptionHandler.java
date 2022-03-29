package com.lucas.deliveryapi.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.lucas.deliveryapi.domain.exception.BusinessException;
import com.lucas.deliveryapi.domain.exception.EntityNotFoundException;
import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        if(rootCause instanceof InvalidFormatException){
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status,request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "O corpo da requisição está inválido! Verifique erros de sintaxe.";
        ApiError error = createApiErrorBuilder(status, ErrorType.INCOMPREHENSIVE_MESSAGE, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException e,
                                                                HttpHeaders headers, HttpStatus status,
                                                                WebRequest request) {
        String path = joinPath(e.getPath());

        String userDetail = String.format("A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido.",
                path, e.getValue());

        String detail = String.format(userDetail + " Corrija e informe um valor compatível com o tipo %s.",
                e.getTargetType().getSimpleName());

        ApiError error = createApiErrorBuilder(status, ErrorType.INCOMPREHENSIVE_MESSAGE, detail)
                .userMessage(userDetail)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);

    }

    public ResponseEntity<Object> handlePropertyBinding(PropertyBindingException e,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request){
        String path = joinPath(e.getPath());

        String detail = String.format("A propriedade '%s' não existe ." +
                        "Corrija ou remova essa propriedade e tente novamente.",
                path);

        ApiError error = createApiErrorBuilder(status, ErrorType.INCOMPREHENSIVE_MESSAGE, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        if(e instanceof MethodArgumentTypeMismatchException){
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) e, headers, status, request);
        }
        return super.handleTypeMismatch(e, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpHeaders headers, HttpStatus status,
                                                                             WebRequest request) {
        String userDetail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido.", e.getName(), e.getValue());

        String detail = String.format(userDetail + " Corrija e informe um valor compatível com o tipo %s.",
                 e.getRequiredType().getSimpleName());

        ApiError error = createApiErrorBuilder(status, ErrorType.INVALID_PARAM, detail)
                .userMessage(userDetail)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<ApiError.Field> errorFields = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    return ApiError.Field.builder()
                            .name(fieldError.getField())
                            .userMessage(message)
                            .build();
                        }
                )
                .collect(Collectors.toList());
        ApiError error = createApiErrorBuilder(status, ErrorType.INVALID_DATA, detail)
                .userMessage(detail)
                .fields(errorFields)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O recurso %s , que você tentou acessar, é inexistente.",
                e.getRequestURL());
        ApiError error = createApiErrorBuilder(status, ErrorType.RESOURCE_NOT_FOUND,detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException e, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiError error = createApiErrorBuilder(status, ErrorType.RESOURCE_NOT_FOUND, e.getMessage())
                .userMessage(e.getMessage())
                .build();

//        ApiError error = ApiError.builder()
//                .status(status.value())
//                .type("https://delivery.com/entity-not-found")
//                .title("Entidade não encontrada")
//                .detail(e.getMessage())
//                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
//        ApiError error = ApiError.builder()
//                .dataHora(LocalDateTime.now())
//                .mensagem(e.getMessage())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(error);
    }

    @ExceptionHandler(EntityUnviableException.class)
    public ResponseEntity<?> handleEntityUnviable(EntityUnviableException e, WebRequest request){
        HttpStatus status = HttpStatus.CONFLICT;

        ApiError error = createApiErrorBuilder(status, ErrorType.ENTITY_UNVIABLE, e.getMessage())
                .userMessage(e.getMessage())
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException e, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError error = createApiErrorBuilder(status, ErrorType.BUSINESS_ERROR, e.getMessage())
                .userMessage(e.getMessage())
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }
//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    public ResponseEntity<?> handleExceptions(){
//
//        ApiError error = ApiError.builder()
//                .dataHora(LocalDateTime.now())
//                .mensagem("O tipo de mídia não é aceito!")
//                .build();
//
//        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                .body(error);

//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception e, WebRequest request){
        String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";
        e.printStackTrace();

        ApiError error = createApiErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYSTEM_ERROR,detail)
                .userMessage("Serviço indisponível no momento, por favor entre em contato com os administradores caso o erro persista.")
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {
        if(body == null) {
            body = ApiError.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();

        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ErrorType type,
               String detail){
        return ApiError.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }

    private String joinPath(List<JsonMappingException.Reference> references){

        return references.stream()
                .map(reference -> reference.getFieldName())
                .collect(Collectors.joining("."));
    }


}

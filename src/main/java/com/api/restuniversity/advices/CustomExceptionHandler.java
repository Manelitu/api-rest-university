package com.api.restuniversity.advices;

import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RestControllerAdvice
public class CustomExceptionHandler {
    private final Map<Class<? extends Exception>, Supplier<ProblemDetail>> exceptionHandlers = new HashMap<>();

    public CustomExceptionHandler() {
        exceptionHandlers.put(BadCredentialsException.class, () -> createProblemDetail(HttpStatus.UNAUTHORIZED, "Authentication failure"));
        exceptionHandlers.put(AccessDeniedException.class, () -> createProblemDetail(HttpStatus.FORBIDDEN, "Not authorized"));
        exceptionHandlers.put(SignatureException.class, () -> createProblemDetail(HttpStatus.FORBIDDEN, "JWT Signature not valid"));
        exceptionHandlers.put(BadRequestException.class, () -> createProblemDetail(HttpStatus.BAD_REQUEST, "Bad request"));
        exceptionHandlers.put(ConflictException.class, () -> createProblemDetail(HttpStatus.CONFLICT, "Conflict"));
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex) {
        Supplier<ProblemDetail> handler = exceptionHandlers.get(ex.getClass());
        return handler != null ? handler.get() : null;
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String detail) {
        ProblemDetail error = ProblemDetail.forStatusAndDetail(status, detail);
        error.setProperty("access_denied_reason", detail);
        return error;
    }
}

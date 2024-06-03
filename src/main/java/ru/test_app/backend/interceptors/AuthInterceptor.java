package ru.test_app.backend.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.test_app.backend.utils.jwt.JwtExceptions;
import ru.test_app.backend.utils.jwt.JwtProvider;

import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;

    public AuthInterceptor(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String clientMethod = request.getMethod();

        /* FIX CORS ERROR: Response for preflight does not have HTTP ok status */

        if(Objects.equals(clientMethod, "OPTIONS")) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }

        try {
            final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
            String authHeader = request.getHeader("Authorization");
            final String pathUrl = request.getRequestURI();
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized. Missing Authorization header or must be bearer");
                return false;
            }

            String token = authHeader.substring(7);
            JwtExceptions checkToken = jwtProvider.checkToken(token);
            response.setContentType("application/json");

            switch (checkToken) {
                case MALFORMED_JWT_EXCEPTION:
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Malformed JWT token");
                    return false;
                case EXPIRED_JWT_EXCEPTION:
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Expired JWT token");
                    return false;
                case INVALID_JWT_EXCEPTION:
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Invalid JWT token");
                    return false;
            }
            return true;
        } catch (Exception e) {
            System.out.printf("Error in auth interceptor: %s\n", e.getMessage());
            return false;
        }
    }

}

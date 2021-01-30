package com.guavapay.ms.card.security;

import com.guavapay.ms.card.error.CommonException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

public class SecurityContextUtils {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_START_INDEX = 7;

    public static <T extends CommonException> String extractToken(String authorizationHeader, Supplier<T> exception) {
        String token = tokenFormatIsValid(authorizationHeader) ?
                authorizationHeader.replace(TOKEN_PREFIX, "") : null;

        if (StringUtils.isEmpty(token))
            throw exception.get();

        return token;
    }

    public static boolean tokenFormatIsValid(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader) &&
                authorizationHeader.startsWith(TOKEN_PREFIX) &&
                authorizationHeader.length() > TOKEN_START_INDEX;
    }

    public static String getCurrentUserJWT() {
        return (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getCredentials();
    }
}

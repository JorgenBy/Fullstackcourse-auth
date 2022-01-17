package com.drmarkdown.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class MarkdownTokenAuthException extends AuthenticationException {
    public MarkdownTokenAuthException(String s) {
        super(s);
    }
}

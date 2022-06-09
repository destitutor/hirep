package net.hexabrain.hireo.web.common.security;

import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CurrentSecurityContext(expression = "authentication.principal")
public @interface CurrentUser {
}

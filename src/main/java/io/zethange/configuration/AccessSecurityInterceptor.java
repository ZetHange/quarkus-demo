package io.zethange.configuration;

import io.zethange.service.user.UserService;
import io.zethange.utils.auth.AccessAuth;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
@AccessAuth
@Priority(Priorities.AUTHENTICATION)
public class AccessSecurityInterceptor implements ContainerRequestFilter {
    @Context
    SecurityContext securityCtx;
    @Inject
    JsonWebToken jwt;
    @Inject
    UserService userService;

    @Inject


    public void filter(ContainerRequestContext context) {
        try {
//            context.setSecurityContext();
            String[] authHeader = context.getHeaderString("Authorization").split(" ");
            System.out.println(authHeader);
        } catch (Exception e) {
            System.out.println("not auth header");
        }
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }
}
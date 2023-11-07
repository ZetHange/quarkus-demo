package io.zethange.configuration;

import io.zethange.configuration.auth.UserContext;
import io.zethange.entity.User;
import io.zethange.service.user.UserService;
import io.zethange.utils.auth.AccessAuth;
import io.zethange.utils.auth.TokenUtil;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Provider
@AccessAuth
@Priority(Priorities.AUTHENTICATION)
public class AccessSecurityInterceptor implements ContainerRequestFilter {
    @Inject
    UserService userService;
    @Inject
    ResourceInfo resourceInfo;
    @Inject
    TokenUtil tokenUtil;

    public void filter(ContainerRequestContext context) {
        Method method = resourceInfo.getResourceMethod();

        AccessAuth annotation = method.getAnnotation(AccessAuth.class);
        String[] roles = annotation.roles();

        try {
            String authHeader = context.getHeaderString("Authorization");

            String[] auth = authHeader.split(" ");
            String type = auth[0];
            String token = auth[1];
            if (!Objects.equals(type, "Bearer")) {
                throw new Exception("type token is not bearer");
            }

            User user = tokenUtil.parseAccessToken(token);

            user = userService.getByUsername(user.getUsername());

            if(roles.length != 0 &&!Arrays.asList(roles).contains(user.getRole())) {
                throw new Exception("role");
            }

            UserContext newContext = new UserContext();
            newContext.setUser(user);

            context.setSecurityContext(newContext);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotAuthorizedException("");
        }
    }
}
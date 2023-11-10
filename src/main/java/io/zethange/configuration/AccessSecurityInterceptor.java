package io.zethange.configuration;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.zethange.configuration.auth.UserContextImpl;
import io.zethange.entity.User;
import io.zethange.exception.BaseException;
import io.zethange.repository.UserRepository;
import io.zethange.utils.auth.AccessAuth;
import io.zethange.utils.auth.TokenUtil;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
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
    UserRepository userRepository;
    @Inject
    ResourceInfo resourceInfo;
    @Inject
    UserContextImpl userContext;
    @Inject
    TokenUtil tokenUtil;

    @Override
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
                throw new Exception();
            }

            User user = tokenUtil.parseAccessToken(token);

            user = userRepository.findById(user.id);

            if(roles.length != 0 &&!Arrays.asList(roles).contains(user.getRole())) {
                throw new ForbiddenException("role");
            }

            userContext.setCurrentUser(user);
        } catch (ForbiddenException e) {
            throw new BaseException(HttpResponseStatus.FORBIDDEN.code(), "Forbidden resource", "You haven't access to this resource");
        } catch (Exception e) {
            throw new BaseException(HttpResponseStatus.UNAUTHORIZED.code(), "Unauthorized", "An error occurred while verifying the token");
        }
    }
}
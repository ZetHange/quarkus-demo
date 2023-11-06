package io.zethange.resource.user;

import io.zethange.entity.User;
import io.zethange.models.auth.LoginRequest;
import io.zethange.models.auth.LoginResponse;
import io.zethange.models.auth.RefreshRequest;
import io.zethange.models.auth.RegisterRequest;
import io.zethange.service.auth.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Auth")
public class AuthResource {
    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @Operation(summary = "Register", description = "Register a new user")
    public LoginResponse register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @POST
    @Path("/login")
    @Operation(summary = "Login", description = "Authenticate user and generate access/refresh token")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return null;
    }

    @POST
    @Path("/refresh")
    @Operation(summary = "Refresh", description = "Get access token using refresh token")
    public LoginResponse refresh(@RequestBody RefreshRequest req) {
        return null;
    }

    @GET
    @Path("/me")
    @Operation(summary = "Get Me", description = "Get user information")
    public User getMe() {
        return null;
    }
}

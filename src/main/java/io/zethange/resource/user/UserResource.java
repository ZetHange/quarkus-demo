package io.zethange.resource.user;

import io.zethange.models.user.UserDto;
import io.zethange.service.user.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User")
public class UserResource {
    @Inject
    UserService userService;

    @GET
    @Operation(summary = "Get all users")
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @GET
    @Operation(summary = "Get user by id")
    @Path("/{id}")
    public UserDto getUserById(@PathParam("id") Long id) {
        return userService.getById(id);
    }

    @GET
    @Operation(summary = "Get count users")
    @Path("/count")
    public Long count() {
        return userService.getCount();
    }
}

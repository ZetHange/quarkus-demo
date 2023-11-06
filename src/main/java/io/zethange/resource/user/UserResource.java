package io.zethange.resource.user;

import io.zethange.entity.User;
import io.zethange.models.user.UserDto;
import io.zethange.repository.UserRepository;
import io.zethange.service.user.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

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
    @Operation(summary = "Get user by username")
    @Path("/username/{username}")
    public UserDto getUserByUsername(@PathParam("username") String username) {
        return userService.getByUsername(username);
    }

    @GET
    @Operation(summary = "Get count users")
    @Path("/count")
    public Long count() {
        return userService.getCount();
    }
}

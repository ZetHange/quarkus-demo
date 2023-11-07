package io.zethange;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Marka API", version = "0.1"
        )
)
@SecurityScheme(securitySchemeName = "JWT",
        description = "JWT Authentication",
        type = SecuritySchemeType.APIKEY,
        scheme = "bearer")
public class TestApplication extends Application {
}

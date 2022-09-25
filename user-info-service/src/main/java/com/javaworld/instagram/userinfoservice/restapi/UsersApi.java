package com.javaworld.instagram.userinfoservice.restapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.codec.multipart.Part;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.processing.Generated;

@Validated
@Tag(name = "users", description = "Operations about users")
public interface UsersApi {

    /**
     * POST /users/ : Create a new user
     * 
     *
     * @param userApiDto  (required)
     * @return Successful operation (status code 200)
     */
    @Operation(
        operationId = "createUser",
        summary = "Create a new user",
        tags = { "User" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserApiDto.class))
            })
        }
    )
    @PostMapping(
        value = "/users/",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<UserApiDto> createUser(
        @Parameter(name = "" ,required=true )  @Valid @RequestBody UserApiDto userApiDto,
        @Parameter(hidden = true) final ServerWebExchange exchange
    );

}

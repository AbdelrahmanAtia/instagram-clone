package com.javaworld.instagram.postservice.features.restapi;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerWebExchange;

import com.javaworld.instagram.postservice.features.restapi.apidto.PostApiDto;
import com.javaworld.instagram.postservice.features.restapi.apidto.PostsCountResponseApiDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@Tag(name = "posts", description = "Operations about posts")
public interface PostsApi {

    /**
     * POST /posts/ : Create a new Post
     * 
     *
     * @param postApiDto  (required)
     * @return Successful operation (status code 200)
     */
    @Operation(
        operationId = "createPost",
        summary = "Create a new Post",
        tags = { "Post" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PostApiDto.class))
            })
        }
    )
    @PostMapping(
        value = "/posts/",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<Void> createPost(
        @Parameter(name = "" ,required=true )  @Valid @RequestBody PostApiDto postApiDto,
        @Parameter(hidden = true) final ServerWebExchange exchange
    );


    /**
     * GET /posts/findByUserId : Finds posts by userId
     * 
     *
     * @param userId  (required)
     * @return successful operation (status code 200)
     *         or Invalid userId (status code 400)
     */
    @Operation(
        operationId = "findPostsByUserId",
        summary = "Finds posts by userId",
        tags = { "Post" },
        responses = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PostApiDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid userId")
        }
    )
    @GetMapping(
        value = "/posts/findByUserId",
        produces = { "application/json" }
    )
    Flux<PostApiDto> findPostsByUserId(
        @NotNull @Parameter(name = "userId", description = "", required = true) @Valid @RequestParam(value = "userId", required = true) Integer userId,
        @Parameter(hidden = true) final ServerWebExchange exchange
    );
    
    
    /**
     * DELETE /posts/deleteByUserId : delete posts by userId
     * 
     *
     * @param userId  (required)
     * @return successful operation (status code 200)
     *         or Invalid userId (status code 400)
     */
    @Operation(
        operationId = "deletePostsByUserId",
        summary = "delete posts by userId",
        tags = { "Post" },
        responses = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid userId")
        }
    )
    @DeleteMapping(
        value = "/posts/deleteByUserId"
    )
    Mono<Void> deletePostsByUserId(
        @NotNull @Parameter(name = "userId", description = "", required = true) @Valid @RequestParam(value = "userId", required = true) Integer userId,
        @Parameter(hidden = true) final ServerWebExchange exchange
    );
    
    
    /**
     * GET /posts/count : Finds posts count for a certain user
     * 
     *
     * @param userId  (required)
     * @return successful operation (status code 200)
     *         or Invalid userId (status code 400)
     */
    @Operation(
        operationId = "findPostsCount",
        summary = "Finds posts count for a certain user",
        tags = { "Post" },
        responses = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PostsCountResponseApiDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid userId")
        }
    )
    @GetMapping(
        value = "/posts/count",
        produces = { "application/json" }
    )
    Mono<PostsCountResponseApiDto> findPostsCount(
        @NotNull @Parameter(name = "userId", description = "", required = true) @Valid @RequestParam(value = "userId", required = true) Integer userId,
        @Parameter(hidden = true) final ServerWebExchange exchange
    );    

}

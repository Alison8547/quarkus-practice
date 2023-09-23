package org.com.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.entity.Movie;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/movie")
@Tag(name = "Movie Resource", description = "Movie API REST")
public class MovieResource {

    private static List<Movie> movieList = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "getMovies",
            summary = "Get Movies",
            description = "Get all movies inside the list"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getListMovie() {
        return Response.ok(movieList).build();
    }

    @GET
    @Path("/size")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
            operationId = "count",
            summary = "Count Movies",
            description = "Size of the list movies"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public Integer count() {
        return movieList.size();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "createMovie",
            summary = "Create new movie",
            description = "Create new Movie to add inside the list"
    )
    @APIResponse(
            responseCode = "201",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response createMovie(@RequestBody(description = "Movie to create", required = true, content = @Content(schema = @Schema(implementation = Movie.class))) Movie movie) {
        movieList.add(movie);
        return Response.status(Response.Status.CREATED).entity(movieList).build();
    }

    @PUT
    @Path("/{id}/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "updateMovie",
            summary = "Update movie",
            description = "Update a Movie inside the list"
    )
    @APIResponse(
            responseCode = "200",
            description = "Movie updated",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response updateMovie(@Parameter(description = "Movie id", required = true)
                                @PathParam("id") Integer id, @Parameter(description = "Movie title", required = true) @PathParam("title") String newTitle) {
        List<Movie> list = movieList.stream()
                .map(movie -> {
                    if (movie.getId().equals(id)) {
                        movie.setTitle(newTitle);
                    }
                    return movie;
                }).toList();

        return Response.ok(list).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "deleteMovie",
            summary = "Delete movie",
            description = "Delete a Movie inside the list"
    )
    @APIResponse(
            responseCode = "204",
            description = "Movie deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Movie not valid",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteMovie(@PathParam("id") Integer id) {
        boolean remove = false;
        Optional<Movie> optionalMovie = movieList.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();

        if (optionalMovie.isPresent()) {
            remove = movieList.remove(optionalMovie.get());
        }

        return remove ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
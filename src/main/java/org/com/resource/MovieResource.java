package org.com.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.entity.Movie;

import java.util.ArrayList;
import java.util.List;

@Path("/movie")
public class MovieResource {

    private static List<Movie> movieList = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListMovie() {
        return Response.ok(movieList).build();
    }

    @GET
    @Path("/size")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer count() {
        return movieList.size();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postMovie(Movie movie) {
        movieList.add(movie);
        return Response.ok(movie).build();
    }

    @PUT
    @Path("/{id}/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") Integer id, @PathParam("title") String newTitle) {
        List<Movie> list = movieList.stream()
                .map(movie -> {
                    if (movie.getId().equals(id)) {
                        movie.setTitle(newTitle);
                    }
                    return movie;
                }).toList();

        return Response.ok(list).build();
    }
}
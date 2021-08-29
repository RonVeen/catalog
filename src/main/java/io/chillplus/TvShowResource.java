package io.chillplus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.chillplus.model.TvShow;

@Path("/api/tv")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TvShowResource {

    private List<TvShow> tvShows = new ArrayList<>();
    private AtomicLong sequence = new AtomicLong(1);

    @GET
    public Response getAll() {
        return Response.ok(tvShows).build();
    }

    @POST
    public Response create(@Valid TvShow show) {
        show.setId(sequence.getAndIncrement());
        tvShows.add(show);
        return Response.status(201).entity(show).build();
    }


    @GET
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneById(@PathParam("title") String title) {
        List<TvShow> show = this.tvShows.stream().filter(s -> s.getTitle().equalsIgnoreCase(title)).limit(1).collect(Collectors.toList());
        if (show.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(show.get(0)).build();
        }
    }


    @DELETE
    public Response deleteAll() {
        this.tvShows.clear();
        return Response.ok().build();
    }


    @DELETE
    @Path("{id}")
    public Response deleteOne(@PathParam("id") Long id) {
        List<TvShow> shows = this.tvShows.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (shows.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.tvShows.remove(shows.get(0));
        return Response.ok().build();
    }
}
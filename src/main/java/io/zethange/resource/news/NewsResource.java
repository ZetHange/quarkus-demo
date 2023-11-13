package io.zethange.resource.news;

import io.zethange.entity.news.News;
import io.zethange.entity.news.NewsCategory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/v1/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "News")
public class NewsResource {
    @GET
    @Operation(summary = "Get all news")
    public List<News> getAllNews() {
        return News.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get news by id")
    public News getNewsById(@PathParam("id") Long id) {
        return News.findById(id);
    }

    @GET
    @Path("/category")
    @Operation(summary = "Get all news categories")
    public List<NewsCategory> getAllCategories() {
        return NewsCategory.listAll();
    }

    @GET
    @Path("/category/{id}")
    @Operation(summary = "Get news category by id")
    public NewsCategory getCategoryById(@PathParam("id") Long id) {
        return NewsCategory.findById(id);
    }
}

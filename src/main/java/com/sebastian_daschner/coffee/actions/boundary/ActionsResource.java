package com.sebastian_daschner.coffee.actions.boundary;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("actions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ActionsResource {

    @Inject
    Actions actions;

    @POST
    @Path("undo")
    public void undoAction(@Valid @NotNull JsonObject json) {
        String actionId = json.getString("actionId");
        actions.undo(actionId);
    }

}

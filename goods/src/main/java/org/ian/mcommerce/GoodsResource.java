package org.ian.mcommerce;

import org.ian.mcommerce.models.Goods;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/goods")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class GoodsResource {

    @PersistenceContext(unitName = "mcommerce")
    private EntityManager em;

    @GET
    public Response getBooks() {

        TypedQuery<Goods> query = em.createNamedQuery("Goods.findAll", Goods.class);

        List<Goods> goodss = query.getResultList();

        return Response.ok(goodss).build();
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") Integer id) {

        Goods b = em.find(Goods.class, id);

        return Response.ok(b).build();
    }

    @POST
    public Response createBook(@Valid Goods b) {
        b.setId(null);

        em.getTransaction().begin();

        em.persist(b);
        em.getTransaction().commit();

        return Response.status(Response.Status.CREATED).entity(b).build();
    }
}
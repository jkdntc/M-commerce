package org.ian.mcommerce.authorization;

import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.validator.constraints.NotEmpty;
import org.ian.mcommerce.models.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jiangkun on 17/1/25.
 */
@RequestScoped
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {
    @PersistenceContext(unitName = "mcommerce")
    private EntityManager em;
    @Context
    ResourceConfig rc;
    @Context
    ResourceContext resourceContext;

    @PermitAll
    @POST
    public Response register(@FormParam("account") @Valid @NotEmpty(message = "请填写帐号") String account,
                             @FormParam("password") @Valid @NotEmpty(message = "请填写密码") String password) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);

        TypedQuery<User> query = em.createQuery("select u from User u where u.account=:account", User.class);
        query.setParameter("account", account);
        List<User> existsUser = query.getResultList();
        if (existsUser.size() > 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("帐号已存在").build();
        }
        em.getTransaction().begin();

        em.persist(user);
        em.getTransaction().commit();

        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @RolesAllowed("Administrator")
    @GET
    public Response getUsers() {
        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);

        List<User> users = query.getResultList();

        return Response.ok(users).build();
    }

}

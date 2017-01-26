package org.ian.mcommerce.authorization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterInjector;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterModifier;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by jiangkun on 17/1/26.
 */
@Provider
public class JsonMappingExceptionOnCollectionResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {
        ObjectWriterInjector.set(new ObjectWriterModifier() {

            @Override
            public ObjectWriter modify(EndpointConfigBase<?> endpoint, MultivaluedMap<String, Object> responseHeaders, Object valueToWrite, ObjectWriter w, JsonGenerator g) throws IOException {
                SimpleFilterProvider filterProvider = new SimpleFilterProvider();
                SimpleBeanPropertyFilter simpleBeanPropertyFilter = new SimpleBeanPropertyFilter() {
                    @Override
                    protected boolean include(BeanPropertyWriter writer) {
                        return true;
                    }

                    @Override
                    protected boolean include(PropertyWriter writer) {
                        return true;
                    }
                };
                filterProvider.addFilter("org.ian.mcommerce.models.User", simpleBeanPropertyFilter);
                filterProvider.addFilter("org.ian.mcommerce.models.Goods", simpleBeanPropertyFilter);
                return w.with(filterProvider);
            }
        });
    }
}
package com.sebastian_daschner.files;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Provider
public class Filter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
//        InputStream is = requestContext.getEntityStream();
//        try {
//            byte[] data = is.readAllBytes();
//            String body = new String(data, StandardCharsets.UTF_8);
//            System.out.println(body);
//            requestContext.setEntityStream(new ByteArrayInputStream(data));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

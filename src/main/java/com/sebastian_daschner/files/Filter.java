package com.sebastian_daschner.files;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Provider
public class Filter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
//        requestContext.getHeaders().forEach((k, v) -> System.out.println(k + ": " + v.get(0)));
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

package com.sebastian_daschner.files;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
@Path("files.html")
public class FilesController {

    @Location("files.html")
    Template template;

    @GET
    public TemplateInstance get() {
        return template.instance();
    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public TemplateInstance upload(@MultipartForm MultipartFormDataInput input) throws IOException {
//
//        System.out.println(">>>");
//
//        System.out.println("text = " + input.getFormDataPart("text", new GenericType<String>() {}));
//
//        for (InputPart part : input.getParts()) {
//            System.out.println("part.getHeaders() = " + part.getHeaders());
//            System.out.println("part.getFileName() = " + part.getFileName());
//            System.out.println("part.getBody() = " + new String(part.getBody().readAllBytes()));
//            System.out.println("---");
//        }
//        System.out.println("<<<");
//
//        return template.instance();
//    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public TemplateInstance upload(FileUploadInput input) throws IOException {

        System.out.println(">>>");

        System.out.println("input.text = " + input.text);

        for (FileUpload file : input.file) {
            System.out.println("input.file = " + file.uploadedFile());
            System.out.println("input.filename = " + file.fileName());
            System.out.println("input.file.content = " + Files.readString(file.uploadedFile()));
        }

        System.out.println("<<<");

        return template.instance();
    }

    public static class FileUploadInput {

        @FormParam("text")
        public String text;

        @FormParam("file")
        public List<FileUpload> file;

    }

}

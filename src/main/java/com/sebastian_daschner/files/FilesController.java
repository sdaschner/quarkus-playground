package com.sebastian_daschner.files;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

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
//    public TemplateInstance upload(Map<String, InputStream> parts) throws IOException {
//
//        System.out.println(">>>");
//        for (Map.Entry<String, InputStream> entry : parts.entrySet()) {
//            System.out.println(entry.getKey() + ": " + new String(entry.getValue().readAllBytes()));
//        }
//        System.out.println("<<<");
//
//        return template.instance();
//    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public TemplateInstance upload(@MultipartForm MultipartInput input) throws IOException {
//
//        System.out.println(">>>");
//        for (InputPart part : input.getParts()) {
//            System.out.println("part.getHeaders() = " + part.getHeaders().getFirst("Content-Disposition"));
//            System.out.println("part.getBodyAsString() = " + part.getBodyAsString());
//        }
//        System.out.println("<<<");
//
//        return template.instance();
//    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public TemplateInstance upload(@MultipartForm FileUploadInput input) throws IOException {
//
//        System.out.println(">>>");
//        System.out.println("input.text = " + input.text);
//        System.out.println("input.file = " + input.file.getAbsolutePath());
//        System.out.println("content = " + Files.readString(input.file.toPath()));
//        System.out.println("<<<");
//
//        return template.instance();
//    }
//
//    public static class FileUploadInput {
//
//        @FormParam("text")
//        public String text;
//
//        @FormParam("file")
//        public File file;
//
//    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public TemplateInstance upload(@MultipartForm FileUploadInput input) throws IOException {

        System.out.println(">>>");
        System.out.println("input.text = " + input.text);
//        for (FileUpload upload : input.file) {
//            System.out.println("input.file = " + upload.uploadedFile());
//            System.out.println("input.fileName = " + upload.fileName());
//            System.out.println("content = " + Files.readString(upload.uploadedFile()));
//        }
        System.out.println("<<<");

        return template.instance();
    }

    public static class FileUploadInput {

        @FormParam("text")
        public String text;

//        @FormParam("file")
//        public List<FileUpload> file;

    }

}

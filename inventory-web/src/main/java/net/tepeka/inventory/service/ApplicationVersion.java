package net.tepeka.inventory.service;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Created by Thomas on 08.03.2015.
 */
@Path("version")
public class ApplicationVersion {

    private final static String MANIFEST = "/META-INF/MANIFEST.MF";
    private final static String IMPL_VERSION = "Implementation-Version";

    @Context
    ServletContext servlet;

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        try {
            InputStream inputStream = servlet.getResourceAsStream(MANIFEST);
            Manifest manifest = new Manifest(inputStream);
            Attributes atts = manifest.getMainAttributes();
            return atts.getValue(IMPL_VERSION);

        } catch (Exception e) {
            return "manifest information is not available :- (";
        }

    }

}

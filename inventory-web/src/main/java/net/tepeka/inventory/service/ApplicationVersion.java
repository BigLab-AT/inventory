package net.tepeka.inventory.service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.io.IOException;
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
    private final static String GIT_SHA1 = "git-SHA-1";

    @Context
    ServletContext servlet;

    @GET
    @Produces("text/plain")
    public String getVersion(@QueryParam("pre") String pre) {
        try {
            InputStream inputStream = servlet.getResourceAsStream(MANIFEST);
            Manifest manifest = new Manifest(inputStream);
            Attributes attributes = manifest.getMainAttributes();
            return (pre != null ? pre : "") + attributes.getValue(IMPL_VERSION) + " " + attributes.getValue(GIT_SHA1);

        } catch (Exception e) {
            return "manifest information is not available :- (";
        }
    }

}
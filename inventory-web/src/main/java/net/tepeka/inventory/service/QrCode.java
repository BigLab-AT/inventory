package net.tepeka.inventory.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Hashtable;

/**
 * Created by Thomas on 09.03.2015.
 */
@Path("qrcode")
public class QrCode {

    private final static int DEFAULT_SIZE = 100;
    private final static String IMAGE_TYPE = "jpeg";
    private final static String CONTENT_TYPE = "image/jpeg";

    @Context
    HttpHeaders header;

    @Context
    HttpServletResponse response;

    @GET
    @Produces(CONTENT_TYPE)
    @Path("{data}")
    public Response getCode(@PathParam("data") String data) {
        return createResponse(data, DEFAULT_SIZE);
    }

    @GET
    @Produces(CONTENT_TYPE)
    @Path("{data}/{size}")
    public Response getCode(
            @PathParam("data") String data,
            @PathParam("size") Integer size) {
        return createResponse(data, size);
    }

    private Response createResponse(String data, Integer size) {
        try {
            if (data == null) {
                return Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                        .entity("data parameter is mandatory")
                        .build();
            }
            if (size == null || size < 70) size = DEFAULT_SIZE;
            response.setContentType(CONTENT_TYPE);
            OutputStream out = response.getOutputStream();
            ImageIO.write(createQRImage(data, size), IMAGE_TYPE, out);
            out.close();
            return Response.ok().build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    private BufferedImage createQRImage(String data, int size) throws WriterException, IOException {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 0);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }
}

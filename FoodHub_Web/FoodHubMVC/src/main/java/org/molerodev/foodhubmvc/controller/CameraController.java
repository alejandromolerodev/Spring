package org.molerodev.foodhubmvc.controller;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@RestController
@RequestMapping("/foodhub/camera")
public class CameraController {

    @GetMapping("/frame")
    public String getCameraFrame() {
        try (FrameGrabber grabber = new OpenCVFrameGrabber(0)) { // 0 es la cámara predeterminada
            grabber.start(); // Inicia la cámara

            // Captura un frame de la cámara
            Frame frame = grabber.grab();

            // Convierte el frame a BufferedImage
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage image = converter.convert(frame);

            // Convierte la imagen a Base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            return base64Image; // Devuelve la imagen en Base64
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Devuelve una cadena vacía en caso de error
        }
    }
}
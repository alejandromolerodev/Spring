package org.molerodev.foodhubmvc.service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class BarcodeScannerService {

    public String scanBarcode() {
        try (FrameGrabber grabber = new OpenCVFrameGrabber(0)) { // 0 es la cámara predeterminada
            grabber.start(); // Inicia la cámara

            // Intentar escanear durante un número máximo de intentos
            int maxAttempts = 10; // Número de frames a capturar
            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                System.out.println("Intento de escaneo #" + attempt);

                // Captura un frame de la cámara
                Frame frame = grabber.grab();

                // Convierte el frame a BufferedImage
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage image = converter.convert(frame);

                // Escanea el código de barras
                MultiFormatReader reader = new MultiFormatReader();
                BinaryBitmap binaryBitmap = new BinaryBitmap(
                        new HybridBinarizer(new BufferedImageLuminanceSource(image))
                );

                try {
                    // Intenta decodificar el código de barras
                    Result result = reader.decode(binaryBitmap);
                    String barcodeText = result.getText();

                    // Imprime el resultado en la consola de IntelliJ
                    System.out.println("Código de barras escaneado: " + barcodeText);

                    return barcodeText; // Devuelve el contenido del código de barras
                } catch (Exception e) {
                    // Imprime un mensaje si no se detecta un código de barras en este frame
                    System.out.println("No se detectó un código de barras en el intento #" + attempt);
                }
            }

            // Si no se detecta un código de barras después de todos los intentos
            System.out.println("No se detectó un código de barras después de " + maxAttempts + " intentos.");
            return "No se detectó un código de barras"; // Mensaje si no se detecta un código
        } catch (Exception e) {
            // Imprime el error si ocurre un problema con la cámara
            System.out.println("Error al acceder a la cámara: " + e.getMessage());
            throw new RuntimeException("Error al acceder a la cámara", e);
        }
    }
}
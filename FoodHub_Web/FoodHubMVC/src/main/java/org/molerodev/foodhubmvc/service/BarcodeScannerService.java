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
import org.molerodev.foodhubmvc.model.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class BarcodeScannerService {

    @Autowired
    private OpenFoodFactsService openFoodFactsService;

    /**
     * Escanea un código de barras utilizando la cámara.
     *
     * @return El código de barras escaneado como String, o un mensaje de error si no se detecta.
     */
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

                    // Imprime el resultado en la consola
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

    /**
     * Obtiene los detalles de un producto a partir del código de barras escaneado.
     *
     * @return Un objeto ProductoDTO con los detalles del producto, o null si no se encuentra.
     */
    public ProductoDTO obtenerDatos(String barcode) {
        barcode = scanBarcode(); // Escanea el código de barras
        if (barcode != null && !barcode.startsWith("No se detectó")) {
            // Obtener el producto desde Open Food Facts
            ProductoDTO productoDTO = openFoodFactsService.getProductByBarcode(barcode);

            if (productoDTO != null) {
                return productoDTO; // Retorna el producto si se encontró
            }
        }
        return null; // Retorna null si no se detecta un código de barras válido o no se encuentra el producto
    }
}
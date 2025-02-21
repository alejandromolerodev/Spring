/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-31 12:19:42
 * @ Modified time: 2025-02-11 11:22:54
 */

package com.molerodev.clase.demo_spring.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRService {

    private static final int WIDTH = 300; // Ancho del código QR
    private static final int HEIGHT = 300; // Alto del código QR

    // Método para generar el código QR como un array de bytes (imagen en formato PNG)
    public byte[] generarQRCode(String data) throws Exception {
        // Crear una instancia de QRCodeWriter
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        
        // Crear un mapa de configuraciones (opcional)
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1); // Margen opcional (puedes cambiarlo si lo deseas)
        
        // Generar el BitMatrix (matriz de bits)
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        
        // Convertir el BitMatrix a un array de bytes (como una imagen en formato PNG)
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        
        // Retornar el array de bytes de la imagen
        return byteArrayOutputStream.toByteArray();
    }
}

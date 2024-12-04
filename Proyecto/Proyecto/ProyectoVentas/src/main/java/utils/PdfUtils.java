package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import main.Producto;

public class PdfUtils {

    // Método para obtener la carpeta de Descargas del usuario
    public static String obtenerRutaDescargas() {
        return System.getProperty("user.home") + File.separator + "Downloads";
    }

    public static void exportarProductoAPDF(Producto producto, String nombreArchivo, String nombreImagen) {
        try {
            // Ruta final para el archivo PDF
            String rutaArchivo = obtenerRutaDescargas() + File.separator + nombreArchivo;

            // Crear documento PDF
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.RED);
            Paragraph titulo = new Paragraph("Detalles del Producto", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // Espacio
            documento.add(new Paragraph("\n"));

            // Información del producto
            Font fontTexto = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            documento.add(new Paragraph("ID: " + producto.getId(), fontTexto));
            documento.add(new Paragraph("Nombre: " + producto.getNombre(), fontTexto));
            documento.add(new Paragraph("Precio: $" + String.format("%.2f", producto.getPrecio()), fontTexto));
            documento.add(new Paragraph("Categoría: " + producto.getCategoria(), fontTexto));
            documento.add(new Paragraph("Descripción: " + producto.getDescripcion(), fontTexto));

            // Espacio antes de la imagen
            documento.add(new Paragraph("\n"));

            // Cargar imagen desde la carpeta 'imagenes'
            if (nombreImagen != null && !nombreImagen.isEmpty()) {
                String rutaImagen = "imagenes" + File.separator + nombreImagen;
                File archivoImagen = new File(rutaImagen);
                if (archivoImagen.exists()) {
                    Image imagen = Image.getInstance(rutaImagen);
                    imagen.scaleToFit(300, 300); // Redimensionar
                    imagen.setAlignment(Image.ALIGN_CENTER);
                    documento.add(imagen);
                } else {
                    System.err.println("Imagen no encontrada en la ruta: " + rutaImagen);
                }
            }

            documento.close();
            System.out.println("PDF creado exitosamente en: " + rutaArchivo);
            
        } catch (DocumentException | IOException e) {
            System.err.println("Error al crear el PDF: " + e.getMessage());
        }
    }
}

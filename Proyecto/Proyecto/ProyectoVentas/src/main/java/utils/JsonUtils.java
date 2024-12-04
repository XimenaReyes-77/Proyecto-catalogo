package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.Producto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Producto> cargarCatalogo(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<Producto>>() {});
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el catálogo: " + e.getMessage());
        }
        return null;
    }

    public static void guardarCatalogo(String fileName, List<Producto> catalogo) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), catalogo);
            System.out.println("Catálogo guardado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el catálogo: " + e.getMessage());
        }
    }
}
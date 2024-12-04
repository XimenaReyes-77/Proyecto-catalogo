package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.JsonUtils;
import utils.PdfUtils;

public class CatalogoProductos {

    private static final String FILE_NAME = "catalogo.json";
    private static List<Producto> catalogo = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        catalogo = JsonUtils.cargarCatalogo(FILE_NAME);
        if (catalogo == null) {
            catalogo = new ArrayList<>();
        }

        verificarCarpetaImagenes();

        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine(); //
            switch (opcion) {
                case 1 -> buscarProductos();
                case 2 -> agregarProducto();
                case 3 -> eliminarProducto();
                case 4 -> exportarProducto();
                case 5 -> borrarPDF();  //
                case 0 -> {
                    JsonUtils.guardarCatalogo(FILE_NAME, catalogo);
                    salir = true;
                    System.out.println("¡Gracias por usar el Catálogo de Productos!");
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void verificarCarpetaImagenes() {
        File carpetaImagenes = new File("imagenes");
        if (!carpetaImagenes.exists()) {
            carpetaImagenes.mkdir();
            System.out.println("Carpeta 'imagenes' creada automáticamente.");
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n*******************");
        System.out.println("* Bienvenido al Catálogo de Productos.          *");
        System.out.println("* Selecciona una de las siguientes opciones:    *");
        System.out.println("* 1) Buscar Productos                           *");
        System.out.println("* 2) Agregar Producto                           *");
        System.out.println("* 3) Eliminar Producto                          *");
        System.out.println("* 4) Exportar Producto a PDF                    *");
        System.out.println("* 5) Borrar PDF                                 *"); 
        System.out.println("* 0) Salir                                      *");
        System.out.println("*******************");
        System.out.print("Opción: ");
    }

    private static void buscarProductos() {
        System.out.println("1) Buscar productos por:");
        System.out.println("   a) ID");
        System.out.println("   b) Categoría");
        System.out.println("   c) Nombre/Descripción");
        System.out.println("   d) Rango de precio");
        System.out.print("Seleccione una opción: ");
        String opcion = scanner.nextLine().trim().toLowerCase();

        switch (opcion) {
            case "a" -> {
                System.out.print("Ingrese el ID del producto: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                Producto producto = obtenerProductoPorId(id);
                if (producto != null) {
                    System.out.println("Producto encontrado:");
                    System.out.println("====================");
                    System.out.println("                    ");
                    System.out.println(        producto        );
                    System.out.println("                    ");
                    System.out.println("====================");
                } else {
                    System.out.println("Producto no encontrado...");
                }
            }
            case "b" -> {
                System.out.print("Ingrese la categoría: ");
                String categoria = scanner.nextLine();
                List<Producto> productosCategoria = buscarPorCategoria(categoria);
                imprimirResultados(productosCategoria);
            }
            case "c" -> {
                System.out.print("Ingrese el nombre o descripción: ");
                String nombreDescripcion = scanner.nextLine();
                List<Producto> productosNombre = buscarPorNombreDescripcion(nombreDescripcion);
                imprimirResultados(productosNombre);
            }
            case "d" -> {
                System.out.print("Ingrese el precio mínimo: ");
                double min = scanner.nextDouble();
                System.out.print("Ingrese el precio máximo: ");
                double max = scanner.nextDouble();
                scanner.nextLine();
                List<Producto> productosRango = buscarPorRangoPrecio(min, max);
                imprimirResultados(productosRango);
            }
            default -> System.out.println("Opción no válida.");
        }
    }

    private static void agregarProducto() {
        System.out.print("Ingrese el ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese el Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Ingrese la Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Ingrese la Descripción: ");
        String descripcion = scanner.nextLine();

        catalogo.add(new Producto(id, nombre, precio, categoria, descripcion));
        System.out.println("Producto agregado exitosamente.");
    }

    private static void eliminarProducto() {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Producto producto = obtenerProductoPorId(id);

        if (producto != null) {
            catalogo.remove(producto);
            System.out.printf("Se borró correctamente el producto \"%s\".%n", producto.getNombre());
            System.out.printf("Quedan %d producto(s) en el catálogo.%n", catalogo.size());
        } else {
            System.out.println("Producto no encontrado...");
        }
    }

    public static Producto obtenerProductoPorId(int id) {
        for (Producto producto : catalogo) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }

    private static List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto producto : catalogo) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                resultados.add(producto);
            }
        }
        return resultados;
    }

    private static List<Producto> buscarPorNombreDescripcion(String texto) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto producto : catalogo) {
            if (producto.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                producto.getDescripcion().toLowerCase().contains(texto.toLowerCase())) {
                resultados.add(producto);
            }
        }
        return resultados;
    }

    private static List<Producto> buscarPorRangoPrecio(double min, double max) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto producto : catalogo) {
            if (producto.getPrecio() >= min && producto.getPrecio() <= max) {
                resultados.add(producto);
            }
        }
        return resultados;
    }

    private static void imprimirResultados(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("Producto(s) no encontrado(s)...");
        } else {
            System.out.println("Producto(s) encontrado(s):");
            for (Producto producto : productos) {
                System.out.println(producto);
            }
        }
    }

    private static void exportarProducto() {
        System.out.print("Ingrese el ID del producto que desea exportar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Producto producto = obtenerProductoPorId(id);
    
        if (producto != null) {
            // Generar nombre del archivo PDF
            String nombreArchivoPDF = String.format("Producto_%d_%s.pdf", producto.getId(), producto.getNombre().replaceAll("\\s+", "_"));
    
            // Preguntar por el nombre de la imagen en la carpeta 'imagenes'
            System.out.print("Ingrese el nombre de la imagen (en la carpeta 'imagenes', o presione Enter para omitir): ");
            String nombreImagen = scanner.nextLine();
    
            // Exportar a PDF
            utils.PdfUtils.exportarProductoAPDF(producto, nombreArchivoPDF, nombreImagen);
        } else {
            System.out.println("Producto no encontrado...");
        }
    }

    private static void borrarPDF() {
    System.out.print("Ingrese el nombre del archivo PDF que desea eliminar (por ejemplo, Producto_1_Nombre.pdf): ");
    String nombreArchivo = scanner.nextLine();
    
    // Ruta donde se encuentran los PDFs
    String rutaArchivo = PdfUtils.obtenerRutaDescargas() + File.separator + nombreArchivo;
    
    File archivo = new File(rutaArchivo);
    
    if (archivo.exists() && archivo.isFile()) {
        if (archivo.delete()) {
            System.out.println("El archivo PDF fue borrado exitosamente.");
        } else {
            System.out.println("No se pudo eliminar el archivo PDF.");
        }
    } else {
        System.out.println("No se encontró el archivo PDF con ese nombre.");
    }
}


}






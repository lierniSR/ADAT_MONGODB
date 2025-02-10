package es.liernisarraoa;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.image.ConvolveOp;

public class MongoDB {
    private static String nombre;
    private static Integer precio;
    private static String tipo;
    private static Integer habitaciones;
    private static Integer habitacionesFiltro;
    private static Integer precioFiltro;
    private static String nombreActualizar;
    private static Integer precioActualizar;
    private static String nombreEliminar;

    public static void main(String[] args) {
        // Definir la URI de conexión a MongoDB Atlas
        String uri = "mongodb+srv://lierni:1234@cluster0.hnyhe.mongodb.net/";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Seleccionar la base de datos "sample_airbnb"
            MongoDatabase database = mongoClient.getDatabase("sample_airbnb");

            // Seleccionar la colección "listingsAndReviews" dentro de la base de datos
            MongoCollection<Document> collection = database.getCollection("listingsAndReviews");

            System.out.println("*****MENU******");
            System.out.println("1.- Añadir apartamento");
            System.out.println("2.- Buscar segun el número de habitacion y segun el rango de dinero");
            System.out.println("3.- Actualizar precio de un apartamento");
            System.out.println("4.- Eliminar apartamento");
            System.out.println("0.- Salir del programa.");
            System.out.println("Elige una opcion:");
            int respuesta = Consola.leeInt();
            while(respuesta != 0){
                if(respuesta == 1){
                    System.out.println("Ha elegido añadir un apartamento nuevo...");
                    System.out.println("Inserte el nombre del apartamento:");
                    nombre = Consola.leeString();
                    System.out.println("Inserte el tipo de apartamento:");
                    tipo = Consola.leeString();
                    System.out.println("Inserte el precio del apartamento:");
                    precio = Consola.leeInt();
                    System.out.println("Inserte el numero de habitaciones del apartamento:");
                    habitaciones = Consola.leeInt();
                    // --- Crear (Insertar) un nuevo documento en la colección ---
                    // Se crea un documento con nombre "Barcelona", tipo "Chalet", 1 habitación y precio 75
                    Document newListing = new Document("name", nombre)
                            .append("summary", tipo)
                            .append("bedrooms", habitaciones)
                            .append("price", precio);

                    // Se inserta el nuevo documento en la colección
                    collection.insertOne(newListing);

                    System.out.println("--- Crear (Insertar) ---");
                    System.out.println("Nuevo apartamento insertado: " + newListing.toJson());
                } else if(respuesta == 2){
                    System.out.println("Has elegido leer los habitaciones segun el rango de dinero y el numero de habitaciones...");
                    System.out.println("Inserte el numero de habitaciones");
                    habitacionesFiltro = Consola.leeInt();
                    System.out.println("Inserte el precio maximo del rango");
                    precioFiltro = Consola.leeInt();
                    // --- Leer (Consultar) documentos según un filtro ---
                    // Se define un filtro para buscar documentos con 1 habitación y precio menor o igual a 100
                    Bson filter = Filters.and(Filters.eq("bedrooms", habitacionesFiltro), Filters.lte("price", precioFiltro));

                    // Se obtiene la lista de documentos que cumplen con el filtro
                    FindIterable<Document> results = collection.find(filter);

                    System.out.println("--- Leer (Consultar) ---");
                    for (Document doc : results) {
                        System.out.println(doc.toJson());
                    }
                } else if(respuesta == 3){
                    System.out.println("Has elegido actualizar el precio segun el nombre del apartamento...");
                    System.out.println("Inserte el nombre del apartamento a actualizar.");
                    nombreActualizar = Consola.leeString();
                    System.out.println("Inserte el nuevo precio del apartamento");
                    precioActualizar = Consola.leeInt();
                    // --- Actualizar el precio del alojamiento ---
                    // Se define un filtro para encontrar el documento con el nombre "Barcelona"
                    Bson updateFilter = Filters.eq("name", nombreActualizar);

                    // Se define la operación de actualización para cambiar el precio a 85
                    Bson updateOperation = Updates.set("price", precioActualizar);

                    // Se actualiza el primer documento que coincida con el filtro
                    collection.updateOne(updateFilter, updateOperation);

                    System.out.println("--- Actualizar ---");

                    Document updatedListing = collection.find(updateFilter).first();
                    if (updatedListing != null) {
                        System.out.println("Apartamento actualizado: " + updatedListing.toJson());
                    }
                } else if(respuesta == 4){
                    System.out.println("Ha elegido eliminar un apartamento...");
                    System.out.println("Inserte el nombre del apartamento a eliminar");
                    nombreEliminar = Consola.leeString();
                    // Se define un filtro para encontrar el documento con el nombre "Barcelona"
                    Bson updateFilter = Filters.eq("name", nombreEliminar);
                    // --- Eliminar el alojamiento actualizado ---
                    // Se elimina el documento que coincida con el filtro "Barcelona"
                    collection.deleteOne(updateFilter);

                    System.out.println("--- Eliminar ---");
                    System.out.println("Alojamiento eliminado.");
                }
                System.out.println("*****MENU******");
                System.out.println("1.- Añadir apartamento");
                System.out.println("2.- Buscar segun el número de habitacion y segun el rango de dinero");
                System.out.println("3.- Actualizar precio de un apartamento");
                System.out.println("4.- Eliminar apartamento");
                System.out.println("0.- Salir del programa.");
                System.out.println("Elige una opcion:");
                respuesta = Consola.leeInt();
            }
            System.out.println("Saliendo del programa...");
        }
    }
}

package es.liernisarraoa;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDB {
    public static void main(String[] args) {
        // Definir la URI de conexión a MongoDB Atlas
        String uri = "mongodb+srv://lierni:1234@cluster0.hnyhe.mongodb.net/";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Seleccionar la base de datos "sample_airbnb"
            MongoDatabase database = mongoClient.getDatabase("sample_airbnb");

            // Seleccionar la colección "listingsAndReviews" dentro de la base de datos
            MongoCollection<Document> collection = database.getCollection("listingsAndReviews");

            // --- Crear (Insertar) un nuevo documento en la colección ---
            // Se crea un documento con nombre "Barcelona", tipo "Chalet", 1 habitación y precio 75
            Document newListing = new Document("name", "Barcelona")
                    .append("summary", "Chalet")
                    .append("bedrooms", 1)
                    .append("price", 75);

            // Se inserta el nuevo documento en la colección
            collection.insertOne(newListing);

            System.out.println("--- Crear (Insertar) ---");
            System.out.println("Nuevo alojamiento insertado: " + newListing.toJson());

            // --- Leer (Consultar) documentos según un filtro ---
            // Se define un filtro para buscar documentos con 1 habitación y precio menor o igual a 100
            Bson filter = Filters.and(Filters.eq("bedrooms", 1), Filters.lte("price", 100));

            // Se obtiene la lista de documentos que cumplen con el filtro
            FindIterable<Document> results = collection.find(filter);

            System.out.println("--- Leer (Consultar) ---");
            for (Document doc : results) {
                System.out.println(doc.toJson());
            }

            // --- Actualizar el precio del alojamiento ---
            // Se define un filtro para encontrar el documento con el nombre "Barcelona"
            Bson updateFilter = Filters.eq("name", "Barcelona");

            // Se define la operación de actualización para cambiar el precio a 85
            Bson updateOperation = Updates.set("price", 85);

            // Se actualiza el primer documento que coincida con el filtro
            collection.updateOne(updateFilter, updateOperation);

            System.out.println("--- Actualizar ---");

            Document updatedListing = collection.find(updateFilter).first();
            if (updatedListing != null) {
                System.out.println("Alojamiento actualizado: " + updatedListing.toJson());
            }

            // --- Eliminar el alojamiento actualizado ---
            // Se elimina el documento que coincida con el filtro "Barcelona"
            collection.deleteOne(updateFilter);

            System.out.println("--- Eliminar ---");
            System.out.println("Alojamiento eliminado.");
        }
    }
}

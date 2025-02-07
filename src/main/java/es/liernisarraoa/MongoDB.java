package es.liernisarraoa;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDB {
    public static void main(String[] args) {
        String uri = "mongodb+srv://lierni:<1234>@cluster0.hnyhe.mongodb.net/";  // Ajusta esto si es necesario

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("test");
            System.out.println("✅ Conectado a MongoDB correctamente!");
        } catch (Exception e) {
            System.out.println("❌ Error al conectar a MongoDB: " + e.getMessage());
        }
    }
    /*public static void main(String[] args) {
        // Reemplaza con tu cadena de conexión de MongoDB Atlas
        String uri = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_airbnb");
            MongoCollection<Document> collection = database.getCollection("listingsAndReviews");

            // Crear (Insertar) un nuevo alojamiento
            Document newListing = new Document("name", "Cozy Studio in Downtown")
                    .append("summary", "Un pequeño pero acogedor apartamento en el centro de la ciudad")
                    .append("bedrooms", 1)
                    .append("price", 75);
            collection.insertOne(newListing);
            System.out.println("--- Crear (Insertar) ---");
            System.out.println("Nuevo alojamiento insertado: " + newListing.toJson());

            // Leer (Consultar) alojamientos en una ciudad específica con un rango de precio
            Bson filter = Filters.and(Filters.eq("bedrooms", 1), Filters.lte("price", 100));
            FindIterable<Document> results = collection.find(filter);
            System.out.println("--- Leer (Consultar) ---");
            for (Document doc : results) {
                System.out.println(doc.toJson());
            }

            // Actualizar el precio del alojamiento recién insertado
            Bson updateFilter = Filters.eq("name", "Cozy Studio in Downtown");
            Bson updateOperation = Updates.set("price", 85);
            collection.updateOne(updateFilter, updateOperation);
            System.out.println("--- Actualizar ---");
            Document updatedListing = collection.find(updateFilter).first();
            if (updatedListing != null) {
                System.out.println("Alojamiento actualizado: " + updatedListing.toJson());
            }

            // Eliminar el alojamiento actualizado
            collection.deleteOne(updateFilter);
            System.out.println("--- Eliminar ---");
            System.out.println("Alojamiento eliminado.");
        }
    }*/
}

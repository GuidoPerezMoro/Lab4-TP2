/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Set;
import com.mongodb.DB;
import java.util.Arrays;
        
/**
 *
 * @author Gerardo
 */
public class MongoDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Prueba conexión MongoDB");
        MongoClient mongoClient = crearConexion();
 
        if (mongoClient != null) {
            System.out.println("Conexion Exitosa");
            List<String> databases = mongoClient.getDatabaseNames();
             
            for (String dbName : databases) {
                System.out.println("Database: " + dbName);
                 
                DB db = mongoClient.getDB(dbName);
                 
                Set<String> collections = db.getCollectionNames();
                for (String colName : collections) {
                    System.out.println("\t + Collection: " + colName);
                }
            }
            //Inserto un nuevo documento en una coleccion
            
            MongoDatabase database = mongoClient.getDatabase("db");
            MongoCollection<Document> autores = database.getCollection("autores");
            
            List<String> secciones = Arrays.asList("iOS" ,"Objective C" ,"NodeJS");
            Document autor = new Document("_id", new ObjectId())
                            .append("nombre", "Gerardo")
                            .append("apellido", "Magni")
                            .append("socialAdmin", true)
                            .append("domicilio", new Document("calle", "San Martin 5555")
                                                         .append("ciudad", "Lujan")
                                                         .append("provincia", "MZA")
                                                         .append("codPostal", 12345))
                            .append("secciones", secciones);
            
            autores.insertOne(autor);  
            System.out.println("Nuevo Autor agregado a la coleccion autores");
            
            //muestro los documentos de la coleccion
            System.out.println("Documentos de la coleccion");
            FindIterable<Document> docs = autores.find();
            for(Document doc : docs){
                System.out.println(doc);
            }
            System.out.println("-------------------------------------------------");
            //muestro los documentos de la coleccion
            System.out.println("Documentos de la coleccion ordenados por apellido ascendente limite de 10");
            docs = autores.find().sort(new BasicDBObject("apellido",1)).limit(10); // 1 ascending order -1 descending order
            for(Document doc : docs){
                System.out.println(doc);
            }
            //cierro la conexion
            mongoClient.close();
 
 
        } else {
            System.out.println("Error: Conexión no establecida");
        }
    }
 
    
    private static MongoClient crearConexion() {
        MongoClient mongo = null;
        try {
            mongo = new MongoClient("localhost", 27017);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return mongo;
    }
 
  
    
}

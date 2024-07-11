//package com.example.touristapplicationbyhighhopes;
//
//import static com.mongodb.client.model.Filters.*;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.ServerApi;
//import com.mongodb.ServerApiVersion;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//import org.bson.Document;
//
//public class MongoDBService {
//
//    private static final String MONGO_DB_CONNECTION = "mongodb+srv://deathsquad12a:DarkMagician#28@cluster0.qhz6bx2.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
//    private static final String DATABASE_NAME = "touristapp";
//    private static final String COLLECTION_NAME = "users";
//
//    MongoClient client;
//    MongoDatabase database;
//    MongoCollection<Document> collection;
//
//    ServerApi serverApi = ServerApi.builder()
//            .version(ServerApiVersion.V1)
//            .build();
//    MongoClientSettings settings = MongoClientSettings.builder()
//            .applyConnectionString(new ConnectionString(MONGO_DB_CONNECTION))
//            .serverApi(serverApi)
//            .build();
//
//    public MongoDBService() {
//        try{
//            client = MongoClients.create(settings);
//            database = client.getDatabase(DATABASE_NAME);
//            collection = database.getCollection(COLLECTION_NAME);
//        } catch (Exception e){
//            System.out.println("Something went wrong.");
//        }
//    }
//
//    public void insertUser(String username, String email, String password) {
//        Document userDocument = new Document()
//                .append("username", username)
//                .append("email", email)
//                .append("password", password);
//
//        collection.insertOne(userDocument);
//    }
//
//    public boolean authenticateUser(String email, String password) {
//        Document query = new Document("email", email).append("password", password);
//        Document user = collection.find(query).first();
//        return user != null;
//    }
//
//    public void close() {
//        client.close();
//    }
//}

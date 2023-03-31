import com.google.firebase.database.*;
import com.MongoDB.client.*;
import org.bson.Document;


//get data from add scanner and pull data from MongoDB and Realtime Database

public class DatabaseExample {

    //initialize Firebase database reference
    private DatabaseReference mDatabase;

    //Initialive MongoDB client and database reference
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public DatabaseExample() {
        //Initialize Firebase Database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learned-skill-377010-42892.asia-southeast1.firebasedatabase.app/");

        //Initialize MongoDB database
        mongoClient= MongoClients.create("mongodb://localhost:27017");
        mongoDatabase = mongoClient.getDatabase("ecommerce_inventory_1");
    }

    // Retrieve data from Firebase Realtime Database
public void retrieveDataFromFirebase(String key) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get data from Realtime Database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getKey();
                    DataSnapshot arraySnapshot = snapshot.child("myarray");
                    for (DataSnapshot childSnapshot : arraySnapshot.getChildren()) {
                        String childKey = childSnapshot.getKey();
                        String value = childSnapshot.getValue(String.class);
                        if (key.equals(childKey)) {
                            System.out.println("User ID: " + userId + ", Key: " + childKey + ", Value: " + value);
                        };
                    };
                };
            };

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error retrieving data from Firebase database: " + databaseError.getMessage());
            };
        };
        mDatabase.addValueEventListener(postListener);
    };

    // Query data from MongoDB database
    public void queryDataFromMongo() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("ecommerce_backend_2");
        Document query = new Document("age", new Document("$gte", 18).append("$lte", 30));
        FindIterable<Document> results = collection.find(query);
        for (Document doc : results) {
            String name = doc.getString("name");
            int age = doc.getInteger("age");
            System.out.println("Name: " + name + ", Age: " + age);
        }
    }

    public static void main(String[] args) {
        DatabaseExample databaseExample = new DatabaseExample();
        databaseExample.retrieveDataFromFirebase("8292550568944");
        //databaseExample.queryDataFromMongo();
    }
}
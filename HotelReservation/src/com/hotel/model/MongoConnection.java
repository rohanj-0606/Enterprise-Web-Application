package com.hotel.model;
import com.hotel.config.*;
import com.hotel.libraries.*; 
import java.io.*;
import java.util.*;
import java.net.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoConnection{

	private static DB database;

	static{
		database = null;
	}

	private MongoConnection() { 
				MongoClient mongo = new MongoClient(Constants.mongoDbHost, Constants.mongoDbPort);
				//MongoCredential credential = MongoCredential.createCredential(Constants.mongoDbUser, Constants.mongoDatabase, Constants.mongoDbPass.toCharArray()); 
		
				database = mongo.getDB((Constants.mongoDatabase));
	}

	public static DB getConnection(){
		if(database == null){
			new MongoConnection();
		}

		return database;
	}

	public static DBCollection getMongoCollection() throws IOException{
		if(database == null){
			new MongoConnection();
		}

		return database.getCollection("ReviewCollection");
	}

	public static void addMongoDocument(String hotelId, String rating, String review) throws IOException{
        HotelModel.updateRating(Integer.parseInt(hotelId), Double.parseDouble(rating));
		DBCollection coll = getMongoCollection();

		BasicDBObject doc = new BasicDBObject();
		doc.put("id", hotelId);
		doc.put("rating", rating);
		doc.put("review", review);

		coll.insert(doc);
	}

	public static ArrayList<Rating> retrieveDocument(String hotelId) throws IOException{	
		DBCollection coll = getMongoCollection();
		ArrayList<Rating> myList = new ArrayList<>();

		BasicDBObject query = new BasicDBObject();
		query.put("id", hotelId);
		DBCursor cursor = coll.find(query);
			while (cursor.hasNext()) {
                                DBObject obj;
                                obj = cursor.next();
				myList.add( new Rating((String)obj.get("id"), (String)obj.get("rating"), (String)obj.get("review")));
			}
		

		return myList;
	}
}

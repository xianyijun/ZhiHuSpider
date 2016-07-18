package me.kuye.spider.manager;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import me.kuye.spider.dao.mongo.codec.UpvoteUserCodec;
import me.kuye.spider.dao.mongo.provider.AnswerProvider;;

public class MongoManager {
	private static Logger logger = LoggerFactory.getLogger(MongoManager.class);
	private static final MongoManager manager = new MongoManager();
	private static MongoClient mongoClient = null;
	// 配置文件配置
	private static final String host = "localhost";
	private static int port = 27017;
	private static final String ZHIHU_MONGO_DATABASE = "zhihu";

	static {
		try {
			CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
					CodecRegistries.fromCodecs(new UpvoteUserCodec()),
					CodecRegistries.fromProviders(new AnswerProvider()), MongoClient.getDefaultCodecRegistry());
			MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();
			mongoClient = new MongoClient(new ServerAddress(host, port), options);
		} catch (Exception e) {
			logger.info("mongodb start failure");
			e.printStackTrace();
		}
	}

	public static MongoManager getInstance() {
		return manager;
	}

	public MongoDatabase getDataBase() {
		try {
			return mongoClient.getDatabase(ZHIHU_MONGO_DATABASE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public MongoCollection<Document> getCollection(String collectionName) {
		try {
			return getDataBase().getCollection(collectionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean insertOne(String collectionName, Document document) {
		try {
			getCollection(collectionName).insertOne(document);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertMany(String collectionName, List<Document> documentList) {
		try {
			getCollection(collectionName).insertMany(documentList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteOne(String collectionName, Bson filter) {
		try {
			getCollection(collectionName).deleteOne(filter);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteMany(String collectionName, Bson filter) {
		try {
			getCollection(collectionName).deleteMany(filter);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateOne(String collectionName, Bson filter, Bson update) {
		try {
			UpdateResult result = getCollection(collectionName).updateOne(filter, new Document("$set", update));
			return result.getModifiedCount() != 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateMany(String collectionName, Bson filter, Bson update) {
		try {
			UpdateResult result = getCollection(collectionName).updateMany(filter, update);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Document> findMany(String collectionName) {
		List<Document> list = new ArrayList<>();
		for (Document doc : getCollection(collectionName).find()) {
			list.add(doc);
		}
		return list;
	}

	public List<Document> findMany(String collectionName, Bson filter) {
		List<Document> list = new ArrayList<>();
		for (Document doc : getCollection(collectionName).find(filter)) {
			list.add(doc);
		}
		return list;
	}

	public List<Document> findMany(String collectionName, Bson filter, int limit) {
		List<Document> list = new ArrayList<>();
		for (Document doc : getCollection(collectionName).find(filter).limit(limit)) {
			list.add(doc);
		}
		return list;
	}

	public List<Document> findMany(String collectionName, int limit) {
		List<Document> list = new ArrayList<>();
		for (Document doc : getCollection(collectionName).find().limit(limit)) {
			list.add(doc);
		}
		return list;
	}

	public Document findOne(String collectionName) {
		return getCollection(collectionName).find().first();
	}

	public Document finyOne(String collectionName, Bson filter) {
		return getCollection(collectionName).find(filter).first();
	}

	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}
}

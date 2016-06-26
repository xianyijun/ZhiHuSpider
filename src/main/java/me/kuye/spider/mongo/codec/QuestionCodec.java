package me.kuye.spider.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import me.kuye.spider.entity.Question;

public class QuestionCodec implements Codec<Question> {

	@Override
	public void encode(BsonWriter writer, Question question, EncoderContext encoderContext) {
		writer.writeStartDocument();
	}

	@Override
	public Class<Question> getEncoderClass() {
		return null;
	}

	@Override
	public Question decode(BsonReader reader, DecoderContext decoderContext) {
		return null;
	}

	
}

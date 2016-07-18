package me.kuye.spider.dao.mongo.provider;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import me.kuye.spider.dao.mongo.codec.AnswerCodec;
import me.kuye.spider.entity.Answer;

public class AnswerProvider implements CodecProvider {
	@Override
	public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
		if (clazz == Answer.class) {
			System.out.println("the answer codec Provider");
			return (Codec<T>) new AnswerCodec(registry);
		}
		return null;
	}

}

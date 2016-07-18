package me.kuye.spider.dao.mongo.codec;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import me.kuye.spider.entity.Answer;
import me.kuye.spider.entity.UpVoteUser;

public class AnswerCodec implements Codec<Answer> {
	private final CodecRegistry codecRegistry;

	public AnswerCodec(final CodecRegistry codecRegistry) {
		this.codecRegistry = codecRegistry;
	}

	@Override
	public void encode(BsonWriter writer, Answer answer, EncoderContext encoderContext) {
		writer.writeStartDocument();
		writer.writeName("url");
		writer.writeString(answer.getUrl());
		writer.writeName("author");
		writer.writeString(answer.getAuthor());
		writer.writeName("content");
		writer.writeString(answer.getContent());
		writer.writeName("dataAid");
		writer.writeString(answer.getDataAid());
		writer.writeName("upvote");
		writer.writeString(answer.getUpvote());
		writer.writeStartArray("upvoteUserList");
		for (UpVoteUser upVoteUser : answer.getUpvoteUserList()) {
			Codec<UpVoteUser> codec = codecRegistry.get(UpVoteUser.class);
			encoderContext.encodeWithChildContext(codec, writer, upVoteUser);
		}
		writer.writeEndArray();
		writer.writeEndDocument();
	}

	@Override
	public Answer decode(BsonReader reader, DecoderContext decoderContext) {
		reader.readStartDocument();
		reader.readName("url");
		String url = reader.readString();
		Answer answer = new Answer(url);
		reader.readName("author");
		answer.setAuthor(reader.readString());
		reader.readName("content");
		answer.setContent(reader.readString());
		reader.readName("dataAid");
		answer.setDataAid(reader.readString());
		reader.readName("upvote");
		answer.setUpvote(reader.readString());
		List<UpVoteUser> upvoteUserList = new ArrayList<>();
		Codec<UpVoteUser> upVoteUserCodec = codecRegistry.get(UpVoteUser.class);
		reader.readStartArray();
		while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
			upvoteUserList.add(upVoteUserCodec.decode(reader, decoderContext));
		}
		reader.readEndArray();
		answer.setUpvoteUserList(upvoteUserList);
		reader.readEndDocument();
		return answer;
	}

	@Override
	public Class<Answer> getEncoderClass() {
		return Answer.class;
	}

}

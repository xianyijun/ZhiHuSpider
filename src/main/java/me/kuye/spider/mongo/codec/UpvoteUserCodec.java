package me.kuye.spider.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import me.kuye.spider.vo.UpVoteUser;

public class UpvoteUserCodec implements Codec<UpVoteUser> {

	@Override
	public void encode(BsonWriter writer, UpVoteUser upVoteUser, EncoderContext encoderContext) {
		writer.writeStartDocument();
		writer.writeString("name", upVoteUser.getName());
		writer.writeString("bio", upVoteUser.getBio());
		writer.writeString("avatar", upVoteUser.getAvatar());
		writer.writeString("answers", upVoteUser.getAnswers());
		writer.writeString("asks", upVoteUser.getAsks());
		writer.writeString("agree", upVoteUser.getAgree());
		writer.writeString("thanks", upVoteUser.getThanks());
		writer.writeEndDocument();
	}

	@Override
	public Class<UpVoteUser> getEncoderClass() {
		return UpVoteUser.class;
	}

	@Override
	public UpVoteUser decode(BsonReader reader, DecoderContext decoderContext) {
		UpVoteUser upVoteUser = new UpVoteUser();
		reader.readStartDocument();
		upVoteUser.setName(reader.readString("name"));
		upVoteUser.setBio(reader.readString("bio"));
		upVoteUser.setAvatar(reader.readString("avatar"));
		upVoteUser.setAnswers(reader.readString("answers"));
		upVoteUser.setAsks(reader.readString("asks"));
		upVoteUser.setAgree(reader.readString("agree"));
		upVoteUser.setThanks(reader.readString("thanks"));
		return upVoteUser;
	}

}

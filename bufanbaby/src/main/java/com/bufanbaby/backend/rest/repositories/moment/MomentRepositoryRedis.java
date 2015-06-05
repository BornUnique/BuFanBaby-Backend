package com.bufanbaby.backend.rest.repositories.moment;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import com.bufanbaby.backend.rest.domain.moment.Content;
import com.bufanbaby.backend.rest.domain.moment.FileMetadata;
import com.bufanbaby.backend.rest.domain.moment.Moment;
import com.bufanbaby.backend.rest.domain.moment.ShareWith;
import com.bufanbaby.backend.rest.repositories.Jackson2HashMapper;
import com.bufanbaby.backend.rest.repositories.Keys;

@Repository
public class MomentRepositoryRedis implements MomentRepository {

	private final StringRedisTemplate stringTemplate;

	private final HashOperations<String, String, String> hashOps;
	private final ListOperations<String, String> listOps;
	private final SetOperations<String, String> setOps;

	private final RedisAtomicLong nextMomentIdGenerator;
	private final RedisAtomicLong nextImageIdGenerator;

	private final HashMapper<Content, String, String> momentHashMapper;
	private final HashMapper<FileMetadata, String, String> fileMetadataHashMapper;

	@Autowired
	public MomentRepositoryRedis(StringRedisTemplate stringTemplate) {
		this.stringTemplate = stringTemplate;

		this.nextMomentIdGenerator = new RedisAtomicLong(Keys.nextMomentIdKey(),
				stringTemplate.getConnectionFactory());
		this.nextImageIdGenerator = new RedisAtomicLong(Keys.nextImageIdKey(),
				stringTemplate.getConnectionFactory());

		this.hashOps = stringTemplate.opsForHash();
		this.listOps = stringTemplate.opsForList();
		this.setOps = stringTemplate.opsForSet();

		this.momentHashMapper = new DecoratingStringHashMapper<Content>(
				new Jackson2HashMapper<Content>(Content.class));
		this.fileMetadataHashMapper = new DecoratingStringHashMapper<FileMetadata>(
				new Jackson2HashMapper<FileMetadata>(FileMetadata.class));
	}

	@Override
	public long create(Moment moment) {
		long momentId = nextMomentIdGenerator.incrementAndGet();

		// get next moment id
		// save the moment content
		Map<String, String> content = momentHashMapper.toHash(moment.getContent());

		// hash
		// moments:{momentId} -> {feeling:"", timeCreated:""}
		hashOps.putAll(Keys.momentIdKey(momentId), content);

		long userId = moment.getUserId();
		String strMomentId = String.valueOf(momentId);

		// list
		// users:{userId}:moments: {momentId, momentId}
		listOps.leftPush(Keys.userIdMomentsKey(userId), strMomentId);

		// user's home or dashboard
		// list
		// users:{userId}:timeline: {publicMomentId, friendMomentId,
		// followingMomentIds(friends+business), others' public momentId, pushed
		// information}
		listOps.leftPush(Keys.userIdTimelineKey(userId), strMomentId);

		// Global home or dashboard
		// list
		// global:timeline -> {all public moments or business information}
		if (moment.getContent().getShareWith() == ShareWith.PUBLIC) {
			listOps.leftPush(Keys.globalTimelineKey(), strMomentId);
		} else if (moment.getContent().getShareWith() == ShareWith.FRIENDS_CIRCLE) {
			// put this entry into your friend timeline
			// TODO: how to push
		}

		Set<String> tags = moment.getTags();
		setOps.add(Keys.momentIdTagsKey(momentId), tags.toArray(new String[tags.size()]));

		// save file list metadata
		List<FileMetadata> files = moment.getFileMetadatas();
		for (FileMetadata fileMetadata : files) {
			long imageId = nextImageIdGenerator.incrementAndGet();

			// image metadata
			// hash
			// images:{imageId} -> {name:"", timeCreated:""}
			hashOps.putAll(Keys.imagesIdKey(imageId), fileMetadataHashMapper.toHash(fileMetadata));

			// moment's images
			// list
			// moments:{momentId}:images - {imageId, imageId}
			listOps.leftPush(Keys.momentsIdImagessKey(momentId), String.valueOf(imageId));
		}

		return momentId;
	}
}

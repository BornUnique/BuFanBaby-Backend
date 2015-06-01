package com.bufanbaby.backend.rest.repositories.moment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.bufanbaby.backend.rest.domain.moment.Moment;

@Repository
public class MomentRepositoryRedis implements MomentRepository {

	private final StringRedisTemplate stringTemplate;

	@Autowired
	public MomentRepositoryRedis(StringRedisTemplate stringTemplate) {
		this.stringTemplate = stringTemplate;
	}

	@Override
	public long create(Moment moment) {

		return 0;
	}

}

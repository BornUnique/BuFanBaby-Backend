package com.bufanbaby.backend.rest.repositories;

import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.domain.Moment;

@Component
public class MomentRepositoryRedis implements MomentRepository {

	@Override
	public long create(Moment moment) {
		return 0;
	}

}

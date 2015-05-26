package com.bufanbaby.backend.rest.repositories.moment;

import org.springframework.stereotype.Repository;

import com.bufanbaby.backend.rest.domain.moment.Moment;

@Repository
public class MomentRepositoryRedis implements MomentRepository {

	// TODO:handle DAOAccessException for Redis
	// using a generic exception 500 for it???
	@Override
	public long create(Moment moment) {
		return 0;
	}

}

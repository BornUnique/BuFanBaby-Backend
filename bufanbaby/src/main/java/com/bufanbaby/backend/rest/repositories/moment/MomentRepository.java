package com.bufanbaby.backend.rest.repositories.moment;

import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.domain.moment.Moment;

@Component
public interface MomentRepository {

	long create(Moment moment);

}

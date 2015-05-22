package com.bufanbaby.backend.rest.repositories;

import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.domain.Moment;

@Component
public interface MomentRepository {

	long create(Moment moment);

}

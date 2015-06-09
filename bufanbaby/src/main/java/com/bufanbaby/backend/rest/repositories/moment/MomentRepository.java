package com.bufanbaby.backend.rest.repositories.moment;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bufanbaby.backend.rest.domain.moment.Moment;

@Component
public interface MomentRepository {

	long create(Moment moment);

	List<Moment> getlatestMoments(long userId, int size);

}

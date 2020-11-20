package org.telit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.telit.model.Consultant;

public interface IConsultantRepository extends JpaRepository<Consultant, Long> {
	
	List<Consultant> findByType(String type);

	@Transactional 
	List<Consultant> deleteByNameAndLastName(String name, String lastName);

	List<Consultant> findByActiveTrueAndPayRateLessThanAndWorkedHoursLessThan(Double i, Double j);
}

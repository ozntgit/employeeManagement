package org.telit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.telit.model.Regular;

public interface IRegularRepository extends JpaRepository<Regular, Long> {
	
	List<Regular> findByType(String type);

	@Transactional 
	List<Regular> deleteByNameAndLastName(String name, String lastName);
}

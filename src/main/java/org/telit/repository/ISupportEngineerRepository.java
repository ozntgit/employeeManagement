package org.telit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.telit.model.SupportEngineer;

public interface ISupportEngineerRepository extends JpaRepository<SupportEngineer, Long> {
	
	List<SupportEngineer> findByType(String type);

	@Transactional 
	List<SupportEngineer> deleteByNameAndLastName(String name, String lastName);
}

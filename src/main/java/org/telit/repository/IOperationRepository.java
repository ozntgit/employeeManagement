package org.telit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.telit.model.Operation;

public interface IOperationRepository extends JpaRepository<Operation, Long> {
	
	List<Operation> findByType(String type);

	@Transactional 
	List<Operation> deleteByNameAndLastName(String name, String lastName);
}

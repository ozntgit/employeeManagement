package org.telit.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telit.model.Operation;
import org.telit.repository.IOperationRepository;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    IOperationRepository iOperationRepository;
    
    @RequestMapping("/create")
	public void create(@RequestBody  Operation o) {
		iOperationRepository.save(o);
	}
	
	@RequestMapping("/update")
	public void update(@RequestBody  Operation o) {
		iOperationRepository.save(o);
	}

	@RequestMapping("/get/{id}")
	public Optional< Operation> get(@PathVariable Long id) {
		return iOperationRepository.findById(id);
	}

	@RequestMapping("/findbytype")
	public List< Operation> findByType(String type) {
		return iOperationRepository.findByType(type);
	}

	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		iOperationRepository.deleteById(id);
	}

	@RequestMapping("/deletebyfullname")
	public void delete(String name, String lastName) {
		iOperationRepository.deleteByNameAndLastName(name, lastName);
	}
    

}

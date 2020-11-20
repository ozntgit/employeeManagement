package org.telit.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telit.model.Regular;
import org.telit.repository.IRegularRepository;

@RestController
@RequestMapping("/regular")
public class RegularController {

    @Autowired
    IRegularRepository iRegularRepository;
    
    @RequestMapping("/create")
	public void create(@RequestBody  Regular o) {
		iRegularRepository.save(o);
	}
	
	@RequestMapping("/update")
	public void update(@RequestBody  Regular o) {
		iRegularRepository.save(o);
	}

	@RequestMapping("/get/{id}")
	public Optional< Regular> get(@PathVariable Long id) {
		return iRegularRepository.findById(id);
	}

	@RequestMapping("/findbytype")
	public List< Regular> findByType(String type) {
		return iRegularRepository.findByType(type);
	}

	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		iRegularRepository.deleteById(id);
	}

	@RequestMapping("/deletebyfullname")
	public void delete(String name, String lastName) {
		iRegularRepository.deleteByNameAndLastName(name, lastName);
	}
}

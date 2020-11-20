package org.telit.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telit.model.SupportEngineer;
import org.telit.repository.ISupportEngineerRepository;

@RestController
@RequestMapping("/supportengineer")
public class SupportEngineerController {

    @Autowired
    ISupportEngineerRepository iSupportEngineerRepository;
    
    @RequestMapping("/create")
	public void create(@RequestBody  SupportEngineer o) {
		iSupportEngineerRepository.save(o);
	}
	
	@RequestMapping("/update")
	public void update(@RequestBody  SupportEngineer o) {
		iSupportEngineerRepository.save(o);
	}

	@RequestMapping("/get/{id}")
	public Optional< SupportEngineer> get(@PathVariable Long id) {
		return iSupportEngineerRepository.findById(id);
	}

	@RequestMapping("/findbytype")
	public List< SupportEngineer> findByType(String type) {
		return iSupportEngineerRepository.findByType(type);
	}

	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		iSupportEngineerRepository.deleteById(id);
	}

	@RequestMapping("/deletebyfullname")
	public void delete(String name, String lastName) {
		iSupportEngineerRepository.deleteByNameAndLastName(name, lastName);
	}
    

}

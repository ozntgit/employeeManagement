package org.telit.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telit.model.Consultant;
import org.telit.repository.IConsultantRepository;

@RestController
@RequestMapping("/consultant")
public class ConsultantController {

	@Autowired
	IConsultantRepository iConsultantRepository;
	
	@RequestMapping("/create")
	public void create(@RequestBody Consultant c) {
		iConsultantRepository.save(c);
	}
	
	@RequestMapping("/update")
	public void update(@RequestBody Consultant c) {
		iConsultantRepository.save(c);
	}

	@RequestMapping("/get/{id}")
	public Optional<Consultant> get(@PathVariable Long id) {
		return iConsultantRepository.findById(id);
	}

	@RequestMapping("/findbytype")
	public List<Consultant> findByType(String type) {
		return iConsultantRepository.findByType(type);
	}

	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		iConsultantRepository.deleteById(id);
	}

	@RequestMapping("/deletebyfullname")
	public void delete(String name, String lastName) {
		iConsultantRepository.deleteByNameAndLastName(name, lastName);
	}

	@RequestMapping("/customsearch")
	public List<Consultant> find(Double payRate, Double workedHours) {
		//fixed solution. could be implemented via a named @Query for more readability/reuse
		return iConsultantRepository.findByActiveTrueAndPayRateLessThanAndWorkedHoursLessThan(payRate, workedHours);
	}

}

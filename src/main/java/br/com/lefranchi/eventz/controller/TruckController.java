package br.com.lefranchi.eventz.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lefranchi.eventz.domain.Truck;
import br.com.lefranchi.eventz.repository.TruckRepository;

@Controller
public class TruckController {

	@Autowired
	TruckRepository truckRepository;

	@RequestMapping(value = "/trucks", method = RequestMethod.GET)
	public String list(final Model model) {
		model.addAttribute("trucks", truckRepository.findAll());
		return "trucks";
	}

	@RequestMapping("truck/{id}")
	public String showTruck(@PathVariable final Long id, final Model model) {
		model.addAttribute("truck", truckRepository.findOne(id));
		return "truckshow";
	}

	@RequestMapping("truck/edit/{id}")
	public String edit(@PathVariable final Long id, final Model model) {
		model.addAttribute("truck", truckRepository.findOne(id));
		return "truckform";
	}

	@RequestMapping("truck/new")
	public String newTruck(final Model model) {
		model.addAttribute("truck", new Truck());
		return "truckform";
	}

	@RequestMapping(value = "truck", method = RequestMethod.POST)
	public String saveTruck(@Valid final Truck truck, final BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "truckform";
		}

		truckRepository.save(truck);
		return "redirect:/truck/" + truck.getId();
	}

	@RequestMapping("truck/delete/{id}")
	public String delete(@PathVariable final Long id) {
		truckRepository.delete(id);
		return "redirect:/trucks";
	}

}

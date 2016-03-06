package br.com.ablebit.eventz.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.ablebit.eventz.domain.Nf;
import br.com.ablebit.eventz.domain.Truck;
import br.com.ablebit.eventz.repository.NfRepository;
import br.com.ablebit.eventz.repository.TruckRepository;

@Controller
public class NfController {

	@Autowired
	NfRepository nfRepository;

	@Autowired
	TruckRepository truckRepository;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@ModelAttribute("allTrucks")
	public List<Truck> populateTrucks() {
		return (List<Truck>) truckRepository.findAll();
	}

	@RequestMapping(value = "/nfs", method = RequestMethod.GET)
	public String list(final Model model) {
		model.addAttribute("nfs", nfRepository.findAllByOrderByDataDesc());
		return "nfs";
	}

	@RequestMapping("nf/{id}")
	public String showNf(@PathVariable final Long id, final Model model) {
		model.addAttribute("nf", nfRepository.findOne(id));
		return "nfshow";
	}

	@RequestMapping("nf/edit/{id}")
	public String edit(@PathVariable final Long id, final Model model) {
		model.addAttribute("nf", nfRepository.findOne(id));
		return "nfform";
	}

	@RequestMapping("nf/new")
	public String newNf(final Model model) {
		model.addAttribute("nf", new Nf());
		return "nfform";
	}

	@RequestMapping(value = "nf", method = RequestMethod.POST)
	public String saveNf(@Valid final Nf nf, final BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "nfform";
		}

		nfRepository.save(nf);
		return "redirect:/nf/" + nf.getId();
	}

	@RequestMapping("nf/delete/{id}")
	public String delete(@PathVariable final Long id) {
		nfRepository.delete(id);
		return "redirect:/nfs";
	}

}

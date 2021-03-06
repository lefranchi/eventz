package br.com.ablebit.eventz.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.ablebit.eventz.domain.Producer;
import br.com.ablebit.eventz.repository.ProducerRepository;
import br.com.ablebit.eventz.service.ProducerService;

@Service
@Validated
public class ProducerServiceImpl implements ProducerService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServiceImpl.class);

	private final ProducerRepository repository;

	@Inject
	public ProducerServiceImpl(final ProducerRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Producer> findAll() {
		return repository.findAll();
	}

}

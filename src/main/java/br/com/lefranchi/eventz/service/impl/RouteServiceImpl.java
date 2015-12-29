package br.com.lefranchi.eventz.service.impl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.service.ProducerService;
import br.com.lefranchi.eventz.service.RouteService;

@Service
@Validated
public class RouteServiceImpl implements RouteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);

	@Autowired
	ProducerService producerService;

	@Override
	@Transactional(readOnly = true)
	public void loadRoutes() {

		producerService.findAll().forEach((producer) -> loadRoute(producer));

	}

	@Override
	@Transactional(readOnly = true)
	public void loadRoute(@NotNull @Valid final Producer producer) {

		LOGGER.info(String.format("Carregando rota para %s", producer));

		// TODO: Implememntar carga de rota. Criar rota de leitura interna e
		// processamento de Regras para o produtor.

	}

}

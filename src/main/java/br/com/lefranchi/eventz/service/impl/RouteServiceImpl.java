package br.com.lefranchi.eventz.service.impl;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.route.ExternalProducerRouteBuilder;
import br.com.lefranchi.eventz.route.InternalConsumerRouteBuilder;
import br.com.lefranchi.eventz.service.ProducerService;
import br.com.lefranchi.eventz.service.RouteService;

@Service
@Validated
public class RouteServiceImpl implements RouteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);

	@Autowired
	ProducerService producerService;

	@Autowired
	CamelContext camelContext;

	@Override
	@PostConstruct
	@Transactional(readOnly = true)
	public void loadRoutes() {

		LOGGER.debug("Carregando Rotas...");

		producerService.findAll().forEach((producer) -> {
			try {
				loadInternalRoute(producer);
				loadExternalRoute(producer);
			} catch (final Exception e) {
				LOGGER.error(String.format("Erro na carga da rota para o produtor %s", producer), e);
			}
		});

	}

	@Override
	@Transactional(readOnly = true)
	public void loadInternalRoute(@NotNull @Valid final Producer producer) throws Exception {

		LOGGER.info(String.format("Carregando rota interna para %s", producer));

		final InternalConsumerRouteBuilder internalConsumerRouteBuilder = new InternalConsumerRouteBuilder(producer);

		camelContext.addRoutes(internalConsumerRouteBuilder);

	}

	@Override
	@Transactional(readOnly = true)
	public void loadExternalRoute(@NotNull @Valid final Producer producer) throws Exception {

		LOGGER.info(String.format("Carregando rota externa para %s", producer));

		if (producer.getInputMethod() != null
				|| (producer.getProducerGroup() != null && producer.getProducerGroup().getInputMethod() != null)) {

			final ExternalProducerRouteBuilder externalProducerRouteBuilder = new ExternalProducerRouteBuilder(
					producer);

			camelContext.addRoutes(externalProducerRouteBuilder);

		}

	}

}

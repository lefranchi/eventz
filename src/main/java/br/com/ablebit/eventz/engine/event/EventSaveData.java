package br.com.ablebit.eventz.engine.event;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ablebit.eventz.domain.ProducerData;
import br.com.ablebit.eventz.engine.RuleProcessorVO;
import br.com.ablebit.eventz.repository.ProducerDataRepository;
import br.com.ablebit.eventz.util.DataValueUtils;

@Component
public class EventSaveData implements Processor {

	/**
	 * Logger.
	 */
	final static Logger LOGGER = LoggerFactory.getLogger(EventSaveData.class);

	@Autowired
	ProducerDataRepository producerDataReporitory;

	@Override
	public void process(final Exchange exchange) throws Exception {

		final ProducerData data = exchange.getIn().getBody(RuleProcessorVO.class).getData();

		DataValueUtils.extractDataValues(data);

		producerDataReporitory.save(data);

		LOGGER.debug(String.format("Dados %s do produtor %s salvos.", data, data.getProducer()));

	}

}

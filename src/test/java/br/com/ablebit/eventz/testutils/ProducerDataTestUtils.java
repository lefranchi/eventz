package br.com.ablebit.eventz.testutils;

import br.com.ablebit.eventz.domain.Producer;
import br.com.ablebit.eventz.domain.ProducerData;

public class ProducerDataTestUtils {

	public final static String PRODUCER_DATA = "88745;LEANDRO FRANCHI;1979;0;25.4";

	public static ProducerData newProducerData(final Producer producer) {

		final ProducerData producerData = new ProducerData();
		producerData.setProducer(producer);
		producerData.setData(PRODUCER_DATA);

		return producerData;

	}

}

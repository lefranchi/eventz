package br.com.lefranchi.eventz.testutils;

import br.com.lefranchi.eventz.domain.Producer;

public class ProducerTestUtils {

	public final static String PRODUCER_NAME = "Producer - 01";

	public static Producer newProducer() {

		final Producer producer = new Producer();
		producer.setName(PRODUCER_NAME);

		return producer;

	}

}

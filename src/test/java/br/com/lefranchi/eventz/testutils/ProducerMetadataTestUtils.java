package br.com.lefranchi.eventz.testutils;

import java.util.TreeSet;

import br.com.lefranchi.eventz.domain.DataFieldMetadata;
import br.com.lefranchi.eventz.domain.FieldDataType;
import br.com.lefranchi.eventz.domain.ProducerDataType;
import br.com.lefranchi.eventz.domain.ProducerMetadata;

public class ProducerMetadataTestUtils {

	public final static String PRODUCER_METADATA_SAMPLE_DATA = "88745;LEANDRO FRANCHI;1979;0;25.4;3";

	public static ProducerMetadata newProducerMetadata() {

		final ProducerMetadata metadata = new ProducerMetadata();
		metadata.setDataType(ProducerDataType.DELIMITED);
		metadata.setSampleData(PRODUCER_METADATA_SAMPLE_DATA);
		metadata.setFields(new TreeSet<>());

		final DataFieldMetadata fieldId = new DataFieldMetadata();
		fieldId.setName("id");
		fieldId.setType(FieldDataType.NUMBER);
		fieldId.setOrder(1);
		fieldId.setProducerMetadata(metadata);
		metadata.getFields().add(fieldId);

		final DataFieldMetadata fieldName = new DataFieldMetadata();
		fieldName.setName("name");
		fieldName.setType(FieldDataType.STRING);
		fieldName.setOrder(2);
		fieldName.setProducerMetadata(metadata);
		metadata.getFields().add(fieldName);

		final DataFieldMetadata fieldYear = new DataFieldMetadata();
		fieldYear.setName("year");
		fieldYear.setType(FieldDataType.NUMBER);
		fieldYear.setOrder(3);
		fieldYear.setProducerMetadata(metadata);
		metadata.getFields().add(fieldYear);

		final DataFieldMetadata fieldActive = new DataFieldMetadata();
		fieldActive.setName("active");
		fieldActive.setType(FieldDataType.BOOLEAN);
		fieldActive.setOrder(4);
		fieldActive.setProducerMetadata(metadata);
		metadata.getFields().add(fieldActive);

		final DataFieldMetadata fieldContent = new DataFieldMetadata();
		fieldContent.setName("content");
		fieldContent.setType(FieldDataType.NUMBER);
		fieldContent.setOrder(5);
		fieldContent.setProducerMetadata(metadata);
		metadata.getFields().add(fieldContent);

		return metadata;

	}

}

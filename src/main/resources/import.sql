------------------------------------------------------------------------------------------------------------
--
-- DADOS DE TESTES
--
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Eventos
------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`event` (`name`, `processor`) VALUES ('Log', 'eventLog');

INSERT INTO `eventz`.`event` (`name`, `processor`) VALUES ('Save Data', 'eventSaveData');

INSERT INTO `eventz`.`event` (`name`, `processor`) VALUES ('Alarm', 'eventAlarm');
INSERT INTO `eventz`.`event_property`(`mandatory`,`name`,`type`,`event`) VALUES (1,'alarmLevel','ALARM_LEVEL',3);

INSERT INTO `eventz`.`event` (`name`, `processor`) VALUES ('SMS', 'eventSMS');
INSERT INTO `eventz`.`event_property`(`mandatory`,`name`,`type`,`event`) VALUES (1,'numeroChip','NR_CHIP',4);
INSERT INTO `eventz`.`event_property`(`mandatory`,`name`,`type`,`event`) VALUES (1,'formatoMensagem','MESSAGE_FORMAT',4);
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Metodos de Entrada
------------------------------------------------------------------------------------------------------------

-- HTTP
INSERT INTO `eventz`.`input_method`(`component_name`,`description`,`name`) VALUES ('jetty:http://0.0.0.0:2121/eventz/VAR','Leitor de mensagens usado no simulador.','Receptor HTTP.');

-- ARQUIVO
-- INSERT INTO `eventz`.`input_method`(`component_name`,`description`,`name`) VALUES ('file:/VAR','Leitor de arquivos.','File TMP');
-- INSERT INTO `eventz`.`input_method_properties`(`input_method`,`properties`) VALUES (1,'delay');
-- INSERT INTO `eventz`.`input_method_properties`(`input_method`,`properties`) VALUES (1,'delete');

--SQL
INSERT INTO `eventz`.`input_method`(`component_name`,`description`,`name`) VALUES ('sql:VAR','Leitor de objetos no banco de dados.','Receptor de Banco de dados.');

INSERT INTO `eventz`.`producer_input_method`(`input_method`) VALUES (1);
INSERT INTO `eventz`.`producer_input_method`(`input_method`) VALUES (2);
-- INSERT INTO `eventz`.`producer_input_method_properties`(`producer_input_method`,`value`,`property`) VALUES (1,'6000','delay');
-- INSERT INTO `eventz`.`producer_input_method_properties`(`producer_input_method`,`value`,`property`) VALUES (1,'false','delete');

------------------------------------------------------------------------------------------------------------
-- Metadados de Produtores
------------------------------------------------------------------------------------------------------------
-- Metadados para dados delimitados.
INSERT INTO `eventz`.`producer_metadata`(`name`, `description`, `data_type`,`sample_data`) VALUES ('TRR', 'Dados recebidos de TRR', 'DELIMITED', '88745;LEANDRO FRANCHI;1979;0;25.4');
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('id',1,'NUMBER',1);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('name',2,'STRING',1);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('year',3,'NUMBER',1);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('active',4,'BOOLEAN',1);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('content',5,'NUMBER',1);

-- Metadados para Banco de Dados
INSERT INTO `eventz`.`producer_metadata`(`name`, `description`, `data_type`,`sample_data`) VALUES ('NF', 'Dados de notas fiscais e volumes', 'DB', null);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('id',1,'NUMBER',2);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('id_caminhao',2,'NUMBER',2);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('numero_lote',3,'NUMBER',2);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('numero_nf',4,'NUMBER',2);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('volume',5,'NUMBER',2);
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Grupo de Produtores
------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`producer_group`(`name`, `input_method`) VALUES ('Caminhões', 1);

-- On Exception deve ser logado.
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (1);
INSERT INTO `eventz`.`producer_group_events_on_exception`(`producer_group`,`events_on_exception`) VALUES (1,1);

-- Toda execução deve ser salva.
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (2);
INSERT INTO `eventz`.`producer_group_events_on_always`(`producer_group`,`events_on_always`)VALUES(1,2);

INSERT INTO `eventz`.`producer_group`(`name`, `input_method`) VALUES ('NFs', 2);

-- On Exception deve ser logado.
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (3);
INSERT INTO `eventz`.`producer_group_events_on_exception`(`producer_group`,`events_on_exception`) VALUES (2,1);

-- Enviar o SMS sempre.
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (4);
INSERT INTO `eventz`.`producer_group_events_on_always`(`producer_group`,`events_on_always`) VALUES (2,4);

------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Produtores
------------------------------------------------------------------------------------------------------------
-- Caminhoes
INSERT INTO `eventz`.`producer`(`name`, `metadata`,`producer_group`) VALUES ('C1', 1, 1);
INSERT INTO `eventz`.`producer`(`name`, `metadata`,`producer_group`) VALUES ('C2', 1, 1);
INSERT INTO `eventz`.`producer`(`name`, `metadata`,`producer_group`) VALUES ('C3', 1, 1);

-- Notas Fiscais
INSERT INTO `eventz`.`producer`(`name`, `metadata`,`producer_group`) VALUES ('NFs', 2, 2);
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Tipos de Alarmes
------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`alarm_level`(`color`,`image`,`name`) VALUES('blue','info.png','Informação');
INSERT INTO `eventz`.`alarm_level`(`color`,`image`,`name`) VALUES('yellow','warning.png','Atenção');
INSERT INTO `eventz`.`alarm_level`(`color`,`image`,`name`) VALUES('red','alert.png','Alerta');
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Regras
------------------------------------------------------------------------------------------------------------
-- Gerar alarme de warning quando conteudo estiver entre 2 e 4.
INSERT INTO `eventz`.`rule`(`formula`,`name`,`type`,`producer`,`producer_group`) VALUES ('content > 2 and content <= 4','TRR - Conteudo em Atenção','JEXL',NULL,1);
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (3);
INSERT INTO `eventz`.`rule_events_on_true`(`rule`,`events_on_true`) VALUES (1, 3);
INSERT INTO `eventz`.`event_to_process_properties`(`event_to_process`,`value`,`properties_key`) VALUES (3, '2', 1);

-- Gerar alarme de alerta quando conteudo for menor que 2.
INSERT INTO `eventz`.`rule`(`formula`,`name`,`type`,`producer`,`producer_group`) VALUES ('content <= 2','TRR - Conteudo em Alerta','JEXL',NULL,1);
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (3);
INSERT INTO `eventz`.`rule_events_on_true`(`rule`,`events_on_true`) VALUES (2, 4);
INSERT INTO `eventz`.`event_to_process_properties`(`event_to_process`,`value`,`properties_key`) VALUES (4, '3', 1);
------------------------------------------------------------------------------------------------------------


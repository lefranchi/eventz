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
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Metadados de Produtores
------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`producer_metadata`(`name`, `description`, `data_type`,`sample_data`) VALUES ('Bombas', 'Dados recebidos de bombas do tipo TRE34', 'DELIMITED', '88745;LEANDRO FRANCHI;1979;0;25.4');

INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('id',1,'NUMBER',1);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('name',2,'STRING',1);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('year',3,'NUMBER',1);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('active',4,'BOOLEAN',1);
INSERT INTO `eventz`.`data_field_metadata` (`name`,`field_order`,`type`,`producer_metadata_id`) VALUES ('content',5,'NUMBER',1);
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Grupo de Produtores
------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`producer_group`(`name`) VALUES ('BOMBAS');
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
-- Produtores
------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`producer`(`name`, `metadata`,`producer_group`) VALUES ('B1', 1, 1);
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (1);
INSERT INTO `eventz`.`producer_events_on_exception`(`producer`,`events_on_exception`) VALUES (1,1);

INSERT INTO `eventz`.`producer`(`name`, `metadata`,`producer_group`) VALUES ('B2', 1, 1);
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (1);
INSERT INTO `eventz`.`producer_events_on_exception`(`producer`,`events_on_exception`) VALUES (2,2);

INSERT INTO `eventz`.`producer`(`name`, `metadata`,`producer_group`) VALUES ('B3', 1, 1);
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (1);
INSERT INTO `eventz`.`producer_events_on_exception`(`producer`,`events_on_exception`) VALUES (3,3);
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
INSERT INTO `eventz`.`rule`(`formula`,`name`,`type`,`producer`,`producer_group`) VALUES ('content > 2 and content <= 4','BOMBA - Conteudo em Atenção','JEXL',NULL,1);
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (3);
INSERT INTO `eventz`.`rule_events_on_true`(`rule`,`events_on_true`) VALUES (1, 4);
INSERT INTO `eventz`.`event_to_process_properties`(`event_to_process`,`value`,`properties_key`) VALUES (4, '2', 1);

-- Gerar alarme de alerta quando conteudo for menor que 2.
INSERT INTO `eventz`.`rule`(`formula`,`name`,`type`,`producer`,`producer_group`) VALUES ('content <= 2','BOMBA - Conteudo em Alerta','JEXL',NULL,1);
INSERT INTO `eventz`.`event_to_process`(`event`) VALUES (3);
INSERT INTO `eventz`.`rule_events_on_true`(`rule`,`events_on_true`) VALUES (1, 5);
INSERT INTO `eventz`.`event_to_process_properties`(`event_to_process`,`value`,`properties_key`) VALUES (5, '3', 1);


-- TODO: FINALIZAR.

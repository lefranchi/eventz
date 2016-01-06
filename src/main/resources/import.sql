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
-- Produtor B1
------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`producer`(`name`, `metadata`) VALUES ('B1', 1);


------------------------------------------------------------------------------------------------------------
-- Produtor B1
------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`producer`(`name`, `metadata`) VALUES ('B2', 1);


------------------------------------------------------------------------------------------------------------
INSERT INTO `eventz`.`producer`(`name`, `metadata`) VALUES ('B3', 1);


------------------------------------------------------------------------------------------------------------

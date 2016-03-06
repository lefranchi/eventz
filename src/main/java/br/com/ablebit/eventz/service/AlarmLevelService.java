package br.com.ablebit.eventz.service;

import br.com.ablebit.eventz.domain.AlarmLevel;

public interface AlarmLevelService {

	AlarmLevel save(AlarmLevel alarmLevel);

	public AlarmLevel findById(final Long id);

}

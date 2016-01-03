package br.com.lefranchi.eventz.service;

import br.com.lefranchi.eventz.domain.AlarmLevel;

public interface AlarmLevelService {

	AlarmLevel save(AlarmLevel alarmLevel);

	public AlarmLevel findById(final Long id);

}

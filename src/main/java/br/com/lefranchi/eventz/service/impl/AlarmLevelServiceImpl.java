package br.com.lefranchi.eventz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.lefranchi.eventz.domain.AlarmLevel;
import br.com.lefranchi.eventz.repository.AlarmLevelRepository;
import br.com.lefranchi.eventz.service.AlarmLevelService;

@Service
@Validated
public class AlarmLevelServiceImpl implements AlarmLevelService {

	@Autowired
	AlarmLevelRepository repository;

	@Override
	public AlarmLevel save(final AlarmLevel alarmLevel) {
		return repository.save(alarmLevel);
	}

	@Override
	public AlarmLevel findById(final Long id) {
		return repository.findOne(id);
	}

}

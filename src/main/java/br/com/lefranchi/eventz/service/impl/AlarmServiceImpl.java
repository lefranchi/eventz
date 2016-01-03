package br.com.lefranchi.eventz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.lefranchi.eventz.domain.Alarm;
import br.com.lefranchi.eventz.repository.AlarmRepository;
import br.com.lefranchi.eventz.service.AlarmService;

@Service
@Validated
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	AlarmRepository repository;

	@Override
	public Alarm save(final Alarm alarm) {
		return repository.save(alarm);
	}
}

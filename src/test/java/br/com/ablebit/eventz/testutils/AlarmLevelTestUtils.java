package br.com.ablebit.eventz.testutils;

import br.com.ablebit.eventz.domain.AlarmLevel;

public class AlarmLevelTestUtils {

	public static AlarmLevel newAlarmLevel() {

		final AlarmLevel alarmLevel = new AlarmLevel();
		alarmLevel.setName("Informacao");
		alarmLevel.setColor("blue");
		alarmLevel.setImage("info.png");

		return alarmLevel;

	}

}

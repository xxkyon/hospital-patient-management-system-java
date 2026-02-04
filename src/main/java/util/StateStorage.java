package util;

import state.DailyState;

public interface StateStorage {
	DailyState loadToday();
	void saveToday(DailyState state);
}

package com.devsquard.mypage.domain.response;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

import com.devsquard.auth.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyProfileCardResponse {
	private Long id;
	private String nickName, language, intro;
	private int streakCount;
	private boolean[] streakDaysInMonth;

	public static MyProfileCardResponse toDTO(User user) {
		LocalDate now = LocalDate.now();
		YearMonth yearMonth = YearMonth.from(now);
		int totalDaysInMonth = yearMonth.lengthOfMonth();

		boolean[] streakDays = new boolean[totalDaysInMonth];
		Set<LocalDate> streDates = user.getStreakDates();

		for (LocalDate date : streDates) {
			if (date.getYear() == now.getYear() && date.getMonth() == now.getMonth()) {
				int dayOfMonth = date.getDayOfMonth();
				if (dayOfMonth <= totalDaysInMonth) {
					streakDays[dayOfMonth - 1] = true;
				}
			}
		}

		return MyProfileCardResponse.builder().id(user.getId()).nickName(user.getNickName()).language(user.getLanguage())
				.streakCount(user.getStreakCount()).intro(user.getIntro()).streakDaysInMonth(streakDays).build();
	}
}

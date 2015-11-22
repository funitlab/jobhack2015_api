package com.funitlab.jobhack.network;

import services.NotificationService;

public class AwardManager {

	private NotificationService notiService;

	public AwardManager() {
		notiService = new NotificationService();
	}

	public void awardRefer() {
		notiService.showNotification(
				"You have got a reward for refering a candidate, "+
				"Tap to see more!");
	}

	public void awardFinishJob() {
		notiService.showNotification(
				"Your refering candidate has just got a new job thank to you. "+
				"Reward yourself a beer now.");
	}
}

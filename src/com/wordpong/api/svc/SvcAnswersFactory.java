package com.wordpong.api.svc;

public class SvcAnswersFactory {
	public static SvcAnswers getAnswersService() {
		return new SvcAnswersImpl();
	}
}

package com.wordpong.api.svc;

import java.util.List;

import com.wordpong.api.model.GameMyTurn;

public interface SvcGame {
	List<GameMyTurn> getMyTurns();
	
	void setMyTurns(List<GameMyTurn> myTurns);
}

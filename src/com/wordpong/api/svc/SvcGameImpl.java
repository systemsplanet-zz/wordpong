package com.wordpong.api.svc;

import java.util.ArrayList;
import java.util.List;

import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameMyTurn.Action;

public class SvcGameImpl implements SvcGame {

    @Override
    public List<GameMyTurn> getMyTurns() {
        // TODO Call backend
        List<GameMyTurn> result = new ArrayList<GameMyTurn>();
        GameMyTurn gmt = new GameMyTurn();
        result.add(gmt);
        gmt = new GameMyTurn();
        gmt.setAction(Action.InvitationRequest);
        result.add(gmt);
        gmt = new GameMyTurn();
        gmt.setAction(Action.InviteAccepted);
        result.add(gmt);
        return result;
    }

    @Override
    public void setMyTurns(List<GameMyTurn> myTurns) {
        // TODO call backend

    }


}

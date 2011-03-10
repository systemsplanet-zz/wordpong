package com.wordpong.cmn.db;

public class TransactionRetry {

	
	/*
	 
for (int i = 0; i < NUM_RETRIES; i++) {
    Transaction tx = Datastore.beginTransaction();
    ClubMembers members = Datastore.get(tx, ClubMembers.class, key);
    members.incrementCounterBy(1);
    try {
        tx.commit();
        break;
    } catch (ConcurrentModificationException ex) {
        if (i == (NUM_RETRIES - 1)) { 
            throw ex;
        }
    }
}
	 
	 */
}

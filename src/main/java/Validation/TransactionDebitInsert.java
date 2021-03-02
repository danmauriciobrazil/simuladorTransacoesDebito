/*
 * CABAL BRASIL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * Copyright (c) 2019, Cabal Brasil and/or its affiliates. All rights reserved.
 */
package Validation;

import Repository.TransactionDebit;
import org.jpos.ee.DB;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;

import java.io.Serializable;

public class TransactionDebitInsert {

	public void insertTransacion(TransactionDebit transactionDebit, Serializable context){
		Context c = (Context) context;
		DB con  = c.get("DB");
		
		//con.beginTransaction();
		transactionDebit.getCard();
		transactionDebit.getResponseCode();
		transactionDebit.getAccount();
		transactionDebit.getAccountType();
		
		con.save(transactionDebit);
		con.commit();
		con.close();
		
	}
}
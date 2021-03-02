/*
 * CABAL BRASIL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * Copyright (c) 2019, Cabal Brasil and/or its affiliates. All rights reserved.
 */
package Validation;

import Repository.CardEntity;
import Repository.ResponseCodeEntity;
import Repository.TransactionDebit;
import org.hibernate.query.Query;
import org.jpos.ee.DB;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.util.LogEvent;

import java.io.Serializable;
import java.util.Objects;

public class ValidarCard implements TransactionParticipant {
	
	@Override
	public int prepare(final long id, final Serializable context) {
		Context c = (Context) context;
		ISOMsg incomingMessage = c.get("INCOMING_MESSAGE");
		String numCard = incomingMessage.getString(2);
		LogEvent log = c.getLogEvent();
		ResponseCodeEntity responseCodeEntity = new ResponseCodeEntity();
		TransactionDebitInsert transactionDebitInsert = new TransactionDebitInsert();
		TransactionDebit transactionDebit = new TransactionDebit();
		
		DB con  = c.get("DB");
		Query query = con.session().createQuery("select  c from CardEntity c where c.card = :card ", CardEntity.class);
		((Query) query).setParameter("card", numCard);
		CardEntity card = (CardEntity) query.getSingleResult();
		
		if( !Objects.isNull(card)){
			responseCodeEntity.setInternalResponseCode("00");
			incomingMessage.set(39,responseCodeEntity.getInternalResponseCode());
			log.addMessage("O cartão enviado  existe!!!" );
			transactionDebit.setResponseCode(responseCodeEntity.getInternalResponseCode());
			transactionDebit.setCard(card.getCard());
			transactionDebit.setAccount(card.getAccount());
			transactionDebit.setAccountType("1");
			transactionDebit.setPurchaseValue(150);
			transactionDebitInsert.insertTransacion(transactionDebit, context);
			return PREPARED;
		}else{
			log.addMessage("O cartão enviado não existe!!!");
			incomingMessage.set(39,"76");
			return PREPARED;
		}
		
	}
}
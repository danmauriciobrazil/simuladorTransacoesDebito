/*
 * CABAL BRASIL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * Copyright (c) 2019, Cabal Brasil and/or its affiliates. All rights reserved.
 */
package Service;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.MUX;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.util.NameRegistrar;

import java.io.Serializable;

public class QueryHost implements TransactionParticipant
{
	@Override
	public int prepare(final long id, final Serializable context)
	{
		try
		{
			Context ctx = (Context) context;
			final MUX mux = NameRegistrar.get("mux.mux-adq-05");
			
			final ISOMsg response = mux.request(ctx.get("INCOMING_MESSAGE"), 10000);
			
			ctx.put("OUTGOING_MESSAGE_RESPONSE", response);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return PREPARED;
	}
}

/*
 * CABAL BRASIL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * Copyright (c) 2019, Cabal Brasil and/or its affiliates. All rights reserved.
 */
package Filter;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOFilter;
import org.jpos.iso.ISOMsg;
import org.jpos.util.LogEvent;

public  class IncomingFilter implements ISOFilter {
	
	@Override
	public ISOMsg filter( ISOChannel isoChannel,  ISOMsg message,  LogEvent evt) throws VetoException {
		
		String pan = message.getString(2);
		
		evt.addMessage("Esse é o cartão");
		evt.addMessage("Esse é o filtro de entrada");
		return message;
	}
}
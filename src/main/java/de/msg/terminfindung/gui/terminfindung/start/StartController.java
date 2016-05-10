package de.msg.terminfindung.gui.terminfindung.start;

import org.apache.log4j.Logger;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;

public class StartController{
	private static final Logger LOG = Logger.getLogger(StartController.class);
	    
    public void werfeTechnicalException() throws TerminfindungTechnicalException{
    	TerminfindungTechnicalException e = new TerminfindungTechnicalException("ID", "Dies ist eine simulierte technische Exception");
    	throw(e);
    }
    
    public void werfeBusinessException() throws TerminfindungBusinessException{
    	TerminfindungBusinessException e = new TerminfindungBusinessException("ID", "Dies ist eine simulierte fachliche Exception");
    	throw(e);
    }
}

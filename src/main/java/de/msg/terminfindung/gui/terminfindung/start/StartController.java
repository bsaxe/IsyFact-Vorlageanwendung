package de.msg.terminfindung.gui.terminfindung.start;

import org.apache.log4j.Logger;
import org.springframework.webflow.engine.support.TransitionExecutingFlowExecutionExceptionHandler;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;

public class StartController extends AbstractController<StartModel>{
	private static final Logger LOG = Logger.getLogger(StartController.class);
	
    public void initialisiereModel(StartModel model) {
        super.initialisiereModell(model);     
        RequestContext context = RequestContextHolder.getRequestContext();       
        Throwable exception = (Throwable) context.getFlashScope().get(TransitionExecutingFlowExecutionExceptionHandler.ROOT_CAUSE_EXCEPTION_ATTRIBUTE);
        model.setException(exception);
    } 
    
    public void werfeTechnicalException() throws TerminfindungTechnicalException{
    	TerminfindungTechnicalException e = new TerminfindungTechnicalException("ID", "Dies ist eine simulierte technische Exception");
    	throw(e);
    }
    
    public void werfeBusinessException() throws TerminfindungBusinessException{
    	TerminfindungBusinessException e = new TerminfindungBusinessException("ID", "Dies ist eine simulierte fachliche Exception");
    	throw(e);
    }
}

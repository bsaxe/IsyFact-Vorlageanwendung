package de.msg.terminfindung.gui.terminfindung.ausnahme;

import org.springframework.webflow.engine.support.TransitionExecutingFlowExecutionExceptionHandler;
import org.springframework.webflow.execution.RequestContext;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;

public class AusnahmeController {
	
	public void initialisiereModel(AusnahmeModel model, RequestContext context){
		TerminfindungBusinessException exc = (TerminfindungBusinessException) context.getFlashScope().get(TransitionExecutingFlowExecutionExceptionHandler.ROOT_CAUSE_EXCEPTION_ATTRIBUTE);
		model.setException(exc);
	}
}

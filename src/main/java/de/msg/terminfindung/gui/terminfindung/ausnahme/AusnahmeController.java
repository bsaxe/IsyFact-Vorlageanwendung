package de.msg.terminfindung.gui.terminfindung.ausnahme;

import org.springframework.webflow.engine.support.TransitionExecutingFlowExecutionExceptionHandler;
import org.springframework.webflow.execution.RequestContext;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.gui.terminfindung.erstellen.ErstellenController;

public class AusnahmeController {
	private static final IsyLogger LOG = IsyLoggerFactory.getLogger(AusnahmeController.class);
	
	public void initialisiereModel(AusnahmeModel model, RequestContext context){
		TerminfindungBusinessException exc = (TerminfindungBusinessException) context.getFlashScope().get(TransitionExecutingFlowExecutionExceptionHandler.ROOT_CAUSE_EXCEPTION_ATTRIBUTE);
		model.setException(exc);
		
		LOG.debug("initialisiere AunahmeModell");
		
		
	}
}

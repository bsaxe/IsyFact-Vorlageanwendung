package de.msg.terminfindung.gui.terminfindung.start;

import java.io.Serializable;

import de.msg.terminfindung.gui.terminfindung.AbstractModel;

public class StartModel extends AbstractModel implements Serializable{
	private static final long serialVersionUID = 3322294916761239009L;

	private Throwable exception;

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
	
}

package de.msg.terminfindung.gui.terminfindung.ausnahme;

import java.io.Serializable;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;

public class AusnahmeModel implements Serializable {
	private static final long serialVersionUID = -8978195471929402643L;

	private TerminfindungBusinessException exception;

	public TerminfindungBusinessException getException() {
		return exception;
	}

	public void setException(TerminfindungBusinessException exception) {
		this.exception = exception;
	}
}

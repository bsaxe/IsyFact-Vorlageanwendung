package de.msg.terminfindung.gui.terminfindung.verwalten.aktualisieren;

import java.io.Serializable;

import de.msg.terminfindung.gui.terminfindung.AbstractModel;

/**
 * Model für den Aktualisieren flow
 * @author vadim
 *
 */
public class AktualisierenModel extends AbstractModel implements Serializable{

	private static final long serialVersionUID = 1465541025910066035L;
	
	private String veranstaltungsName;
	
	private String organisatorName;

	public String getVeranstaltungsName() {
		return veranstaltungsName;
	}

	public void setVeranstaltungsName(String veranstaltungsName) {
		this.veranstaltungsName = veranstaltungsName;
	}

	public String getOrganisatorName() {
		return organisatorName;
	}

	public void setOrganisatorName(String organisatorName) {
		this.organisatorName = organisatorName;
	}
}

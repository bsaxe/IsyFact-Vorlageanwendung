package de.msg.terminfindung.persistence.dao.jpa;

/*
 * #%L
 * Terminfindung
 * %%
 * Copyright (C) 2015 - 2016 Bundesverwaltungsamt (BVA), msg systems ag
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import de.msg.terminfindung.persistence.dao.TeilnehmerDao;
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import de.msg.terminfindung.persistence.entity.TeilnehmerZeitraum;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementierung fuer die TeilnehmerDAO
 * @author msg systems ag, Maximilian Falter
 *
 */
public class JpaTeilnehmerDao extends AbstraktJpaDao<Teilnehmer> implements TeilnehmerDao{

	/**
	 * Laedt praeferenzen zu einem Teilnehmer mit prepared statement
	 */
	@Override
	public Collection<TeilnehmerZeitraum> ladePraeferenzZuTeilnehmer(
			Teilnehmer teilnehmer) {
		Query query = super.getEntityManager().createNamedQuery("leseTeilnehmerPraeferenzMitId");
		query.setParameter("nr", teilnehmer.getId());
		List<TeilnehmerZeitraum> tz = new ArrayList<>(query.getResultList().size());
		for(Object o:query.getResultList()){
			tz.add((TeilnehmerZeitraum) o);
		}
		return tz;
	}

}

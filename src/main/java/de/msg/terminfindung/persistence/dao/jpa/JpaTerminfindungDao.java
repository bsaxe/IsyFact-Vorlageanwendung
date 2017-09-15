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

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Terminfindung;

/**
 * Implementierung fuer die TerminfindungDAO
 *
 * @author msg systems ag, Maximilian Falter
 */
public class JpaTerminfindungDao extends AbstraktJpaDao<Terminfindung> implements TerminfindungDao {

    @Override
    public List<Terminfindung> sucheVor(LocalDate datum) {
        TypedQuery<Terminfindung> q = getEntityManager().createNamedQuery("terminfindung.vor", Terminfindung.class);
        q.setParameter("datum", datum);
        return q.getResultList();
    }

	@Override
	public List<Terminfindung> findeAlle() {
		EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Terminfindung> cq = cb.createQuery(Terminfindung.class);
        Root<Terminfindung> rootEntry = cq.from(Terminfindung.class);
        CriteriaQuery<Terminfindung> all = cq.select(rootEntry);
        TypedQuery<Terminfindung> allQuery = em.createQuery(all);
        
        return allQuery.getResultList();
    }

	@Override
	public Terminfindung sucheMitReferenz(String ref) {
        TypedQuery<Terminfindung> query = getEntityManager().createNamedQuery("terminfindung.ref", Terminfindung.class);
        query.setParameter("ref", ref);
        return query.getSingleResult();
	}

	
}

package de.msg.terminfindung.persistence.entity;

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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestTerminfindung {

	@Test
	public void testFindeZeitraumById (){
		Terminfindung tf = new Terminfindung();
		Tag tag = new Tag();
		Zeitraum zeitraum1 = new Zeitraum();
		Zeitraum zeitraum2 = new Zeitraum();
		
		zeitraum1.setId(1L);
		zeitraum2.setId(22L);
				
		tag.getZeitraeume().add(zeitraum1);
		tag.getZeitraeume().add(zeitraum2);
		
		tf.getTermine().add(tag);
		
		assertNull(tf.findeZeitraumById(0));
		assertNotNull(tf.findeZeitraumById(22));		
				
	}
	
	@Test 
	public void testFindeZeitraumByIdTermineNull (){
		Terminfindung tf = new Terminfindung();
		tf.setTermine(null);
		
		assertNull(tf.findeZeitraumById(0));
	}
	
	@Test 
	public void testFindeZeitraumByIdTageNull (){
		Terminfindung tf = new Terminfindung();
		Tag tag = new Tag();
		tf.getTermine().add(tag);
		
		tag.setZeitraeume(null);
		
		assertNull(tf.findeZeitraumById(0));
	}
}

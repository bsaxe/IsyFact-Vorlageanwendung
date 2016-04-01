package de.msg.terminfindung.persistence.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestTerminfindung {

	@Test
	public void testFindeZeitraumById (){
		Terminfindung tf = new Terminfindung();
		Tag tag = new Tag();
		Zeitraum zeitraum1 = new Zeitraum();
		Zeitraum zeitraum2 = new Zeitraum();
		
		zeitraum1.setZeitraum_Nr(1);
		zeitraum2.setZeitraum_Nr(22);
				
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

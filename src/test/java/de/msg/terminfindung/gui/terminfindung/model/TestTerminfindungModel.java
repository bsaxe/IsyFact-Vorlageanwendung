package de.msg.terminfindung.gui.terminfindung.model;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

import static org.junit.Assert.*;


@RunWith(BlockJUnit4ClassRunner.class)
public class TestTerminfindungModel {
	@Test
	public void testFindeZeitraumByIdModel (){
		TerminfindungModel tfModel = new TerminfindungModel();
		TagModel tagModel = new TagModel();
		ZeitraumModel zeitraumModel1 = new ZeitraumModel();
		ZeitraumModel zeitraumModel2 = new ZeitraumModel();
		
		zeitraumModel1.setZeitraum_nr(1);
		zeitraumModel2.setZeitraum_nr(22);
				
		tagModel.getZeitraeume().add(zeitraumModel1);
		tagModel.getZeitraeume().add(zeitraumModel2);
		
		tfModel.getTage().add(tagModel);
		
		
		assertNull(tfModel.findeZeitraumById(0));
		assertNotNull(tfModel.findeZeitraumById(22));		
				
	}
	
	@Test 
	public void testFindeZeitraumByIdTermineNullModel (){
		TerminfindungModel tfModel = new TerminfindungModel();
		tfModel.setTage(null);
		
		assertNull(tfModel.findeZeitraumById(0));
	}
	
	@Test 
	public void testFindeZeitraumByIdTageNullModel (){
		TerminfindungModel tfModel = new TerminfindungModel();
		TagModel tagModel = new TagModel();
		tfModel.getTage().add(tagModel);
		
		tagModel.setZeitraeume(null);
		
		assertNull(tfModel.findeZeitraumById(0));
	}

}

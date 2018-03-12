package com.jfkey.sarank;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jfkey.sarank.repository.PaperInfoRespository;
import com.jfkey.sarank.service.PaperInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SarankApplicationTests {
	
	
	@Autowired
	private Session session;
	
	@Autowired
	private PaperInfoRespository paperInfoRespository;
	
	@Test
	public void testFormatInput() {
	}
	
	@Before
	public void setUp(){
		
	}
	@After 
	public void tearDown() {
		session.purgeDatabase();
	}
	
	
	
	@Test
	public void testGetPaperIDWeight() {
		String queryParam = "originalTitle:DATABASE AND originalTitle:graph";
		int size = 0;
		Iterator<Map<String, Object>> iterator = paperInfoRespository.getPaperIDWeight(queryParam).iterator();
		while(iterator.hasNext()) {
			size++;
		}
		assertEquals(296,size);
		
	}
	
//	@Before
//	public void setUp() {
//	}
//
//	@After
//	public void tearDown() {
//		session.purgeDatabase();
//	}

	/**
	 * Test of findByTitle method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitle() {

//		String title = "The Matrix";
//		Movie result = instance.findByTitle(title);
//		assertNotNull(result);
//		assertEquals(1999, result.getReleased());
	}

	/**
	 * Test of findByTitleContaining method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitleContaining() {
//		String title = "*Matrix*";
//		Collection<Movie> result = instance.findByTitleLike(title);
//		assertNotNull(result);
//		assertEquals(1, result.size());
	}


}

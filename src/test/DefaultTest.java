package test;

import static org.junit.Assert.*;
import org.junit.Test;


public class DefaultTest {
	@Test
	public void testDefault(){
		int a = 2 + 2;
		assertEquals("A test for basic addition", 4, a);
	}
	
	
}

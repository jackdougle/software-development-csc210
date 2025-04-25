// Author: Jack Douglass
// File: ASTNodeTest.java
// Class: CSC 210, Fall 2024
// Purpose: This file provides test cases to test the functionality of ASTNode,java.

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ASTNodeTest {

	@Test
	void testNull() {
		ASTNode socrates = ASTNode.createIdNode("Socrates");
		ASTNode plato = ASTNode.createIdNode("Plato");
		assertNull(socrates.child1);
		assertNull(plato.child2);
	}
	
	@Test
	void testNotNull() {
		ASTNode odysseus = ASTNode.createIdNode("Odysseus");
		ASTNode theseus = ASTNode.createIdNode("Theseus");
		ASTNode cecilia = ASTNode.createNandNode(odysseus, theseus);
		assertNotNull(cecilia.child1);
		assertNotNull(cecilia.child2);
	}
	
	@Test
	void testIdType() {
		ASTNode guy = ASTNode.createIdNode("guy");
		ASTNode dude = ASTNode.createIdNode("dude");
		ASTNode bob = ASTNode.createNandNode(guy, dude);
		assertTrue(guy.isId());
		assertTrue(bob.isNand());
		assertFalse(guy.isNand());
		assertFalse(dude.isNand());
	}
	
	@Test
	void testGetId() {
		ASTNode videoGame = ASTNode.createIdNode("Minecraft");
		ASTNode musician = ASTNode.createIdNode("Taylor Swift");
		assertEquals("Minecraft", videoGame.getId());
		assertEquals("Taylor Swift", musician.getId());
	}

}

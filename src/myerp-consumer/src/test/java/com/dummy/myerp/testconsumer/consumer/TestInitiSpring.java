package com.dummy.myerp.testconsumer.consumer;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Kybox
 * @version 1.0
 */
public class TestInitiSpring extends ConsumerTestCase {

    /**
     * Constructeur.
     */
    public TestInitiSpring(){
        super();
    }

    /**
     * Teste l'initialisation du contexte Spring
     */
    @Test
    public void testInit() {
        SpringRegistry.init();
        assertNotNull(SpringRegistry.getDaoProxy());
    }
}

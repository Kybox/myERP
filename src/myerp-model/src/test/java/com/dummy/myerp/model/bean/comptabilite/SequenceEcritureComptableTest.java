package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Kybox
 * @version 1.0
 */

@RunWith(MockitoJUnitRunner.class)
public class SequenceEcritureComptableTest {

    @Mock
    SequenceEcritureComptable sequence;

    private final Integer ANNEE = 1234;
    private final Integer VALEUR = 5678;
    private final String CODE = "ZZ";

    @Before
    public void setUp() {

        Mockito.when(sequence.getAnnee()).thenReturn(ANNEE);
        Mockito.when(sequence.getDerniereValeur()).thenReturn(VALEUR);
        Mockito.when(sequence.getCode()).thenReturn(CODE);
        Mockito.doCallRealMethod().when(sequence).toString();
    }

    @Test
    public void constructor() {

        sequence = new SequenceEcritureComptable(ANNEE, VALEUR);
        Assert.assertEquals(ANNEE, sequence.getAnnee());
        Assert.assertEquals(VALEUR, sequence.getDerniereValeur());
    }

    @Test
    public void getCode() {

        Assert.assertEquals(CODE, sequence.getCode());
    }

    @Test
    public void getToString() {

        String result = sequence.getClass().getSimpleName();
        result += "{annee=" + ANNEE + ", derniereValeur=" + VALEUR + ", ";
        result += "code=" + CODE + "}";

        Assert.assertEquals(result, sequence.toString());
    }
}

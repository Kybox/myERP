package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

/**
 * @author Kybox
 * @version 1.0
 */

@RunWith(MockitoJUnitRunner.class)
public class LigneEcritureComptableTest {

    @Mock
    LigneEcritureComptable ligne;

    private final String LIB = "Test";
    private final BigDecimal CRED = new BigDecimal(123);
    private final BigDecimal DEB = new BigDecimal(321);
    private final CompteComptable CPT = new CompteComptable();

    @Before
    public void setUp() {

        Mockito.when(ligne.getCompteComptable()).thenReturn(CPT);
        Mockito.when(ligne.getLibelle()).thenReturn(LIB);
        Mockito.when(ligne.getCredit()).thenReturn(CRED);
        Mockito.when(ligne.getDebit()).thenReturn(DEB);
    }

    @Test
    public void constructor() {

        ligne = new LigneEcritureComptable(CPT, LIB, DEB, CRED);

        Assert.assertEquals(CPT, ligne.getCompteComptable());
        Assert.assertEquals(LIB, ligne.getLibelle());
        Assert.assertEquals(DEB, ligne.getDebit());
        Assert.assertEquals(CRED, ligne.getCredit());
    }

    @Test
    public void getToString() {

        String result = ligne.getClass().getSimpleName();
        result += "{compteComptable=" + CPT + ", libelle='" + LIB + "', ";
        result += "debit=" + DEB + ", credit=" + CRED + "}";
    }
}

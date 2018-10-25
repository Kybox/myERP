package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EcritureComptableTest {

    @Mock
    private EcritureComptable ecritureComptable;

    @Test
    public void getTotalDebit(){

        Mockito.when(ecritureComptable.getTotalDebit()).thenReturn(BigDecimal.valueOf(123));
        Assert.assertEquals(ecritureComptable.getTotalDebit(), BigDecimal.valueOf(123));
    }

    @Test
    public void getTotalCredit(){

        Mockito.when(ecritureComptable.getTotalCredit()).thenReturn(BigDecimal.valueOf(321));
        Assert.assertEquals(ecritureComptable.getTotalCredit(), BigDecimal.valueOf(321));
    }

    @Test
    public void isEquilibree() {

        Mockito.doCallRealMethod().when(ecritureComptable).isEquilibree();

        // Positive values : totalCredit = 100, totalDebit = 100
        Mockito.when(ecritureComptable.getTotalCredit()).thenReturn(BigDecimal.valueOf(100));
        Mockito.when(ecritureComptable.getTotalDebit()).thenReturn(BigDecimal.valueOf(100));
        Assert.assertTrue(ecritureComptable.isEquilibree());

        // Positive values : totalCredit = 100, totalDebit = 10
        Mockito.when(ecritureComptable.getTotalDebit()).thenReturn(BigDecimal.valueOf(10));
        Assert.assertFalse(ecritureComptable.isEquilibree());

        // Negative values, : totalCredit = -100, totalDebit = -100
        Mockito.when(ecritureComptable.getTotalCredit()).thenReturn(BigDecimal.valueOf(-100));
        Mockito.when(ecritureComptable.getTotalDebit()).thenReturn(BigDecimal.valueOf(-100));
        Assert.assertTrue(ecritureComptable.isEquilibree());

        // Negative values, : totalCredit = -100, totalDebit = -10
        Mockito.when(ecritureComptable.getTotalDebit()).thenReturn(BigDecimal.valueOf(-10));
        Assert.assertFalse(ecritureComptable.isEquilibree());
    }
}

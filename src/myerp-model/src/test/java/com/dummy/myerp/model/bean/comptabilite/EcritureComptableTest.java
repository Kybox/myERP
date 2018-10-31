package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EcritureComptableTest {

    @Mock
    private EcritureComptable ecritureComptable;

    private final Integer ID = 1;
    private final JournalComptable JOURNAL = new JournalComptable();
    private final String REF = "ZZ-9876/54321";
    private final Date DATE = new Date();
    private final String LIB = "Test";
    private final BigDecimal TOTDEB = new BigDecimal(123);
    private final BigDecimal TOTCRE = new BigDecimal(321);

    @Before
    public void setUp() {

        Mockito.when(ecritureComptable.getId()).thenReturn(ID);
        Mockito.when(ecritureComptable.getJournal()).thenReturn(JOURNAL);
        Mockito.when(ecritureComptable.getReference()).thenReturn(REF);
        Mockito.when(ecritureComptable.getDate()).thenReturn(DATE);
        Mockito.when(ecritureComptable.getLibelle()).thenReturn(LIB);
        Mockito.when(ecritureComptable.getListLigneEcriture()).thenReturn(null);
        Mockito.when(ecritureComptable.getTotalCredit()).thenReturn(TOTCRE);
        Mockito.when(ecritureComptable.getTotalDebit()).thenReturn(TOTDEB);
        Mockito.doCallRealMethod().when(ecritureComptable).toString();
    }

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

    @Test
    public void getToString() {

        String result = ecritureComptable.getClass().getSimpleName();
        result += "{id=" + ID + ", journal=" + JOURNAL + ", reference='" + REF + "', ";
        result += "date=" + DATE + ", libelle='" + LIB + "', totalDebit=" + TOTDEB.toPlainString() + ", ";
        result += "totalCredit=" + TOTCRE.toPlainString() + ", listLigneEcriture=[\n";
        result += "null\n]}";

        Assert.assertEquals(result, ecritureComptable.toString());
    }
}

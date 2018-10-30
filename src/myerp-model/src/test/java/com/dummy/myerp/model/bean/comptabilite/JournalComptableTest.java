package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */

@RunWith(MockitoJUnitRunner.class)
public class JournalComptableTest {

    @Mock
    private JournalComptable journalComptable;

    private final String LIBELLE = "Libelle";
    private final String CODE = "123";

    @Before
    public void setUp() {

        Mockito.when(journalComptable.getLibelle()).thenReturn(LIBELLE);
        Mockito.when(journalComptable.getCode()).thenReturn(CODE);
        Mockito.doCallRealMethod().when(journalComptable).toString();
    }

    @Test
    public void getLibelle() {

        Assert.assertEquals(LIBELLE, journalComptable.getLibelle());
    }

    @Test
    public void getCode() {

        Assert.assertEquals(CODE, journalComptable.getCode());
    }

    @Test
    public void getToString() {

        String result = journalComptable.getClass().getSimpleName();
        result += "{code='" + CODE + "', libelle='" + LIBELLE + "'}";

        Assert.assertEquals(result, journalComptable.toString());
    }

    @Test
    public void getByCode() {

        List<JournalComptable> list = new ArrayList<>();

        list.add(new JournalComptable("AC", "Achat"));
        list.add(new JournalComptable("VE", "Vente"));

        Assert.assertEquals("Achat", JournalComptable.getByCode(list, "AC").getLibelle());
        Assert.assertEquals("Vente", JournalComptable.getByCode(list, "VE").getLibelle());
        Assert.assertNotEquals("Achat", JournalComptable.getByCode(list, "VE").getLibelle());
    }
}

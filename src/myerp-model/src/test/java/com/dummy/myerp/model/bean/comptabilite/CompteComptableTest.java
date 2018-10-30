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
public class CompteComptableTest {

    @Mock
    private CompteComptable compteComptable;

    private final String LIBELLE = "Libelle";
    private final Integer NUMERO = 123;

    @Before
    public void setUp() {

        Mockito.when(compteComptable.getLibelle()).thenReturn(LIBELLE);
        Mockito.when(compteComptable.getNumero()).thenReturn(NUMERO);
        Mockito.doCallRealMethod().when(compteComptable).toString();
    }

    @Test
    public void getLibelle() {

        Assert.assertEquals(LIBELLE, compteComptable.getLibelle());
    }

    @Test
    public void getNumero() {

        Assert.assertEquals(NUMERO, compteComptable.getNumero());
    }

    @Test
    public void getToString() {

        String result = compteComptable.getClass().getSimpleName();
        result += "{numero=" + NUMERO + ", libelle='" + LIBELLE + "'}";

        Assert.assertEquals(result, compteComptable.toString());
    }

    @Test
    public void getByNumero() {

        List<CompteComptable> list = new ArrayList<>();

        list.add(new CompteComptable(1, "cpt1"));
        list.add(new CompteComptable(2, "cpt2"));

        Assert.assertEquals("cpt1", CompteComptable.getByNumero(list, 1).getLibelle());
        Assert.assertEquals("cpt2", CompteComptable.getByNumero(list, 2).getLibelle());
        Assert.assertNotEquals("cpt1", CompteComptable.getByNumero(list, 2).getLibelle());
    }
}

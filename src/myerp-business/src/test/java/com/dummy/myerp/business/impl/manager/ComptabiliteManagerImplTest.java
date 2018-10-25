package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

public class ComptabiliteManagerImplTest extends BusinessTestCase {

    private static final Logger logger = LogManager.getLogger(ComptabiliteManagerImpl.class);

    private EcritureComptable vEcritureComptable;

    private ComptabiliteManagerImpl managerImpl;
    private ComptabiliteManager manager;

    private Integer currentDate;

    @Before
    public void setUp(){

        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");

        managerImpl = new ComptabiliteManagerImpl();

        currentDate = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    }

    @Test
    public void checkEcritureComptableUnit() throws Exception {

        logger.debug("Method checkEcritureComptableUnit :");

        vEcritureComptable.setReference("AC-" + currentDate + "/00001");
        logger.debug("Reference = " + vEcritureComptable.getReference());

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(123)));

        managerImpl.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {

        managerImpl.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(1234)));

        managerImpl.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));

        managerImpl.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableRG5() throws Exception {

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(123)));

        logger.debug("Method checkEcritureComptableRG5 :");

        vEcritureComptable.setReference("DC-" + currentDate + "/00001");
        logger.debug("Reference = " + vEcritureComptable.getReference());

        currentDate++;
        vEcritureComptable.setReference("AC-" + currentDate + "/00001");
        logger.debug("Reference = " + vEcritureComptable.getReference());

        managerImpl.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableRG6() throws Exception {

        vEcritureComptable.setId(null);
        vEcritureComptable.setReference("AC-2016/00001");

        managerImpl.checkEcritureComptableContext(vEcritureComptable);
    }

    @Test(expected = NotFoundException.class)
    public void addReference() throws Exception {

        manager = getBusinessProxy().getComptabiliteManager();
        manager.addReference(vEcritureComptable);

    }

}

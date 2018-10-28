package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */
public class ComptabiliteDaoImplTest extends ConsumerTestCase {

    private Logger logger = LogManager.getLogger(this.getClass());

    private ComptabiliteDaoImpl comptabiliteDao;
    private EcritureComptable ecritureComptable;
    private List<String> listReferences;
    private String currentDate;

    @Before
    public void setUp(){

        logger.debug("Setup objects");
        comptabiliteDao = new ComptabiliteDaoImpl();
        ecritureComptable = new EcritureComptable();
        currentDate = new SimpleDateFormat("yyyy").format(new Date());

        getReferences();
    }

    private void getReferences(){

        listReferences = new ArrayList<>();
        for(EcritureComptable ecriture : comptabiliteDao.getListEcritureComptable())
            listReferences.add(ecriture.getReference());
    }

    @Test
    public void getListQueries() {

        List<?> list;

        logger.debug("Get list ecriture comptable...");
        list = comptabiliteDao.getListEcritureComptable();
        Assert.assertTrue(list.size() >= 5);

        logger.debug("Get list compte comptable...");
        list = comptabiliteDao.getListCompteComptable();
        Assert.assertTrue(list.size() >= 7);

        logger.debug("Get list journal comptable...");
        list = comptabiliteDao.getListJournalComptable();
        Assert.assertTrue(list.size() >= 4);
    }

    @Test
    public void getEcritureComptable() throws NotFoundException {

        int id = 0;

        logger.debug("Get ecriture comptable...");
        for(int i = 0; i < listReferences.size(); i++){

            id--;
            ecritureComptable = comptabiliteDao.getEcritureComptable(id);
            Assert.assertEquals(listReferences.get(i), ecritureComptable.getReference());
            if((i + 1) < listReferences.size())
                Assert.assertNotEquals(listReferences.get(i + 1), ecritureComptable.getReference());

            ecritureComptable = comptabiliteDao.getEcritureComptableByRef(listReferences.get(i));

            String journalCode = ecritureComptable.getReference().substring(0,2);
            Assert.assertEquals(ecritureComptable.getJournal().getCode(), journalCode);

            String ecritureDate = new SimpleDateFormat("yyyy").format(ecritureComptable.getDate());
            String dateRef = ecritureComptable.getReference().substring(3,7);
            Assert.assertEquals(ecritureDate, dateRef);
        }
    }

    @Test
    public void loadListLigneEcriture() throws NotFoundException {

        int id = 0;

        logger.debug("Get list ligne comptable...");
        for(int i = 1; i <= listReferences.size(); i++){

            id --;
            ecritureComptable = comptabiliteDao.getEcritureComptable(id);
            comptabiliteDao.loadListLigneEcriture(ecritureComptable);
            Assert.assertTrue(ecritureComptable.getListLigneEcriture().size() > 0);
        }
    }

    @Test
    public void insertUpdateDeleteEcritureComptable() throws NotFoundException {

        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Photocopieur");
        ecritureComptable.setReference("OD-" + currentDate + "/012345");
        ecritureComptable.setJournal(new JournalComptable("OD", "Opérations Diverses"));

        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
                "Format A4", new BigDecimal(20), null));

        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "Facture RP011100", null, new BigDecimal(20)));

        logger.debug("Insert ecriture comptable...");
        comptabiliteDao.insertEcritureComptable(ecritureComptable);

        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
                "Format A4", new BigDecimal(21), null));

        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "Facture RP011100", null, new BigDecimal(21)));

        logger.debug("Update ecriture comptable...");
        comptabiliteDao.updateEcritureComptable(ecritureComptable);

        logger.debug("Delete ecriture comptable...");
        ecritureComptable = comptabiliteDao.getEcritureComptableByRef("OD-" + currentDate + "/012345");
        comptabiliteDao.deleteEcritureComptable(ecritureComptable.getId());
    }
}

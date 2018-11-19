package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */

@FixMethodOrder(MethodSorters.JVM)
public class ComptabiliteDaoImplTest extends ConsumerTestCase {

    private Logger logger = LogManager.getLogger(this.getClass());

    private ComptabiliteDaoImpl comptabiliteDao;
    private EcritureComptable ecritureComptable;
    private List<String> listReferences;
    private String currentDate;
    private String reference;
    private Object result;
    private int id;

    @Before
    public void setUp(){

        logger.debug("Setup objects");
        comptabiliteDao = new ComptabiliteDaoImpl();
        ecritureComptable = new EcritureComptable();
        currentDate = new SimpleDateFormat("yyyy").format(new Date());
        reference = "OD-" + currentDate + "/012345";
        getReferences();

        id = (int) (Math.random() * listReferences.size());
    }

    private void getReferences(){

        listReferences = new ArrayList<>();
        for(EcritureComptable ecriture : comptabiliteDao.getListEcritureComptable())
            listReferences.add(ecriture.getReference());
    }

    @Test
    public void getListCompteComptable() {

        logger.info("Get list compte comptable...");
        result = comptabiliteDao.getListCompteComptable();
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof ArrayList);
        Assert.assertTrue(((List) result).size() > 0);
        Assert.assertTrue(((List) result).get(id) instanceof CompteComptable);
    }

    @Test
    public void getListEcritureComptable() {

        logger.info("Get list ecriture comptable...");
        result = comptabiliteDao.getListEcritureComptable();
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof ArrayList);
        Assert.assertTrue(((List) result).size() > 0);
        Assert.assertTrue(((List) result).get(id) instanceof EcritureComptable);
    }

    @Test
    public void getListJournalComptable() {

        logger.info("Get list journal comptable...");
        result = comptabiliteDao.getListJournalComptable();
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof ArrayList);
        Assert.assertTrue(((List) result).size() > 0);
        Assert.assertTrue(((List) result).get(1) instanceof JournalComptable);
    }

    @Test
    public void getEcritureComptable() throws NotFoundException {

        logger.info("Get ecriture comptable...");

        id = 0;
        for(int i = 0; i < listReferences.size(); i++){

            id --;
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

        logger.info("Get list ligne comptable...");

        id *= -1;
        if(id == 0) id = -1;
        ecritureComptable = comptabiliteDao.getEcritureComptable(id);
        comptabiliteDao.loadListLigneEcriture(ecritureComptable);
        result = ecritureComptable.getListLigneEcriture();
        id = (int) (Math.random() * ((List) result).size() - 1);
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof ArrayList);
        Assert.assertTrue(((List) result).size() > 0);
        Assert.assertTrue(((List) result).get(id) instanceof LigneEcritureComptable);
    }

    @Test
    public void insertEcritureComptable() throws NotFoundException {

        logger.info("Insert ecriture comptable...");

        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Photocopieur");
        ecritureComptable.setReference(reference);
        ecritureComptable.setJournal(new JournalComptable("OD", "Opérations Diverses"));

        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
                "Format A4", new BigDecimal(20), null));

        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "Facture RP011100", null, new BigDecimal(20)));

        comptabiliteDao.insertEcritureComptable(ecritureComptable);

        Assert.assertNotNull(comptabiliteDao.getEcritureComptableByRef(reference));
    }

    @Test
    public void updateEcritureComptable() throws NotFoundException {

        logger.info("Update ecriture comptable...");

        ecritureComptable = comptabiliteDao.getEcritureComptableByRef(reference);

        int nbLines = ecritureComptable.getListLigneEcriture().size();

        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
                "Format A4", new BigDecimal(40), null));

        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "Facture RP011100", null, new BigDecimal(40)));

        comptabiliteDao.updateEcritureComptable(ecritureComptable);

        ecritureComptable = comptabiliteDao.getEcritureComptableByRef(reference);
        Assert.assertEquals(ecritureComptable.getListLigneEcriture().size(), nbLines + 2);
    }

    @Test
    public void deleteListLigneEcritureComptable() throws NotFoundException {

        ecritureComptable = comptabiliteDao.getEcritureComptableByRef(reference);
        comptabiliteDao.deleteListLigneEcritureComptable(ecritureComptable.getId());
        ecritureComptable = comptabiliteDao.getEcritureComptableByRef(reference);

        Assert.assertEquals(0, ecritureComptable.getListLigneEcriture().size());
    }

    @Test
    public void deleteEcritureComptable() throws NotFoundException {

        logger.info("Delete ecriture comptable...");

        ecritureComptable = comptabiliteDao.getEcritureComptableByRef(reference);
        comptabiliteDao.deleteEcritureComptable(ecritureComptable.getId());
    }

    @Test(expected = NotFoundException.class)
    public void deletedEcritureComptableException() throws NotFoundException {

        ecritureComptable = comptabiliteDao.getEcritureComptableByRef(reference);
    }

    @Test
    public void getSequenceEcritureComptable() throws NotFoundException {

        ecritureComptable = comptabiliteDao.getEcritureComptable(-1);

        String journalCode = ecritureComptable.getJournal().getCode();
        Integer annee = Integer.valueOf(new SimpleDateFormat("yyyy").format(ecritureComptable.getDate()));

        SequenceEcritureComptable sequence = comptabiliteDao.getSequenceEcritureComptable(journalCode, annee);

        Assert.assertNotNull(sequence);
        Assert.assertEquals(journalCode, sequence.getCode());
        Assert.assertEquals(annee, sequence.getAnnee());
    }

    @Test
    public void upsertSequenceEcritureComptable() throws NotFoundException {

        ecritureComptable = comptabiliteDao.getEcritureComptable(-1);
        Integer newValue = 999;
        Integer lastValue;


        String journalCode = ecritureComptable.getJournal().getCode();
        Integer annee = Integer.valueOf(new SimpleDateFormat("yyyy").format(ecritureComptable.getDate()));
        SequenceEcritureComptable sequence = comptabiliteDao.getSequenceEcritureComptable(journalCode, annee);

        lastValue = sequence.getDerniereValeur();
        sequence.setDerniereValeur(newValue);
        comptabiliteDao.upsertSequenceEcritureComptable(sequence);

        sequence = comptabiliteDao.getSequenceEcritureComptable(journalCode, annee);
        Assert.assertEquals(newValue, sequence.getDerniereValeur());

        sequence.setDerniereValeur(lastValue);
        comptabiliteDao.upsertSequenceEcritureComptable(sequence);
    }
}

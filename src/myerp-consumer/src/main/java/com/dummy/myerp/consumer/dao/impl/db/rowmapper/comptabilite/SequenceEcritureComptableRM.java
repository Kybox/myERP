package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kybox
 * @version 1.0
 */
public class SequenceEcritureComptableRM implements RowMapper<SequenceEcritureComptable> {


    @Override
    public SequenceEcritureComptable mapRow(ResultSet rs, int rowNum) throws SQLException {
        SequenceEcritureComptable vBean = new SequenceEcritureComptable();
        vBean.setCode(rs.getString("journal_code"));
        vBean.setAnnee(rs.getInt("annee"));
        vBean.setDerniereValeur(rs.getInt("derniere_valeur"));
        return vBean;
    }
}

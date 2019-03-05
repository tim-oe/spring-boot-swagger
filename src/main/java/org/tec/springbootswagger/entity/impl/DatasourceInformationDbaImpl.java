package org.tec.springbootswagger.entity.impl;

import org.tec.springbootswagger.entity.DatasourceInformationDba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DatasourceInformationDbaImpl implements DatasourceInformationDba {

    private static final String VERSION_SQL = "SELECT VERSION()";

    @Autowired
    protected transient JdbcTemplate jdbcTemplate;

    /**
     * resultset extractor
     */
    protected transient ResultSetExtractor<String> stringExtractor = new ResultSetExtractor<String>() {
        /** {@inheritDoc} */
        @Override()
        public String extractData(ResultSet rs) throws SQLException {
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        }
    };

    @Override
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public String getVersion() {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(VERSION_SQL);
            }
        };

        return jdbcTemplate.query(psc, stringExtractor);
    }
}
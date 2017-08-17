/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.AnalyticsBetaKeyRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.9.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class AnalyticsBetaKey extends TableImpl<AnalyticsBetaKeyRecord> {

    private static final long serialVersionUID = 1033917903;

    /**
     * The reference instance of <code>diluv.ANALYTICS_BETA_KEY</code>
     */
    public static final AnalyticsBetaKey ANALYTICS_BETA_KEY = new AnalyticsBetaKey();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AnalyticsBetaKeyRecord> getRecordType() {
        return AnalyticsBetaKeyRecord.class;
    }

    /**
     * The column <code>diluv.ANALYTICS_BETA_KEY.USER_ID</code>.
     */
    public final TableField<AnalyticsBetaKeyRecord, Long> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.ANALYTICS_BETA_KEY.CREATED_AT</code>.
     */
    public final TableField<AnalyticsBetaKeyRecord, Timestamp> CREATED_AT = createField("CREATED_AT", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>diluv.ANALYTICS_BETA_KEY</code> table reference
     */
    public AnalyticsBetaKey() {
        this("ANALYTICS_BETA_KEY", null);
    }

    /**
     * Create an aliased <code>diluv.ANALYTICS_BETA_KEY</code> table reference
     */
    public AnalyticsBetaKey(String alias) {
        this(alias, ANALYTICS_BETA_KEY);
    }

    private AnalyticsBetaKey(String alias, Table<AnalyticsBetaKeyRecord> aliased) {
        this(alias, aliased, null);
    }

    private AnalyticsBetaKey(String alias, Table<AnalyticsBetaKeyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Diluv.DILUV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AnalyticsBetaKeyRecord> getPrimaryKey() {
        return Keys.KEY_ANALYTICS_BETA_KEY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AnalyticsBetaKeyRecord>> getKeys() {
        return Arrays.<UniqueKey<AnalyticsBetaKeyRecord>>asList(Keys.KEY_ANALYTICS_BETA_KEY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AnalyticsBetaKeyRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AnalyticsBetaKeyRecord, ?>>asList(Keys.ANALYTICS_BETA_KEY_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsBetaKey as(String alias) {
        return new AnalyticsBetaKey(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AnalyticsBetaKey rename(String name) {
        return new AnalyticsBetaKey(name, null);
    }
}

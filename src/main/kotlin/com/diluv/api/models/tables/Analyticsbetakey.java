/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.AnalyticsbetakeyRecord;
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
public class Analyticsbetakey extends TableImpl<AnalyticsbetakeyRecord> {

    private static final long serialVersionUID = -545880683;

    /**
     * The reference instance of <code>diluv.analyticsBetaKey</code>
     */
    public static final Analyticsbetakey ANALYTICSBETAKEY = new Analyticsbetakey();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AnalyticsbetakeyRecord> getRecordType() {
        return AnalyticsbetakeyRecord.class;
    }

    /**
     * The column <code>diluv.analyticsBetaKey.userID</code>.
     */
    public final TableField<AnalyticsbetakeyRecord, Long> USERID = createField("userID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.analyticsBetaKey.createdAt</code>.
     */
    public final TableField<AnalyticsbetakeyRecord, Timestamp> CREATEDAT = createField("createdAt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>diluv.analyticsBetaKey</code> table reference
     */
    public Analyticsbetakey() {
        this("analyticsBetaKey", null);
    }

    /**
     * Create an aliased <code>diluv.analyticsBetaKey</code> table reference
     */
    public Analyticsbetakey(String alias) {
        this(alias, ANALYTICSBETAKEY);
    }

    private Analyticsbetakey(String alias, Table<AnalyticsbetakeyRecord> aliased) {
        this(alias, aliased, null);
    }

    private Analyticsbetakey(String alias, Table<AnalyticsbetakeyRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<AnalyticsbetakeyRecord> getPrimaryKey() {
        return Keys.KEY_ANALYTICSBETAKEY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AnalyticsbetakeyRecord>> getKeys() {
        return Arrays.<UniqueKey<AnalyticsbetakeyRecord>>asList(Keys.KEY_ANALYTICSBETAKEY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AnalyticsbetakeyRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AnalyticsbetakeyRecord, ?>>asList(Keys.ANALYTICSBETAKEY_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Analyticsbetakey as(String alias) {
        return new Analyticsbetakey(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Analyticsbetakey rename(String name) {
        return new Analyticsbetakey(name, null);
    }
}

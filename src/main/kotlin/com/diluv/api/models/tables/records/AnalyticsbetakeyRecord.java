/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables.records;


import com.diluv.api.models.tables.Analyticsbetakey;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


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
public class AnalyticsbetakeyRecord extends UpdatableRecordImpl<AnalyticsbetakeyRecord> implements Record2<Long, Timestamp> {

    private static final long serialVersionUID = 567113884;

    /**
     * Setter for <code>diluv.analyticsBetaKey.userID</code>.
     */
    public void setUserid(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>diluv.analyticsBetaKey.userID</code>.
     */
    public Long getUserid() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>diluv.analyticsBetaKey.createdAt</code>.
     */
    public void setCreatedat(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>diluv.analyticsBetaKey.createdAt</code>.
     */
    public Timestamp getCreatedat() {
        return (Timestamp) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, Timestamp> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, Timestamp> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return Analyticsbetakey.ANALYTICSBETAKEY.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return Analyticsbetakey.ANALYTICSBETAKEY.CREATEDAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value2() {
        return getCreatedat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsbetakeyRecord value1(Long value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsbetakeyRecord value2(Timestamp value) {
        setCreatedat(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsbetakeyRecord values(Long value1, Timestamp value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AnalyticsbetakeyRecord
     */
    public AnalyticsbetakeyRecord() {
        super(Analyticsbetakey.ANALYTICSBETAKEY);
    }

    /**
     * Create a detached, initialised AnalyticsbetakeyRecord
     */
    public AnalyticsbetakeyRecord(Long userid, Timestamp createdat) {
        super(Analyticsbetakey.ANALYTICSBETAKEY);

        set(0, userid);
        set(1, createdat);
    }
}

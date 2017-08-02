/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables.records;


import com.diluv.api.models.tables.Userbetakey;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class UserbetakeyRecord extends UpdatableRecordImpl<UserbetakeyRecord> implements Record5<String, Boolean, Timestamp, Long, Long> {

    private static final long serialVersionUID = 261356003;

    /**
     * Setter for <code>diluv.userBetaKey.betaKey</code>.
     */
    public void setBetakey(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>diluv.userBetaKey.betaKey</code>.
     */
    public String getBetakey() {
        return (String) get(0);
    }

    /**
     * Setter for <code>diluv.userBetaKey.valid</code>.
     */
    public void setValid(Boolean value) {
        set(1, value);
    }

    /**
     * Getter for <code>diluv.userBetaKey.valid</code>.
     */
    public Boolean getValid() {
        return (Boolean) get(1);
    }

    /**
     * Setter for <code>diluv.userBetaKey.createdAt</code>.
     */
    public void setCreatedat(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>diluv.userBetaKey.createdAt</code>.
     */
    public Timestamp getCreatedat() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>diluv.userBetaKey.userID</code>.
     */
    public void setUserid(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>diluv.userBetaKey.userID</code>.
     */
    public Long getUserid() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>diluv.userBetaKey.creationUserId</code>.
     */
    public void setCreationuserid(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>diluv.userBetaKey.creationUserId</code>.
     */
    public Long getCreationuserid() {
        return (Long) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<String, Boolean, Timestamp, Long, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<String, Boolean, Timestamp, Long, Long> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Userbetakey.USERBETAKEY.BETAKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field2() {
        return Userbetakey.USERBETAKEY.VALID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return Userbetakey.USERBETAKEY.CREATEDAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return Userbetakey.USERBETAKEY.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return Userbetakey.USERBETAKEY.CREATIONUSERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getBetakey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value2() {
        return getValid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getCreatedat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getCreationuserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserbetakeyRecord value1(String value) {
        setBetakey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserbetakeyRecord value2(Boolean value) {
        setValid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserbetakeyRecord value3(Timestamp value) {
        setCreatedat(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserbetakeyRecord value4(Long value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserbetakeyRecord value5(Long value) {
        setCreationuserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserbetakeyRecord values(String value1, Boolean value2, Timestamp value3, Long value4, Long value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserbetakeyRecord
     */
    public UserbetakeyRecord() {
        super(Userbetakey.USERBETAKEY);
    }

    /**
     * Create a detached, initialised UserbetakeyRecord
     */
    public UserbetakeyRecord(String betakey, Boolean valid, Timestamp createdat, Long userid, Long creationuserid) {
        super(Userbetakey.USERBETAKEY);

        set(0, betakey);
        set(1, valid);
        set(2, createdat);
        set(3, userid);
        set(4, creationuserid);
    }
}
/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables.records;


import com.diluv.api.models.tables.Authmfatoken;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


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
public class AuthmfatokenRecord extends UpdatableRecordImpl<AuthmfatokenRecord> implements Record2<String, Long> {

    private static final long serialVersionUID = -1962520940;

    /**
     * Setter for <code>diluv.authMFAToken.token</code>.
     */
    public void setToken(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>diluv.authMFAToken.token</code>.
     */
    public String getToken() {
        return (String) get(0);
    }

    /**
     * Setter for <code>diluv.authMFAToken.userID</code>.
     */
    public void setUserid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>diluv.authMFAToken.userID</code>.
     */
    public Long getUserid() {
        return (Long) get(1);
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
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Authmfatoken.AUTHMFATOKEN.TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return Authmfatoken.AUTHMFATOKEN.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthmfatokenRecord value1(String value) {
        setToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthmfatokenRecord value2(Long value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthmfatokenRecord values(String value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthmfatokenRecord
     */
    public AuthmfatokenRecord() {
        super(Authmfatoken.AUTHMFATOKEN);
    }

    /**
     * Create a detached, initialised AuthmfatokenRecord
     */
    public AuthmfatokenRecord(String token, Long userid) {
        super(Authmfatoken.AUTHMFATOKEN);

        set(0, token);
        set(1, userid);
    }
}
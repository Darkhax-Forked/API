/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables.records;


import com.diluv.api.models.tables.AuthVerifyToken;
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
public class AuthVerifyTokenRecord extends UpdatableRecordImpl<AuthVerifyTokenRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = -1835489482;

    /**
     * Setter for <code>diluv.AUTH_VERIFY_TOKEN.USER_ID</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>diluv.AUTH_VERIFY_TOKEN.USER_ID</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>diluv.AUTH_VERIFY_TOKEN.TOKEN</code>.
     */
    public void setToken(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>diluv.AUTH_VERIFY_TOKEN.TOKEN</code>.
     */
    public String getToken() {
        return (String) get(1);
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
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return AuthVerifyToken.AUTH_VERIFY_TOKEN.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return AuthVerifyToken.AUTH_VERIFY_TOKEN.TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthVerifyTokenRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthVerifyTokenRecord value2(String value) {
        setToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthVerifyTokenRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthVerifyTokenRecord
     */
    public AuthVerifyTokenRecord() {
        super(AuthVerifyToken.AUTH_VERIFY_TOKEN);
    }

    /**
     * Create a detached, initialised AuthVerifyTokenRecord
     */
    public AuthVerifyTokenRecord(Long userId, String token) {
        super(AuthVerifyToken.AUTH_VERIFY_TOKEN);

        set(0, userId);
        set(1, token);
    }
}
/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables.records;


import com.diluv.api.models.tables.Analyticsauthaccesstoken;
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
public class AnalyticsauthaccesstokenRecord extends UpdatableRecordImpl<AnalyticsauthaccesstokenRecord> implements Record5<Long, String, String, Timestamp, Long> {

    private static final long serialVersionUID = 1984853136;

    /**
     * Setter for <code>diluv.analyticsAuthAccessToken.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>diluv.analyticsAuthAccessToken.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>diluv.analyticsAuthAccessToken.token</code>.
     */
    public void setToken(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>diluv.analyticsAuthAccessToken.token</code>.
     */
    public String getToken() {
        return (String) get(1);
    }

    /**
     * Setter for <code>diluv.analyticsAuthAccessToken.refreshToken</code>.
     */
    public void setRefreshtoken(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>diluv.analyticsAuthAccessToken.refreshToken</code>.
     */
    public String getRefreshtoken() {
        return (String) get(2);
    }

    /**
     * Setter for <code>diluv.analyticsAuthAccessToken.createdAt</code>.
     */
    public void setCreatedat(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>diluv.analyticsAuthAccessToken.createdAt</code>.
     */
    public Timestamp getCreatedat() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>diluv.analyticsAuthAccessToken.userId</code>.
     */
    public void setUserid(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>diluv.analyticsAuthAccessToken.userId</code>.
     */
    public Long getUserid() {
        return (Long) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, String, Timestamp, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, String, Timestamp, Long> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN.TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN.REFRESHTOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN.CREATEDAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
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
    public String value3() {
        return getRefreshtoken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreatedat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsauthaccesstokenRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsauthaccesstokenRecord value2(String value) {
        setToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsauthaccesstokenRecord value3(String value) {
        setRefreshtoken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsauthaccesstokenRecord value4(Timestamp value) {
        setCreatedat(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsauthaccesstokenRecord value5(Long value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyticsauthaccesstokenRecord values(Long value1, String value2, String value3, Timestamp value4, Long value5) {
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
     * Create a detached AnalyticsauthaccesstokenRecord
     */
    public AnalyticsauthaccesstokenRecord() {
        super(Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN);
    }

    /**
     * Create a detached, initialised AnalyticsauthaccesstokenRecord
     */
    public AnalyticsauthaccesstokenRecord(Long id, String token, String refreshtoken, Timestamp createdat, Long userid) {
        super(Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN);

        set(0, id);
        set(1, token);
        set(2, refreshtoken);
        set(3, createdat);
        set(4, userid);
    }
}

/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.AuthaccesstokenRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
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
public class Authaccesstoken extends TableImpl<AuthaccesstokenRecord> {

    private static final long serialVersionUID = -279788331;

    /**
     * The reference instance of <code>diluv.authAccessToken</code>
     */
    public static final Authaccesstoken AUTHACCESSTOKEN = new Authaccesstoken();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthaccesstokenRecord> getRecordType() {
        return AuthaccesstokenRecord.class;
    }

    /**
     * The column <code>diluv.authAccessToken.token</code>.
     */
    public final TableField<AuthaccesstokenRecord, String> TOKEN = createField("token", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.authAccessToken.refreshToken</code>.
     */
    public final TableField<AuthaccesstokenRecord, String> REFRESHTOKEN = createField("refreshToken", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.authAccessToken.userID</code>.
     */
    public final TableField<AuthaccesstokenRecord, Long> USERID = createField("userID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.authAccessToken</code> table reference
     */
    public Authaccesstoken() {
        this("authAccessToken", null);
    }

    /**
     * Create an aliased <code>diluv.authAccessToken</code> table reference
     */
    public Authaccesstoken(String alias) {
        this(alias, AUTHACCESSTOKEN);
    }

    private Authaccesstoken(String alias, Table<AuthaccesstokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private Authaccesstoken(String alias, Table<AuthaccesstokenRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<AuthaccesstokenRecord> getPrimaryKey() {
        return Keys.KEY_AUTHACCESSTOKEN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AuthaccesstokenRecord>> getKeys() {
        return Arrays.<UniqueKey<AuthaccesstokenRecord>>asList(Keys.KEY_AUTHACCESSTOKEN_PRIMARY, Keys.KEY_AUTHACCESSTOKEN_REFRESHTOKEN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Authaccesstoken as(String alias) {
        return new Authaccesstoken(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Authaccesstoken rename(String name) {
        return new Authaccesstoken(name, null);
    }
}
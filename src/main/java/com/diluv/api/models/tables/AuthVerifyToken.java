/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.AuthVerifyTokenRecord;
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
public class AuthVerifyToken extends TableImpl<AuthVerifyTokenRecord> {

    private static final long serialVersionUID = 1602749787;

    /**
     * The reference instance of <code>diluv.AUTH_VERIFY_TOKEN</code>
     */
    public static final AuthVerifyToken AUTH_VERIFY_TOKEN = new AuthVerifyToken();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthVerifyTokenRecord> getRecordType() {
        return AuthVerifyTokenRecord.class;
    }

    /**
     * The column <code>diluv.AUTH_VERIFY_TOKEN.USER_ID</code>.
     */
    public final TableField<AuthVerifyTokenRecord, Long> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.AUTH_VERIFY_TOKEN.TOKEN</code>.
     */
    public final TableField<AuthVerifyTokenRecord, String> TOKEN = createField("TOKEN", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * Create a <code>diluv.AUTH_VERIFY_TOKEN</code> table reference
     */
    public AuthVerifyToken() {
        this("AUTH_VERIFY_TOKEN", null);
    }

    /**
     * Create an aliased <code>diluv.AUTH_VERIFY_TOKEN</code> table reference
     */
    public AuthVerifyToken(String alias) {
        this(alias, AUTH_VERIFY_TOKEN);
    }

    private AuthVerifyToken(String alias, Table<AuthVerifyTokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private AuthVerifyToken(String alias, Table<AuthVerifyTokenRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<AuthVerifyTokenRecord> getPrimaryKey() {
        return Keys.KEY_AUTH_VERIFY_TOKEN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AuthVerifyTokenRecord>> getKeys() {
        return Arrays.<UniqueKey<AuthVerifyTokenRecord>>asList(Keys.KEY_AUTH_VERIFY_TOKEN_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AuthVerifyTokenRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AuthVerifyTokenRecord, ?>>asList(Keys.AUTH_VERIFY_TOKEN_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthVerifyToken as(String alias) {
        return new AuthVerifyToken(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AuthVerifyToken rename(String name) {
        return new AuthVerifyToken(name, null);
    }
}

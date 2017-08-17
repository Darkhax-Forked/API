/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.AuthMfaTokenRecord;
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
public class AuthMfaToken extends TableImpl<AuthMfaTokenRecord> {

    private static final long serialVersionUID = -1955083882;

    /**
     * The reference instance of <code>diluv.AUTH_MFA_TOKEN</code>
     */
    public static final AuthMfaToken AUTH_MFA_TOKEN = new AuthMfaToken();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthMfaTokenRecord> getRecordType() {
        return AuthMfaTokenRecord.class;
    }

    /**
     * The column <code>diluv.AUTH_MFA_TOKEN.TOKEN</code>.
     */
    public final TableField<AuthMfaTokenRecord, String> TOKEN = createField("TOKEN", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>diluv.AUTH_MFA_TOKEN.USER_ID</code>.
     */
    public final TableField<AuthMfaTokenRecord, Long> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.AUTH_MFA_TOKEN</code> table reference
     */
    public AuthMfaToken() {
        this("AUTH_MFA_TOKEN", null);
    }

    /**
     * Create an aliased <code>diluv.AUTH_MFA_TOKEN</code> table reference
     */
    public AuthMfaToken(String alias) {
        this(alias, AUTH_MFA_TOKEN);
    }

    private AuthMfaToken(String alias, Table<AuthMfaTokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private AuthMfaToken(String alias, Table<AuthMfaTokenRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<AuthMfaTokenRecord> getPrimaryKey() {
        return Keys.KEY_AUTH_MFA_TOKEN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AuthMfaTokenRecord>> getKeys() {
        return Arrays.<UniqueKey<AuthMfaTokenRecord>>asList(Keys.KEY_AUTH_MFA_TOKEN_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AuthMfaTokenRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AuthMfaTokenRecord, ?>>asList(Keys.AUTH_MFA_TOKEN_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthMfaToken as(String alias) {
        return new AuthMfaToken(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AuthMfaToken rename(String name) {
        return new AuthMfaToken(name, null);
    }
}

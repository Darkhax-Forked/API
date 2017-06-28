/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.UserbetakeyRecord;
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
public class Userbetakey extends TableImpl<UserbetakeyRecord> {

    private static final long serialVersionUID = 1170838183;

    /**
     * The reference instance of <code>diluv.userBetaKey</code>
     */
    public static final Userbetakey USERBETAKEY = new Userbetakey();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserbetakeyRecord> getRecordType() {
        return UserbetakeyRecord.class;
    }

    /**
     * The column <code>diluv.userBetaKey.betaKey</code>.
     */
    public final TableField<UserbetakeyRecord, String> BETAKEY = createField("betaKey", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "");

    /**
     * The column <code>diluv.userBetaKey.valid</code>.
     */
    public final TableField<UserbetakeyRecord, Boolean> VALID = createField("valid", org.jooq.impl.SQLDataType.BIT.nullable(false), this, "");

    /**
     * The column <code>diluv.userBetaKey.createdAt</code>.
     */
    public final TableField<UserbetakeyRecord, Timestamp> CREATEDAT = createField("createdAt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>diluv.userBetaKey.userID</code>.
     */
    public final TableField<UserbetakeyRecord, Long> USERID = createField("userID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.userBetaKey.creationUserId</code>.
     */
    public final TableField<UserbetakeyRecord, Long> CREATIONUSERID = createField("creationUserId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.userBetaKey</code> table reference
     */
    public Userbetakey() {
        this("userBetaKey", null);
    }

    /**
     * Create an aliased <code>diluv.userBetaKey</code> table reference
     */
    public Userbetakey(String alias) {
        this(alias, USERBETAKEY);
    }

    private Userbetakey(String alias, Table<UserbetakeyRecord> aliased) {
        this(alias, aliased, null);
    }

    private Userbetakey(String alias, Table<UserbetakeyRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<UserbetakeyRecord> getPrimaryKey() {
        return Keys.KEY_USERBETAKEY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserbetakeyRecord>> getKeys() {
        return Arrays.<UniqueKey<UserbetakeyRecord>>asList(Keys.KEY_USERBETAKEY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UserbetakeyRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserbetakeyRecord, ?>>asList(Keys.USERBETAKEY_IBFK_1, Keys.USERBETAKEY_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Userbetakey as(String alias) {
        return new Userbetakey(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Userbetakey rename(String name) {
        return new Userbetakey(name, null);
    }
}

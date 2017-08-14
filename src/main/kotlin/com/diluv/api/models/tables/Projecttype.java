/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjecttypeRecord;
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
public class Projecttype extends TableImpl<ProjecttypeRecord> {

    private static final long serialVersionUID = -1650773950;

    /**
     * The reference instance of <code>diluv.projectType</code>
     */
    public static final Projecttype PROJECTTYPE = new Projecttype();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjecttypeRecord> getRecordType() {
        return ProjecttypeRecord.class;
    }

    /**
     * The column <code>diluv.projectType.id</code>.
     */
    public final TableField<ProjecttypeRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.projectType.name</code>.
     */
    public final TableField<ProjecttypeRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(300).nullable(false), this, "");

    /**
     * The column <code>diluv.projectType.description</code>.
     */
    public final TableField<ProjecttypeRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(2000).nullable(false), this, "");

    /**
     * The column <code>diluv.projectType.slug</code>.
     */
    public final TableField<ProjecttypeRecord, String> SLUG = createField("slug", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

    /**
     * The column <code>diluv.projectType.maxByteSize</code>.
     */
    public final TableField<ProjecttypeRecord, Long> MAXBYTESIZE = createField("maxByteSize", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.projectType.gameId</code>.
     */
    public final TableField<ProjecttypeRecord, Long> GAMEID = createField("gameId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.projectType</code> table reference
     */
    public Projecttype() {
        this("projectType", null);
    }

    /**
     * Create an aliased <code>diluv.projectType</code> table reference
     */
    public Projecttype(String alias) {
        this(alias, PROJECTTYPE);
    }

    private Projecttype(String alias, Table<ProjecttypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private Projecttype(String alias, Table<ProjecttypeRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ProjecttypeRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECTTYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjecttypeRecord> getPrimaryKey() {
        return Keys.KEY_PROJECTTYPE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjecttypeRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjecttypeRecord>>asList(Keys.KEY_PROJECTTYPE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjecttypeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjecttypeRecord, ?>>asList(Keys.PROJECTTYPE_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Projecttype as(String alias) {
        return new Projecttype(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Projecttype rename(String name) {
        return new Projecttype(name, null);
    }
}

/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjectRecord;
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
public class Project extends TableImpl<ProjectRecord> {

    private static final long serialVersionUID = -1908984522;

    /**
     * The reference instance of <code>diluv.PROJECT</code>
     */
    public static final Project PROJECT = new Project();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectRecord> getRecordType() {
        return ProjectRecord.class;
    }

    /**
     * The column <code>diluv.PROJECT.ID</code>.
     */
    public final TableField<ProjectRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT.NAME</code>.
     */
    public final TableField<ProjectRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT.SHORT_DESCRIPTION</code>.
     */
    public final TableField<ProjectRecord, String> SHORT_DESCRIPTION = createField("SHORT_DESCRIPTION", org.jooq.impl.SQLDataType.VARCHAR.length(300).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT.DESCRIPTION</code>.
     */
    public final TableField<ProjectRecord, String> DESCRIPTION = createField("DESCRIPTION", org.jooq.impl.SQLDataType.VARCHAR.length(5000).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT.DESCRIPTION_TYPE</code>.
     */
    public final TableField<ProjectRecord, String> DESCRIPTION_TYPE = createField("DESCRIPTION_TYPE", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT.SLUG</code>.
     */
    public final TableField<ProjectRecord, String> SLUG = createField("SLUG", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT.LOGO</code>.
     */
    public final TableField<ProjectRecord, String> LOGO = createField("LOGO", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT.TOTAL_DOWNLOADS</code>.
     */
    public final TableField<ProjectRecord, Long> TOTAL_DOWNLOADS = createField("TOTAL_DOWNLOADS", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>diluv.PROJECT.DELETED</code>.
     */
    public final TableField<ProjectRecord, Boolean> DELETED = createField("DELETED", org.jooq.impl.SQLDataType.BIT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * The column <code>diluv.PROJECT.UPDATED_AT</code>.
     */
    public final TableField<ProjectRecord, Timestamp> UPDATED_AT = createField("UPDATED_AT", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>diluv.PROJECT.CREATED_AT</code>.
     */
    public final TableField<ProjectRecord, Timestamp> CREATED_AT = createField("CREATED_AT", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>diluv.PROJECT.PROJECT_TYPE_ID</code>.
     */
    public final TableField<ProjectRecord, Long> PROJECT_TYPE_ID = createField("PROJECT_TYPE_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT.USER_ID</code>.
     */
    public final TableField<ProjectRecord, Long> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.PROJECT</code> table reference
     */
    public Project() {
        this("PROJECT", null);
    }

    /**
     * Create an aliased <code>diluv.PROJECT</code> table reference
     */
    public Project(String alias) {
        this(alias, PROJECT);
    }

    private Project(String alias, Table<ProjectRecord> aliased) {
        this(alias, aliased, null);
    }

    private Project(String alias, Table<ProjectRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ProjectRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjectRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectRecord>>asList(Keys.KEY_PROJECT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectRecord, ?>>asList(Keys.PROJECT_IBFK_1, Keys.PROJECT_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project as(String alias) {
        return new Project(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Project rename(String name) {
        return new Project(name, null);
    }
}
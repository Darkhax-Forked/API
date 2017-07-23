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

    private static final long serialVersionUID = -486451747;

    /**
     * The reference instance of <code>diluv.project</code>
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
     * The column <code>diluv.project.id</code>.
     */
    public final TableField<ProjectRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.project.name</code>.
     */
    public final TableField<ProjectRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.project.shortDescription</code>.
     */
    public final TableField<ProjectRecord, String> SHORTDESCRIPTION = createField("shortDescription", org.jooq.impl.SQLDataType.VARCHAR.length(300).nullable(false), this, "");

    /**
     * The column <code>diluv.project.description</code>.
     */
    public final TableField<ProjectRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(5000).nullable(false), this, "");

    /**
     * The column <code>diluv.project.descriptionType</code>.
     */
    public final TableField<ProjectRecord, String> DESCRIPTIONTYPE = createField("descriptionType", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.project.slug</code>.
     */
    public final TableField<ProjectRecord, String> SLUG = createField("slug", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

    /**
     * The column <code>diluv.project.logo</code>.
     */
    public final TableField<ProjectRecord, String> LOGO = createField("logo", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.project.totalDownloads</code>.
     */
    public final TableField<ProjectRecord, Long> TOTALDOWNLOADS = createField("totalDownloads", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>diluv.project.deleted</code>.
     */
    public final TableField<ProjectRecord, Boolean> DELETED = createField("deleted", org.jooq.impl.SQLDataType.BIT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * The column <code>diluv.project.updatedAt</code>.
     */
    public final TableField<ProjectRecord, Timestamp> UPDATEDAT = createField("updatedAt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>diluv.project.createdAt</code>.
     */
    public final TableField<ProjectRecord, Timestamp> CREATEDAT = createField("createdAt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>diluv.project.projectTypeId</code>.
     */
    public final TableField<ProjectRecord, Long> PROJECTTYPEID = createField("projectTypeId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.project.userId</code>.
     */
    public final TableField<ProjectRecord, Long> USERID = createField("userId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.project</code> table reference
     */
    public Project() {
        this("project", null);
    }

    /**
     * Create an aliased <code>diluv.project</code> table reference
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
        return Arrays.<ForeignKey<ProjectRecord, ?>>asList(Keys.PROJECT_IBFK_1);
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

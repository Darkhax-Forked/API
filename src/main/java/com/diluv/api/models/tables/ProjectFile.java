/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjectFileRecord;
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
public class ProjectFile extends TableImpl<ProjectFileRecord> {

    private static final long serialVersionUID = 1750682229;

    /**
     * The reference instance of <code>diluv.PROJECT_FILE</code>
     */
    public static final ProjectFile PROJECT_FILE = new ProjectFile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectFileRecord> getRecordType() {
        return ProjectFileRecord.class;
    }

    /**
     * The column <code>diluv.PROJECT_FILE.ID</code>.
     */
    public final TableField<ProjectFileRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.SHA256</code>.
     */
    public final TableField<ProjectFileRecord, String> SHA256 = createField("SHA256", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.FILE_NAME</code>.
     */
    public final TableField<ProjectFileRecord, String> FILE_NAME = createField("FILE_NAME", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.DISPLAY_NAME</code>.
     */
    public final TableField<ProjectFileRecord, String> DISPLAY_NAME = createField("DISPLAY_NAME", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.SIZE</code>.
     */
    public final TableField<ProjectFileRecord, Long> SIZE = createField("SIZE", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.DOWNLOADS</code>.
     */
    public final TableField<ProjectFileRecord, Long> DOWNLOADS = createField("DOWNLOADS", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.RELEASE_TYPE</code>.
     */
    public final TableField<ProjectFileRecord, String> RELEASE_TYPE = createField("RELEASE_TYPE", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.CREATED_AT</code>.
     */
    public final TableField<ProjectFileRecord, Timestamp> CREATED_AT = createField("CREATED_AT", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.PUBLIC</code>.
     */
    public final TableField<ProjectFileRecord, Boolean> PUBLIC = createField("PUBLIC", org.jooq.impl.SQLDataType.BIT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.PARENT_ID</code>.
     */
    public final TableField<ProjectFileRecord, Long> PARENT_ID = createField("PARENT_ID", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.PROJECT_ID</code>.
     */
    public final TableField<ProjectFileRecord, Long> PROJECT_ID = createField("PROJECT_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE.USER_ID</code>.
     */
    public final TableField<ProjectFileRecord, Long> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.PROJECT_FILE</code> table reference
     */
    public ProjectFile() {
        this("PROJECT_FILE", null);
    }

    /**
     * Create an aliased <code>diluv.PROJECT_FILE</code> table reference
     */
    public ProjectFile(String alias) {
        this(alias, PROJECT_FILE);
    }

    private ProjectFile(String alias, Table<ProjectFileRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProjectFile(String alias, Table<ProjectFileRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ProjectFileRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECT_FILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjectFileRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_FILE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectFileRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectFileRecord>>asList(Keys.KEY_PROJECT_FILE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectFileRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectFileRecord, ?>>asList(Keys.PROJECT_FILE_IBFK_1, Keys.PROJECT_FILE_IBFK_2, Keys.PROJECT_FILE_IBFK_3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFile as(String alias) {
        return new ProjectFile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectFile rename(String name) {
        return new ProjectFile(name, null);
    }
}
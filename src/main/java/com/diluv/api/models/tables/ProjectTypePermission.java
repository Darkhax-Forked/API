/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjectTypePermissionRecord;
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
public class ProjectTypePermission extends TableImpl<ProjectTypePermissionRecord> {

    private static final long serialVersionUID = -134065035;

    /**
     * The reference instance of <code>diluv.PROJECT_TYPE_PERMISSION</code>
     */
    public static final ProjectTypePermission PROJECT_TYPE_PERMISSION = new ProjectTypePermission();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectTypePermissionRecord> getRecordType() {
        return ProjectTypePermissionRecord.class;
    }

    /**
     * The column <code>diluv.PROJECT_TYPE_PERMISSION.ID</code>.
     */
    public final TableField<ProjectTypePermissionRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_TYPE_PERMISSION.PERMISSION_CREATE</code>.
     */
    public final TableField<ProjectTypePermissionRecord, Integer> PERMISSION_CREATE = createField("PERMISSION_CREATE", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>diluv.PROJECT_TYPE_PERMISSION</code> table reference
     */
    public ProjectTypePermission() {
        this("PROJECT_TYPE_PERMISSION", null);
    }

    /**
     * Create an aliased <code>diluv.PROJECT_TYPE_PERMISSION</code> table reference
     */
    public ProjectTypePermission(String alias) {
        this(alias, PROJECT_TYPE_PERMISSION);
    }

    private ProjectTypePermission(String alias, Table<ProjectTypePermissionRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProjectTypePermission(String alias, Table<ProjectTypePermissionRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ProjectTypePermissionRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECT_TYPE_PERMISSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjectTypePermissionRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_TYPE_PERMISSION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectTypePermissionRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectTypePermissionRecord>>asList(Keys.KEY_PROJECT_TYPE_PERMISSION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectTypePermissionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectTypePermissionRecord, ?>>asList(Keys.PROJECT_TYPE_PERMISSION_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectTypePermission as(String alias) {
        return new ProjectTypePermission(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectTypePermission rename(String name) {
        return new ProjectTypePermission(name, null);
    }
}

/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjecttypepermissionRecord;
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
public class Projecttypepermission extends TableImpl<ProjecttypepermissionRecord> {

    private static final long serialVersionUID = 732696450;

    /**
     * The reference instance of <code>diluv.projectTypePermission</code>
     */
    public static final Projecttypepermission PROJECTTYPEPERMISSION = new Projecttypepermission();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjecttypepermissionRecord> getRecordType() {
        return ProjecttypepermissionRecord.class;
    }

    /**
     * The column <code>diluv.projectTypePermission.id</code>.
     */
    public final TableField<ProjecttypepermissionRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.projectTypePermission.permissionCreate</code>.
     */
    public final TableField<ProjecttypepermissionRecord, Integer> PERMISSIONCREATE = createField("permissionCreate", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>diluv.projectTypePermission</code> table reference
     */
    public Projecttypepermission() {
        this("projectTypePermission", null);
    }

    /**
     * Create an aliased <code>diluv.projectTypePermission</code> table reference
     */
    public Projecttypepermission(String alias) {
        this(alias, PROJECTTYPEPERMISSION);
    }

    private Projecttypepermission(String alias, Table<ProjecttypepermissionRecord> aliased) {
        this(alias, aliased, null);
    }

    private Projecttypepermission(String alias, Table<ProjecttypepermissionRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ProjecttypepermissionRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECTTYPEPERMISSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjecttypepermissionRecord> getPrimaryKey() {
        return Keys.KEY_PROJECTTYPEPERMISSION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjecttypepermissionRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjecttypepermissionRecord>>asList(Keys.KEY_PROJECTTYPEPERMISSION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjecttypepermissionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjecttypepermissionRecord, ?>>asList(Keys.PROJECTTYPEPERMISSION_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Projecttypepermission as(String alias) {
        return new Projecttypepermission(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Projecttypepermission rename(String name) {
        return new Projecttypepermission(name, null);
    }
}

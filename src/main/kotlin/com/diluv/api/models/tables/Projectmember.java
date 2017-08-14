/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjectmemberRecord;
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
public class Projectmember extends TableImpl<ProjectmemberRecord> {

    private static final long serialVersionUID = 827952039;

    /**
     * The reference instance of <code>diluv.projectMember</code>
     */
    public static final Projectmember PROJECTMEMBER = new Projectmember();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectmemberRecord> getRecordType() {
        return ProjectmemberRecord.class;
    }

    /**
     * The column <code>diluv.projectMember.id</code>.
     */
    public final TableField<ProjectmemberRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.projectMember.role</code>.
     */
    public final TableField<ProjectmemberRecord, String> ROLE = createField("role", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * The column <code>diluv.projectMember.permission</code>.
     */
    public final TableField<ProjectmemberRecord, Integer> PERMISSION = createField("permission", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>diluv.projectMember.createdAt</code>.
     */
    public final TableField<ProjectmemberRecord, Timestamp> CREATEDAT = createField("createdAt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>diluv.projectMember.projectId</code>.
     */
    public final TableField<ProjectmemberRecord, Long> PROJECTID = createField("projectId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.projectMember.userId</code>.
     */
    public final TableField<ProjectmemberRecord, Long> USERID = createField("userId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.projectMember</code> table reference
     */
    public Projectmember() {
        this("projectMember", null);
    }

    /**
     * Create an aliased <code>diluv.projectMember</code> table reference
     */
    public Projectmember(String alias) {
        this(alias, PROJECTMEMBER);
    }

    private Projectmember(String alias, Table<ProjectmemberRecord> aliased) {
        this(alias, aliased, null);
    }

    private Projectmember(String alias, Table<ProjectmemberRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ProjectmemberRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECTMEMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjectmemberRecord> getPrimaryKey() {
        return Keys.KEY_PROJECTMEMBER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectmemberRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectmemberRecord>>asList(Keys.KEY_PROJECTMEMBER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectmemberRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectmemberRecord, ?>>asList(Keys.PROJECTMEMBER_IBFK_2, Keys.PROJECTMEMBER_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Projectmember as(String alias) {
        return new Projectmember(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Projectmember rename(String name) {
        return new Projectmember(name, null);
    }
}

/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjectfileprocessingRecord;
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
public class Projectfileprocessing extends TableImpl<ProjectfileprocessingRecord> {

    private static final long serialVersionUID = -1696108147;

    /**
     * The reference instance of <code>diluv.projectFileProcessing</code>
     */
    public static final Projectfileprocessing PROJECTFILEPROCESSING = new Projectfileprocessing();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectfileprocessingRecord> getRecordType() {
        return ProjectfileprocessingRecord.class;
    }

    /**
     * The column <code>diluv.projectFileProcessing.projectFileId</code>.
     */
    public final TableField<ProjectfileprocessingRecord, Long> PROJECTFILEID = createField("projectFileId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.projectFileProcessing.status</code>.
     */
    public final TableField<ProjectfileprocessingRecord, String> STATUS = createField("status", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>diluv.projectFileProcessing.startedAt</code>.
     */
    public final TableField<ProjectfileprocessingRecord, Timestamp> STARTEDAT = createField("startedAt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>diluv.projectFileProcessing</code> table reference
     */
    public Projectfileprocessing() {
        this("projectFileProcessing", null);
    }

    /**
     * Create an aliased <code>diluv.projectFileProcessing</code> table reference
     */
    public Projectfileprocessing(String alias) {
        this(alias, PROJECTFILEPROCESSING);
    }

    private Projectfileprocessing(String alias, Table<ProjectfileprocessingRecord> aliased) {
        this(alias, aliased, null);
    }

    private Projectfileprocessing(String alias, Table<ProjectfileprocessingRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<ProjectfileprocessingRecord> getPrimaryKey() {
        return Keys.KEY_PROJECTFILEPROCESSING_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectfileprocessingRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectfileprocessingRecord>>asList(Keys.KEY_PROJECTFILEPROCESSING_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectfileprocessingRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectfileprocessingRecord, ?>>asList(Keys.PROJECTFILEPROCESSING_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Projectfileprocessing as(String alias) {
        return new Projectfileprocessing(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Projectfileprocessing rename(String name) {
        return new Projectfileprocessing(name, null);
    }
}
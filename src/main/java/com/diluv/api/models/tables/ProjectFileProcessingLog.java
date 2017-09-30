/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjectFileProcessingLogRecord;
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
public class ProjectFileProcessingLog extends TableImpl<ProjectFileProcessingLogRecord> {

    private static final long serialVersionUID = 539358336;

    /**
     * The reference instance of <code>diluv.PROJECT_FILE_PROCESSING_LOG</code>
     */
    public static final ProjectFileProcessingLog PROJECT_FILE_PROCESSING_LOG = new ProjectFileProcessingLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectFileProcessingLogRecord> getRecordType() {
        return ProjectFileProcessingLogRecord.class;
    }

    /**
     * The column <code>diluv.PROJECT_FILE_PROCESSING_LOG.PROJECT_FILE_ID</code>.
     */
    public final TableField<ProjectFileProcessingLogRecord, Long> PROJECT_FILE_ID = createField("PROJECT_FILE_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE_PROCESSING_LOG.STATUS_ID</code>.
     */
    public final TableField<ProjectFileProcessingLogRecord, Integer> STATUS_ID = createField("STATUS_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE_PROCESSING_LOG.LOGS</code>.
     */
    public final TableField<ProjectFileProcessingLogRecord, String> LOGS = createField("LOGS", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_FILE_PROCESSING_LOG.CREATED_AT</code>.
     */
    public final TableField<ProjectFileProcessingLogRecord, Timestamp> CREATED_AT = createField("CREATED_AT", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>diluv.PROJECT_FILE_PROCESSING_LOG</code> table reference
     */
    public ProjectFileProcessingLog() {
        this("PROJECT_FILE_PROCESSING_LOG", null);
    }

    /**
     * Create an aliased <code>diluv.PROJECT_FILE_PROCESSING_LOG</code> table reference
     */
    public ProjectFileProcessingLog(String alias) {
        this(alias, PROJECT_FILE_PROCESSING_LOG);
    }

    private ProjectFileProcessingLog(String alias, Table<ProjectFileProcessingLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProjectFileProcessingLog(String alias, Table<ProjectFileProcessingLogRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<ProjectFileProcessingLogRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_FILE_PROCESSING_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectFileProcessingLogRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectFileProcessingLogRecord>>asList(Keys.KEY_PROJECT_FILE_PROCESSING_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectFileProcessingLogRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectFileProcessingLogRecord, ?>>asList(Keys.PROJECT_FILE_PROCESSING_LOG_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileProcessingLog as(String alias) {
        return new ProjectFileProcessingLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectFileProcessingLog rename(String name) {
        return new ProjectFileProcessingLog(name, null);
    }
}
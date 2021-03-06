/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables.records;


import com.diluv.api.models.tables.ProjectFileGameVersion;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


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
public class ProjectFileGameVersionRecord extends UpdatableRecordImpl<ProjectFileGameVersionRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = 742355548;

    /**
     * Setter for <code>diluv.PROJECT_FILE_GAME_VERSION.PROJECT_FILE_ID</code>.
     */
    public void setProjectFileId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE_GAME_VERSION.PROJECT_FILE_ID</code>.
     */
    public Long getProjectFileId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE_GAME_VERSION.PROJECT_VERSION_ID</code>.
     */
    public void setProjectVersionId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE_GAME_VERSION.PROJECT_VERSION_ID</code>.
     */
    public Long getProjectVersionId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION.PROJECT_FILE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION.PROJECT_VERSION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getProjectFileId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getProjectVersionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileGameVersionRecord value1(Long value) {
        setProjectFileId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileGameVersionRecord value2(Long value) {
        setProjectVersionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileGameVersionRecord values(Long value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProjectFileGameVersionRecord
     */
    public ProjectFileGameVersionRecord() {
        super(ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION);
    }

    /**
     * Create a detached, initialised ProjectFileGameVersionRecord
     */
    public ProjectFileGameVersionRecord(Long projectFileId, Long projectVersionId) {
        super(ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION);

        set(0, projectFileId);
        set(1, projectVersionId);
    }
}

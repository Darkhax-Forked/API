/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables.records;


import com.diluv.api.models.tables.Projectfilegameversion;
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
public class ProjectfilegameversionRecord extends UpdatableRecordImpl<ProjectfilegameversionRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = -662600740;

    /**
     * Setter for <code>diluv.projectFileGameVersion.projectFileId</code>.
     */
    public void setProjectfileid(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>diluv.projectFileGameVersion.projectFileId</code>.
     */
    public Long getProjectfileid() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>diluv.projectFileGameVersion.projectVersionId</code>.
     */
    public void setProjectversionid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>diluv.projectFileGameVersion.projectVersionId</code>.
     */
    public Long getProjectversionid() {
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
        return Projectfilegameversion.PROJECTFILEGAMEVERSION.PROJECTFILEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return Projectfilegameversion.PROJECTFILEGAMEVERSION.PROJECTVERSIONID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getProjectfileid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getProjectversionid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectfilegameversionRecord value1(Long value) {
        setProjectfileid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectfilegameversionRecord value2(Long value) {
        setProjectversionid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectfilegameversionRecord values(Long value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProjectfilegameversionRecord
     */
    public ProjectfilegameversionRecord() {
        super(Projectfilegameversion.PROJECTFILEGAMEVERSION);
    }

    /**
     * Create a detached, initialised ProjectfilegameversionRecord
     */
    public ProjectfilegameversionRecord(Long projectfileid, Long projectversionid) {
        super(Projectfilegameversion.PROJECTFILEGAMEVERSION);

        set(0, projectfileid);
        set(1, projectversionid);
    }
}
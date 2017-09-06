/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables.records;


import com.diluv.api.models.tables.ProjectFile;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


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
public class ProjectFileRecord extends UpdatableRecordImpl<ProjectFileRecord> implements Record12<Long, String, String, String, Long, Long, String, Timestamp, Boolean, Long, Long, Long> {

    private static final long serialVersionUID = -1684727237;

    /**
     * Setter for <code>diluv.PROJECT_FILE.ID</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.ID</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.SHA256</code>.
     */
    public void setSha256(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.SHA256</code>.
     */
    public String getSha256() {
        return (String) get(1);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.FILE_NAME</code>.
     */
    public void setFileName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.FILE_NAME</code>.
     */
    public String getFileName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.DISPLAY_NAME</code>.
     */
    public void setDisplayName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.DISPLAY_NAME</code>.
     */
    public String getDisplayName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.SIZE</code>.
     */
    public void setSize(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.SIZE</code>.
     */
    public Long getSize() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.DOWNLOADS</code>.
     */
    public void setDownloads(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.DOWNLOADS</code>.
     */
    public Long getDownloads() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.RELEASE_TYPE</code>.
     */
    public void setReleaseType(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.RELEASE_TYPE</code>.
     */
    public String getReleaseType() {
        return (String) get(6);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.CREATED_AT</code>.
     */
    public void setCreatedAt(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.CREATED_AT</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.PUBLIC</code>.
     */
    public void setPublic(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.PUBLIC</code>.
     */
    public Boolean getPublic() {
        return (Boolean) get(8);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.PARENT_ID</code>.
     */
    public void setParentId(Long value) {
        set(9, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.PARENT_ID</code>.
     */
    public Long getParentId() {
        return (Long) get(9);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.PROJECT_ID</code>.
     */
    public void setProjectId(Long value) {
        set(10, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.PROJECT_ID</code>.
     */
    public Long getProjectId() {
        return (Long) get(10);
    }

    /**
     * Setter for <code>diluv.PROJECT_FILE.USER_ID</code>.
     */
    public void setUserId(Long value) {
        set(11, value);
    }

    /**
     * Getter for <code>diluv.PROJECT_FILE.USER_ID</code>.
     */
    public Long getUserId() {
        return (Long) get(11);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Long, String, String, String, Long, Long, String, Timestamp, Boolean, Long, Long, Long> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Long, String, String, String, Long, Long, String, Timestamp, Boolean, Long, Long, Long> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return ProjectFile.PROJECT_FILE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ProjectFile.PROJECT_FILE.SHA256;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ProjectFile.PROJECT_FILE.FILE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ProjectFile.PROJECT_FILE.DISPLAY_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return ProjectFile.PROJECT_FILE.SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field6() {
        return ProjectFile.PROJECT_FILE.DOWNLOADS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return ProjectFile.PROJECT_FILE.RELEASE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return ProjectFile.PROJECT_FILE.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field9() {
        return ProjectFile.PROJECT_FILE.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field10() {
        return ProjectFile.PROJECT_FILE.PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field11() {
        return ProjectFile.PROJECT_FILE.PROJECT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field12() {
        return ProjectFile.PROJECT_FILE.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getSha256();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getFileName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDisplayName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value6() {
        return getDownloads();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getReleaseType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value9() {
        return getPublic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value10() {
        return getParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value11() {
        return getProjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value12() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value2(String value) {
        setSha256(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value3(String value) {
        setFileName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value4(String value) {
        setDisplayName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value5(Long value) {
        setSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value6(Long value) {
        setDownloads(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value7(String value) {
        setReleaseType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value8(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value9(Boolean value) {
        setPublic(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value10(Long value) {
        setParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value11(Long value) {
        setProjectId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord value12(Long value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectFileRecord values(Long value1, String value2, String value3, String value4, Long value5, Long value6, String value7, Timestamp value8, Boolean value9, Long value10, Long value11, Long value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProjectFileRecord
     */
    public ProjectFileRecord() {
        super(ProjectFile.PROJECT_FILE);
    }

    /**
     * Create a detached, initialised ProjectFileRecord
     */
    public ProjectFileRecord(Long id, String sha256, String fileName, String displayName, Long size, Long downloads, String releaseType, Timestamp createdAt, Boolean public_, Long parentId, Long projectId, Long userId) {
        super(ProjectFile.PROJECT_FILE);

        set(0, id);
        set(1, sha256);
        set(2, fileName);
        set(3, displayName);
        set(4, size);
        set(5, downloads);
        set(6, releaseType);
        set(7, createdAt);
        set(8, public_);
        set(9, parentId);
        set(10, projectId);
        set(11, userId);
    }
}

/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.ProjectCategoryRecord;
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
public class ProjectCategory extends TableImpl<ProjectCategoryRecord> {

    private static final long serialVersionUID = -1992527588;

    /**
     * The reference instance of <code>diluv.PROJECT_CATEGORY</code>
     */
    public static final ProjectCategory PROJECT_CATEGORY = new ProjectCategory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectCategoryRecord> getRecordType() {
        return ProjectCategoryRecord.class;
    }

    /**
     * The column <code>diluv.PROJECT_CATEGORY.PROJECT_ID</code>.
     */
    public final TableField<ProjectCategoryRecord, Long> PROJECT_ID = createField("PROJECT_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.PROJECT_CATEGORY.PROJECT_TYPE_CATEGORY_ID</code>.
     */
    public final TableField<ProjectCategoryRecord, Long> PROJECT_TYPE_CATEGORY_ID = createField("PROJECT_TYPE_CATEGORY_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>diluv.PROJECT_CATEGORY</code> table reference
     */
    public ProjectCategory() {
        this("PROJECT_CATEGORY", null);
    }

    /**
     * Create an aliased <code>diluv.PROJECT_CATEGORY</code> table reference
     */
    public ProjectCategory(String alias) {
        this(alias, PROJECT_CATEGORY);
    }

    private ProjectCategory(String alias, Table<ProjectCategoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProjectCategory(String alias, Table<ProjectCategoryRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<ProjectCategoryRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_CATEGORY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectCategoryRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectCategoryRecord>>asList(Keys.KEY_PROJECT_CATEGORY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectCategoryRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectCategoryRecord, ?>>asList(Keys.PROJECT_CATEGORY_IBFK_1, Keys.PROJECT_CATEGORY_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectCategory as(String alias) {
        return new ProjectCategory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectCategory rename(String name) {
        return new ProjectCategory(name, null);
    }
}

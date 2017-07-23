/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models.tables;


import com.diluv.api.models.Diluv;
import com.diluv.api.models.Keys;
import com.diluv.api.models.tables.records.GameRecord;
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
public class Game extends TableImpl<GameRecord> {

    private static final long serialVersionUID = -502237312;

    /**
     * The reference instance of <code>diluv.game</code>
     */
    public static final Game GAME = new Game();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GameRecord> getRecordType() {
        return GameRecord.class;
    }

    /**
     * The column <code>diluv.game.id</code>.
     */
    public final TableField<GameRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>diluv.game.name</code>.
     */
    public final TableField<GameRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

    /**
     * The column <code>diluv.game.website</code>.
     */
    public final TableField<GameRecord, String> WEBSITE = createField("website", org.jooq.impl.SQLDataType.VARCHAR.length(300).nullable(false), this, "");

    /**
     * The column <code>diluv.game.description</code>.
     */
    public final TableField<GameRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(2000).nullable(false), this, "");

    /**
     * The column <code>diluv.game.slug</code>.
     */
    public final TableField<GameRecord, String> SLUG = createField("slug", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

    /**
     * Create a <code>diluv.game</code> table reference
     */
    public Game() {
        this("game", null);
    }

    /**
     * Create an aliased <code>diluv.game</code> table reference
     */
    public Game(String alias) {
        this(alias, GAME);
    }

    private Game(String alias, Table<GameRecord> aliased) {
        this(alias, aliased, null);
    }

    private Game(String alias, Table<GameRecord> aliased, Field<?>[] parameters) {
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
    public Identity<GameRecord, Long> getIdentity() {
        return Keys.IDENTITY_GAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GameRecord> getPrimaryKey() {
        return Keys.KEY_GAME_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GameRecord>> getKeys() {
        return Arrays.<UniqueKey<GameRecord>>asList(Keys.KEY_GAME_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game as(String alias) {
        return new Game(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Game rename(String name) {
        return new Game(name, null);
    }
}

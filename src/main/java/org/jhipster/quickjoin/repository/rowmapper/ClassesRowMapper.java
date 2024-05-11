package org.jhipster.quickjoin.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.jhipster.quickjoin.domain.Classes;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Classes}, with proper type conversions.
 */
@Service
public class ClassesRowMapper implements BiFunction<Row, String, Classes> {

    private final ColumnConverter converter;

    public ClassesRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Classes} stored in the database.
     */
    @Override
    public Classes apply(Row row, String prefix) {
        Classes entity = new Classes();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setTecher_name(converter.fromRow(row, prefix + "_techer_name", String.class));
        entity.setPrice(converter.fromRow(row, prefix + "_price", Integer.class));
        entity.setLocation(converter.fromRow(row, prefix + "_location", String.class));
        entity.setDuration(converter.fromRow(row, prefix + "_duration", Integer.class));
        return entity;
    }
}

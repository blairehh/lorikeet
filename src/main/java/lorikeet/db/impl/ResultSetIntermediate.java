package lorikeet.db.impl;


import lorikeet.Opt;
import lorikeet.db.Intermediate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

// @TODO log these exceptions
public class ResultSetIntermediate implements Intermediate {

    private static final Logger log = LoggerFactory.getLogger(ResultSetIntermediate.class);

    private final ResultSet resultSet;

    public ResultSetIntermediate(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public Opt<String> string(String identifier) {
        try {
            return Opt.ofNullable(this.resultSet.getString(identifier));
        } catch (SQLException e) {
            log.warn("could not extract string from identifier {}", identifier, e);
            return Opt.empty();
        }
    }

    @Override
    public Opt<Number> number(String identifier) {
        try {
            final Object value = this.resultSet.getObject(identifier);
            if (value == null) {
                return Opt.empty();
            }
            if (!(value instanceof Number)) {
                return Opt.empty();
            }
            return Opt.of((Number)value);
        } catch (SQLException e) {
            log.warn("could not extract number from identifier {}", identifier, e);
            return Opt.empty();
        }
    }

    @Override
    public Opt<Integer> integer(String identifier) {
        return this.number(identifier)
            .map(Number::intValue);
    }

    @Override
    public Opt<Double> decimal(String identifier) {
        return this.number(identifier)
            .map(Number::doubleValue);
    }

    @Override
    public Opt<Boolean> bool(String identifier) {
        try {
            final Object value = this.resultSet.getObject(identifier);
            if (value == null) {
                return Opt.empty();
            }
            if (!(value instanceof Boolean)) {
                return Opt.empty();
            }
            return Opt.of((Boolean) value);
        } catch (SQLException e) {
            log.warn("could not extract boolean from identifier {}", identifier, e);
            return Opt.empty();
        }
    }
}

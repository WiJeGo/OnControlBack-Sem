package com.oncontrol.oncontrolbackend.shared.infrastructure.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SnakeCaseWithPluralizedTablePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String tableName = convertToSnakeCase(name.getText());
        String pluralizedTableName = pluralize(tableName);
        return Identifier.toIdentifier(pluralizedTableName, name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return Identifier.toIdentifier(convertToSnakeCase(name.getText()), name.isQuoted());
    }

    private String convertToSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    private String pluralize(String word) {
        // Simple pluralization rules
        if (word.endsWith("y")) {
            return word.substring(0, word.length() - 1) + "ies";
        } else if (word.endsWith("s") || word.endsWith("sh") || word.endsWith("ch") || word.endsWith("x") || word.endsWith("z")) {
            return word + "es";
        } else {
            return word + "s";
        }
    }
}

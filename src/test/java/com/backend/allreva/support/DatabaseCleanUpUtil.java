package com.backend.allreva.support;

public final class DatabaseCleanUpUtil {

    public static String getDeleteSql(String tableName) {
        return "DELETE FROM " + tableName;
    }

    public static String getMysqlAutoIncrementResetSql(String tableName) {
        return "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
    }
}

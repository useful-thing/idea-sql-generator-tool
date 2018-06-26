package org.yseasony.sqlgenerator.children;

import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.SqlGenerator;
import org.yseasony.sqlgenerator.TableInfo;
import org.yseasony.sqlgenerator.Util;


public class InsertSqlGeneratorAction extends BaseSqlGenerator {

    public InsertSqlGeneratorAction() {
        super("insert sql generator");
    }

    public InsertSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "INSERT";
    }

    @Override
    public String getSqlTemplate() {
        return "INSERT INTO $TABLE_NAME$ ($COLUMN_LIST$) VALUES ($INSERT_VALUES$)";
    }

    public static class NamedPlaceholderSqlGeneratorAction extends InsertSqlGeneratorAction {

        String prefix;
        String suffix;

        public NamedPlaceholderSqlGeneratorAction(String prefix, String suffix) {
            super("insert sql generator    " + prefix + "param" + suffix);
            this.prefix = prefix;
            this.suffix = suffix;
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                public String getInsertValues() {
                    return Util.appendColumns(tableInfo.getColumnsName(), ",", prefix, suffix, false, true);
                }
            };
        }
    }
}

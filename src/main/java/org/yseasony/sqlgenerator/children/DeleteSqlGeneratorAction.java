package org.yseasony.sqlgenerator.children;

import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.SqlGenerator;
import org.yseasony.sqlgenerator.TableInfo;
import org.yseasony.sqlgenerator.Util;

public class DeleteSqlGeneratorAction extends BaseSqlGenerator {

    public DeleteSqlGeneratorAction() {
        super("delete sql generator");
    }

    public DeleteSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "DELETE";
    }

    @Override
    protected String getSqlTemplate() {
        return "DELETE FROM $TABLE_NAME$ $WHERE_CLAUSE$";
    }

    public static class NamedPlaceholderSqlGeneratorAction extends DeleteSqlGeneratorAction {
        String prefix;
        String suffix;

        public NamedPlaceholderSqlGeneratorAction(String prefix, String suffix) {
            super("delete sql generator    " + prefix + "param" + suffix);
            this.prefix = prefix;
            this.suffix = suffix;
        }
        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                public String getWhereClause() {
                    return Util.makeNamedPlaceholderWhereClause(tableInfo.getPrimaryKeys(), prefix, suffix);
                }
            };
        }
    }
}

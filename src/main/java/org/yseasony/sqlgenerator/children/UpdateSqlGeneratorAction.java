package org.yseasony.sqlgenerator.children;


import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.SqlGenerator;
import org.yseasony.sqlgenerator.TableInfo;
import org.yseasony.sqlgenerator.Util;

public class UpdateSqlGeneratorAction extends BaseSqlGenerator {

    public UpdateSqlGeneratorAction() {
        super("update sql generator");
    }

    public UpdateSqlGeneratorAction(
        @Nullable
            String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "UPDATE";
    }

    @Override
    protected String getSqlTemplate() {
        return "UPDATE $TABLE_NAME$ SET $SET_CLAUSE$ $WHERE_CLAUSE$";
    }

    public static class NamedPlaceholderSqlGeneratorAction extends UpdateSqlGeneratorAction {

        String prefix;
        String suffix;

        public NamedPlaceholderSqlGeneratorAction(String prefix, String suffix) {
            super("update sql generator    " + prefix + "param" + suffix);
            this.prefix = prefix;
            this.suffix = suffix;
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                public String getSetClause() {
                    return Util.appendNamedParams(tableInfo.getColumnsName(), ",", prefix, suffix);
                }

                @Override
                public String getWhereClause() {
                    return Util.makeNamedPlaceholderWhereClause(tableInfo.getPrimaryKeys(), prefix,
                        suffix);
                }
            };
        }
    }
}
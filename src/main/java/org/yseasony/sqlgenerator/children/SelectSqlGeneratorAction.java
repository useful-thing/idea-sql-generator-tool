package org.yseasony.sqlgenerator.children;

import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.SqlGenerator;
import org.yseasony.sqlgenerator.TableInfo;
import org.yseasony.sqlgenerator.Util;

/**
 * 类SelectSqlGeneratorAction.java
 * 
 * @author Damon 2014-03-26 下午4:41
 */
public class SelectSqlGeneratorAction extends BaseSqlGenerator {

    public SelectSqlGeneratorAction() {
        super("select sql generator");
    }

    public SelectSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    protected String getSqlTemplate() {
        return "SELECT $COLUMN_LIST$ FROM $TABLE_NAME$ $WHERE_CLAUSE$";
    }

    @Override
    String getStatementType() {
        return "SELECT";
    }

    public static class NamedPlaceholderSqlGeneratorAction extends SelectSqlGeneratorAction {


        String prefix;
        String suffix;

        public NamedPlaceholderSqlGeneratorAction(String prefix, String suffix) {
            super("select sql generator    " + prefix + "param" + suffix);
            this.prefix = prefix;
            this.suffix = suffix;
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                public String getWhereClause() {
                    return Util.makeNamedPlaceholderWhereClause(tableInfo.getPrimaryKeys(), prefix,
                        suffix);
                }
            };
        }
    }
}

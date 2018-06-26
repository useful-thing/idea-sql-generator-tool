package org.yseasony.sqlgenerator;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.children.DeleteSqlGeneratorAction;
import org.yseasony.sqlgenerator.children.InsertSqlGeneratorAction;
import org.yseasony.sqlgenerator.children.SelectSqlGeneratorAction;
import org.yseasony.sqlgenerator.children.UpdateSqlGeneratorAction;

/**
 * 类SqlGeneratorAction.java
 *
 * @author Damon 2014-03-26 下午4:39
 */
public class SqlGeneratorAction extends ActionGroup {

    public SqlGeneratorAction() {
        super("Sql Generator", true);
    }

    @Override
    public void update(AnActionEvent e) {
        PsiElement psiElement = e.getData(LangDataKeys.PSI_ELEMENT);
        if (psiElement == null) {
            return;
        }

        e.getPresentation().setEnabledAndVisible(psiElement instanceof DbTable);
        super.update(e);
    }

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
        return new AnAction[]{
            new SelectSqlGeneratorAction(),
            new SelectSqlGeneratorAction.NamedPlaceholderSqlGeneratorAction(":", ""),
            new SelectSqlGeneratorAction.NamedPlaceholderSqlGeneratorAction("#{", "}"),
            new InsertSqlGeneratorAction(),
            new InsertSqlGeneratorAction.NamedPlaceholderSqlGeneratorAction(":", ""),
            new InsertSqlGeneratorAction.NamedPlaceholderSqlGeneratorAction("#{", "}"),
            new DeleteSqlGeneratorAction(),
            new DeleteSqlGeneratorAction.NamedPlaceholderSqlGeneratorAction(":", ""),
            new DeleteSqlGeneratorAction.NamedPlaceholderSqlGeneratorAction("#{", "}"),
            new UpdateSqlGeneratorAction(),
            new UpdateSqlGeneratorAction.NamedPlaceholderSqlGeneratorAction(":", ""),
            new UpdateSqlGeneratorAction.NamedPlaceholderSqlGeneratorAction("#{", "}"),
        };
    }

}

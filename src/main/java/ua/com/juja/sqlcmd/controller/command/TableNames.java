package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.Set;

public class TableNames implements Command {
    private final View view;
    private final DatabaseManager manager;

    public TableNames(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return "tables".equals(command);
    }

    @Override
    public void process(String command) {
        Set<String> tableNames = manager.getTableNames();
        view.write(tableNames.toString());
    }

    @Override
    public String format() {
        return "tables";
    }

    @Override
    public String description() {
        return "display list of tables";
    }
}

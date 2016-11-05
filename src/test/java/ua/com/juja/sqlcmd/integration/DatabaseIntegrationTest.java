package ua.com.juja.sqlcmd.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.Main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ua.com.juja.sqlcmd.TestUtilites.*;

public class DatabaseIntegrationTest {
    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;
    private static String connectParameters;

    @BeforeClass
    public static void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        connectParameters = getConnectParameters();
    }

    @Before
    public void clearIn() throws IOException {
        in.reset();
    }

    @Test
    public void testCreateAndDropDatabase() {
        in.add(connectParameters);
        in.add("dropDatabase|db1");
        in.add("yes");
        in.add("dropDatabase|db2");
        in.add("yes");
        in.add("createDatabase|db1|zzz");
        in.add("createDatabase|db1");
        in.add("createDatabase|db1");
        in.add("dropDatabase|db1|zzz");
        in.add("dropDatabase|db2");
        in.add("yes");
        in.add("dropDatabase|db1");
        in.add("ye");
        in.add("dropDatabase|db1");
        in.add("yes");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Hello!\n" +
                "Enter the database name, user name and password in format connect|databaseName|userName|password.\n" +
                "(Full list of commands - help).\n" +
                "Connection successful.\n" +
                "Enter a command (help - list of commands):\n" +
                "To confirm drop database 'db1' type 'yes'.\n" +
                "Database 'db1' not exist. Operation cancelled.\n" +
                "Enter a command (help - list of commands):\n" +
                "To confirm drop database 'db2' type 'yes'.\n" +
                "Database 'db2' not exist. Operation cancelled.\n" +
                "Enter a command (help - list of commands):\n" +
                "Failure. Reason: Incorrect command format. The correct format: 'createDatabase|DatabaseName',\n" +
                "your command: createDatabase|db1|zzz\n" +
                "Try again.\n" +
                "Enter a command (help - list of commands):\n" +
                "Database 'db1' created successfully\n" +
                "Enter a command (help - list of commands):\n" +
                "Database 'db1' already exist. Operation cancelled.\n" +
                "Enter a command (help - list of commands):\n" +
                "Failure. Reason: Incorrect command format. The correct format: 'dropDatabase|DatabaseName',\n" +
                "your command: dropDatabase|db1|zzz\n" +
                "Try again.\n" +
                "Enter a command (help - list of commands):\n" +
                "To confirm drop database 'db2' type 'yes'.\n" +
                "Database 'db2' not exist. Operation cancelled.\n" +
                "Enter a command (help - list of commands):\n" +
                "To confirm drop database 'db1' type 'yes'.\n" +
                "Drop database 'db1' cancelled.\n" +
                "Enter a command (help - list of commands):\n" +
                "To confirm drop database 'db1' type 'yes'.\n" +
                "Database 'db1' dropped successfully\n" +
                "Enter a command (help - list of commands):\n" +
                "Good luck!\n", formatOutput(out));
    }

    @Test
    public void testDatabasesList() {
        in.add(connectParameters);
        in.add("databases");
        in.add("exit");
        Main.main(new String[0]);
        assertTrue(formatOutput(out).contains("sqlcmd"));
    }
}

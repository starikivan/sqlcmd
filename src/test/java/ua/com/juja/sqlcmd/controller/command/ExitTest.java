package ua.com.juja.sqlcmd.controller.command;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExitTest {

    FakeView view = new FakeView();

    @Test
    public void TestCanProcessExit(){
        Command command = new Exit(view);
        assertTrue(command.canProcess("exit"));
    }

    @Test
    public void TestCanProcessQwe(){
        Command command = new Exit(view);
        assertFalse(command.canProcess("qwe"));
    }

    @Test
    public void TestProcessExit_ThrowsExitException(){
        Command command = new Exit(view);
        try {
            command.process("exit");
            fail("Expected ExitException");
        }catch (ExitException e){

        }
        assertEquals("Good luck!\n",view.getContent());
    }
}

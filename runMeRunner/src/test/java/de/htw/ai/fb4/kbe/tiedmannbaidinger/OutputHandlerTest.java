package de.htw.ai.fb4.kbe.tiedmannbaidinger;

import de.htw.ai.fb4.kbe.runmerunner.OutputHandler;
import de.htw.ai.fb4.kbe.runmerunner.ReflectionHandler;
import de.htw.ai.fb4.kbe.runmerunner.WrongArgumentsException;
import org.junit.Test;
import static org.junit.Assert.*;

public class OutputHandlerTest {

    private OutputHandler oh;

    @Test
    public void testIfOutputIsCorrect(){
        try{
            ReflectionHandler rh = new ReflectionHandler("Test");
            rh.findMethods();
            oh = new OutputHandler(rh);
            String output = "Methodennamen ohne @RunMe:" +
                    "" +
                    "Methodennamen mit @RunMe:" +
                    "test1" +
                    "test2" +
                    "" +
                    "Nicht invokierbare Methoden mit @RunMe:" +
                    "test4: IllegalArgumentException";
            assertEquals(output, oh.createOutput().replace("\n",""));
        }
        catch(WrongArgumentsException wae){
            fail("Exception is thrown");
        }

    }
}

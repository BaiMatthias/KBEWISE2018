package de.htw.ai.fb4.kbe.tiedmannbaidinger;

import de.htw.ai.fb4.kbe.runmerunner.ReflectionHandler;
import de.htw.ai.fb4.kbe.runmerunner.WrongArgumentsException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReflectionHandlerTest {

    ReflectionHandler rh;

    @Test
    public void testIfMethodsAreCollectedProperlyWithCorrectClassName() throws WrongArgumentsException {
        rh = new ReflectionHandler("Test");
        rh.findMethods();
        assertEquals(0, rh.getMethodsWithoutRM().size());
        assertEquals(2, rh.getMethodsWithRM().size());
        assertEquals(1,rh.getMethodsException().size());
    }

    @Test (expected = WrongArgumentsException.class)
    public void testIfMethodsAreCollectedWithIncorrectClassName() throws WrongArgumentsException{
        rh = new ReflectionHandler("KeineKlasse");
        rh.findMethods();
    }

}


package de.htw.ai.fb4.kbe.tiedmannbaidinger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
  @Test
  public void helpTextTest(){
      String helptext= App.getHelpText("error");
      System.out.println(helptext);
      assertEquals("error\nSyntax: java -jar runMeRunner-x.x-jar-with-dependencies.jar -c <classname> [-o <output>]", helptext);
  }
}

package de.htw.ai.fb4.kbe.runmerunner;

/**
 * Dies ist eine Testklasse, um die Ergebnisse der Reflection testen zu koennen
 * @author tiedmannbaidinger
 * @version 1.0
 */

public class Test {

    @RunMe
    public void test1(){
        System.out.println("Dies ist eine Testmethode");
    }

    @RunMe
    public String test2(){
        return "Dies ist eine weitere Testmethode";
    }

    public int test3(int num){
        return num;
    }

    @RunMe
    public double test4(double num){
        return num;
    }
}

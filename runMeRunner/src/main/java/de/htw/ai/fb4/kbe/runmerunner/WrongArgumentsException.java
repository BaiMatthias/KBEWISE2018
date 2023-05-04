package de.htw.ai.fb4.kbe.runmerunner;

public class WrongArgumentsException extends Exception {
    public WrongArgumentsException(){
        super("WrongArgumentsException");
    }

    public WrongArgumentsException(String error){
        super(error);
    }

}

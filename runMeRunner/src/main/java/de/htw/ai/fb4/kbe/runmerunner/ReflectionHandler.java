package de.htw.ai.fb4.kbe.runmerunner;

/**
 * Diese Klasse ermittelt mit Hilfe von Reflection die Methoden und die dazugehörigen Annotationen einer angegebenen Klasse
 * und speichert die Ergebnisse dazu in Listen, die spaeter ausgewertet werden koennen
 */

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ReflectionHandler {

    private Class classname;
    private ArrayList<String> methodsWithoutRM;
    private ArrayList<String> methodsWithRM;
    private HashMap<String,String> methodsException;

    /**
     * Erstellt ein Objekt ueber das auf die erstellten Listen zugegriffen werden kann, die die ermittelten Methoden enthalten
     * @param classname Der Name der Klasse, die analysiert werden soll
     * @throws WrongArgumentsException Wenn die Klasse nicht gefunden werden kann
     */
    public ReflectionHandler(String classname) throws WrongArgumentsException {
        try {
            this.classname = Class.forName("de.htw.ai.fb4.kbe.runmerunner."+classname);
            methodsWithoutRM = new ArrayList();
            methodsWithRM = new ArrayList();
            methodsException = new HashMap();
        } catch (ClassNotFoundException e) {
            throw new WrongArgumentsException("No Classname found");
        }
    }

    /**
     * Ermittelt die Methoden und Annotationen der Klasse, die im ReflectionHandler-Objekt gespeichert ist.
     * Speichert die Ergebnisse in Listen, die per Getter abgefragt werden koennen
     * @throws WrongArgumentsException Wenn die Klasse, die per Reflection analysiert werden soll, nicht existiert
     * oder andere Probleme bei der Initialisierung des Objekts der Klasse auftretren
     */
    public void findMethods() throws WrongArgumentsException {

        try {
            Method[] methodArr = this.classname.getDeclaredMethods();
            Object obj = this.classname.getConstructor().newInstance();


            for (int i = 0; i < methodArr.length; i++) {

                try {

                    methodArr[i].invoke(obj, new Object[] {});
                    //  methodArr[i].invoke();
                        if (methodArr[i].isAnnotationPresent(RunMe.class)) {
                            methodsWithRM.add(methodArr[i].getName());
                        } else
                            methodsWithoutRM.add(methodArr[i].getName());

                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ExceptionInInitializerError iae) {
                    if (methodArr[i].isAnnotationPresent(RunMe.class)) {
                        methodsException.put(methodArr[i].getName(), iae.getClass().getSimpleName());
                    }

                }


            }
            Collections.sort(methodsWithoutRM);
            Collections.sort(methodsWithRM);
        }catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ie){
            System.out.println("Fehler beim Instanziieren des Objekts");
            throw new WrongArgumentsException();
        }


    }


    public ArrayList<String> getMethodsWithoutRM() {
        return methodsWithoutRM;
    }
    public ArrayList<String> getMethodsWithRM() {
        return methodsWithRM;
    }
    public HashMap<String, String> getMethodsException() {
        return methodsException;
    }


}

package de.htw.ai.kbe.echo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputHandler {
    private ObjectMapper mapper = new ObjectMapper();
    private Songs songs = null;

    /**
     * Construktor of OutputHanlder
     *
     * @param out List of Songs to write
     */
    public OutputHandler(Songs out) {
        this.songs = out;
    }

    /**
     * writes the the output to a file
     *
     * @param filepath path of teh file
     * @return true if written, false if not
     * @throws IOException if file can not written
     */
    public void writeToFile(String filepath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write("[\n");
        for (int i = 0; i < this.songs.getSongs().size(); i++) {
            try {
                Song song = this.songs.getSongs().get(i);
                writer.write(mapper.writeValueAsString(song));
                if (i < this.songs.getSongs().size() - 1) {
                    writer.write(",\n");
                } else {
                    writer.write("\n");
                }
            } catch (JsonProcessingException e) {
                System.out.println("Somthing went wrong:\n" + e.getMessage());
            }
        }
        writer.write("]");
        writer.close();
    }
}

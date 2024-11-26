package fileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// i messaggi che vengono scritti dai client vengono salvati dentro un file presente nel progetto
public class HandleFile {

    private final String path = System.getProperty("user.dir");
    private final String fileName = "file.txt";
    private final String directory = "src/main/resources/filefolder";
    private final String completePath = Paths.get(path, directory, fileName).toString();
    private StringBuilder sb;


    private boolean fileAlreadyExist() {
        Path filePath = Paths.get(completePath);
        return Files.exists(filePath);
    }

    public void writeOnFile(String frase, int idClient) throws IOException {
        sb = new StringBuilder();

        if (!fileAlreadyExist()) {
            Files.createDirectories(Paths.get(path, directory));
            Files.createFile(Paths.get(completePath));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(completePath, true))) {
            sb.append("Server N° ").append(idClient).append(" ").append(frase);
            bw.write(sb.toString());
            bw.newLine();

        } catch (IOException ex) {
            throw new IOException("Errore durante la scrittura su file.", ex);
        }
    }

    
}

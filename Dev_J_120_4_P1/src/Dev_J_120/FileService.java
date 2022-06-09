
package Dev_J_120;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileService {
    
    private static final String URL = "books.dat"; 
    
    public void saveBooksToFile(List list) throws IOException {
        
        Path path = Paths.get(URL); 
        if(!path.isAbsolute())
            path = path.toAbsolutePath();
        Path dir = path.getParent();
        if(!Files.isDirectory(dir))
           Files.createDirectories(dir);
        if(!Files.exists(path))
           Files.createFile(path); 
        List<String> listString = new ArrayList<>();
        list.forEach((s) -> {
            listString.add(s.toString());
        });
        Files.write(path, listString);                 
    }
    
    public List<String> extractBooksFromFile() throws IOException{
        
        Path path = Paths.get(URL); 
        if(!path.isAbsolute())
            path = path.toAbsolutePath();
        //Path dir = path.getParent();
        if(!Files.exists(path))
            throw new IOException
            ("The source data file was not found. The book database is empty.");
        ArrayList<String> sourceList = new ArrayList<>(Files.readAllLines(path)); 
      return sourceList;
    }
}

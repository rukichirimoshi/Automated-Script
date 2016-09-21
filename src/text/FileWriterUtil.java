package text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterUtil {

    private File file;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public FileWriterUtil(String file, boolean overwrite) {
        setFile(new File(file));
        if (this.getFile().exists()) {
            try {
                setFileWriter(new FileWriter(this.getFile(), overwrite));
                setBufferedWriter(new BufferedWriter(this.getFileWriter()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileWriter getFileWriter() {
        return fileWriter;
    }

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public void writeFile(String content) {
        if (this.getFile().exists()) {
            try {
                String[] lines = content.split("\r\n");
                for (int i = 0; i < lines.length; i++) {
                    this.getBufferedWriter().write(lines[i] + "\r\n");
                }
                this.getBufferedWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally { // always close the file
                if (this.getBufferedWriter() != null) {
                    try {
                        this.getBufferedWriter().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

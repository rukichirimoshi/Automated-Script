package reference_files;

import text.FileReaderUtil;

public class ReferenceFile {

    private int id;
    private int check_id;
    private int check_item_id;
    private String db_content;
    private String file_path;
    private String file_content;

    public ReferenceFile(Integer id, Integer check_id, Integer check_item_id, String db_content, String file_path) {
        this.setId(id);
        this.setCheck_id(check_item_id);
        this.setCheck_item_id(check_item_id);
        
        this.setDb_content(db_content);
        this.setFile_path(file_path);
        
        this.getFileContent();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheck_id() {
        return check_id;
    }

    public void setCheck_id(int check_id) {
        this.check_id = check_id;
    }

    public int getCheck_item_id() {
        return check_item_id;
    }

    public void setCheck_item_id(int check_item_id) {
        this.check_item_id = check_item_id;
    }

    public String getDb_content() {
        return db_content;
    }

    public void setDb_content(String db_content) {
        //Could be that exists just some "\n"
        //So first change everything to "\n"
        String content = db_content.replace("\r\n", "\n");
        
        String[] lines = content.split("\n");
        content = "";
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (!line.isEmpty()) { 
                line += "\r\n";
            }
            content += line;
        }
        
        this.db_content = content;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_content() {
        return file_content;
    }

    public void setFile_content(String file_content) {
        this.file_content = file_content;
    }

    private void getFileContent() {
        FileReaderUtil futil = new FileReaderUtil(this.getFile_path());
        this.setFile_content(futil.readFileCRLF());
    }
    
    public boolean needUpdate() {
        if ( this.getDb_content().intern() == this.getFile_content().intern() ) {
            return false;
        } else {
            return true;
        }
    }
}

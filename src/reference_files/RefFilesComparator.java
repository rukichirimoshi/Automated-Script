package reference_files;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import database.DBUtil;
import text.FileWriterUtil;

public class RefFilesComparator {

    private Set<ReferenceFile> fileWithUpdate = new HashSet<ReferenceFile>();
    private DBUtil dbUtil = new DBUtil();
    private Date exec = null;

    public RefFilesComparator(Date exec) {
        this.exec = exec;
    }

    public void checkRefFilesUpdates() {
    	this.getReferencesFromDB();

        for (ReferenceFile f : this.getFileWithUpdate()) {
            //Write new content in the reference file
            FileWriterUtil fwutil = new FileWriterUtil(f.getFile_path(), false);
            fwutil.writeFile(f.getDb_content());

            //Update the 'last_file_check' parameter
            PreparedStatement pstmt = null;
            try {
                pstmt = dbUtil.getConn().prepareStatement("UPDATE reference_files_update SET last_file_check=? WHERE check_item_id=?;");

                pstmt.setTimestamp(1, new Timestamp((exec.getTime())));
                pstmt.setInt(2, f.getCheck_item_id());

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e);
            }

            dbUtil.doINSERT(pstmt);
        }
    }

    public void getReferencesFromDB() {
        ResultSet rs = dbUtil.doSelect("*", "reference_files_update", "");

        try {
            while (rs.next()) {
                int ID = rs.getInt("id");
                int checkID = rs.getInt("check_id");
                int checkItemID = rs.getInt("check_item_id");

                String content = rs.getString("update_to");
                String filePath = rs.getString("file_path");

                ReferenceFile ref = new ReferenceFile(ID, checkID, checkItemID, content, filePath);

                if (ref.needUpdate()) {
                    fileWithUpdate.add(ref);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Set<ReferenceFile> getFileWithUpdate() {
        return fileWithUpdate;
    }

}

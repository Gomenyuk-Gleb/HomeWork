package Implementation;

import Entity.DBTable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/save")
public class SaveFile extends HttpServlet {
    private final static EntityManager entityManager = SETFile.getEntityManager();
    List<String> strings = GetFile.getStrings();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("name");
        List<DBTable> files = new ArrayList<>();
        System.out.println(fileName);
        System.out.println(strings);
        if(fileName.equals("ALL")){
            System.out.println("all");
            files = selectAllFiles();
            saveAllFile(files);
        }
        else if(strings.contains(fileName)){//check
            System.out.println("Sad");
            System.out.println(fileName);
            DBTable dbTable = saveOneFile(fileName);
            saveFile(dbTable);
        }
    }

    public DBTable saveOneFile(String fileName){
        TypedQuery<DBTable> query = entityManager.createQuery("SELECT f FROM DBTable f WHERE f.fileName = :name", DBTable.class);
        query.setParameter("name", fileName);
        return query.getSingleResult();
    }


    public List<DBTable> selectAllFiles() {
        TypedQuery<DBTable> query = entityManager.createQuery("SELECT c FROM DBTable c", DBTable.class);
        return query.getResultList();
    }

    public void writeBytes(File file, byte[] fileZIP) {
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(fileZIP);
            System.out.println("E***** URAAAA");
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAllFile(List<DBTable> files){
        for (DBTable fileEntity : files) {
            System.out.println("Download");
            File file = new File("D:\\A", fileEntity.getName() + ".zip");
            writeBytes(file, fileEntity.getFileLink());
        }
    }
    public void saveFile(DBTable dbTable){
        System.out.println("Download");
        File file = new File("D:\\A", dbTable.getName() + ".zip");
        writeBytes(file, dbTable.getFileLink());
    }

}

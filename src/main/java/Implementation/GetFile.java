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

@WebServlet("/download")
public class GetFile extends HttpServlet {
    private final static EntityManager entityManager = SETFile.getEntityManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        List<DBTable> files = new ArrayList<>();
        if (fileName.equals("ALL")) {
            System.out.println("all");
            files = selectAllFiles();
                    }
        System.out.println(files.toString());

        for (DBTable fileEntity : files) {
            System.out.println("Download");
            File file = new File("D:\\pere", fileEntity.getNameFile() + ".zip");
            writeBytes(file, fileEntity.getFileLink());
        }

        req.setAttribute("filePost", files);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/lib/results.jsp");
        dispatcher.forward(req, resp);
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
}

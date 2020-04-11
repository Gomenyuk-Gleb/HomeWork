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

@WebServlet("/watch")
public class GetFile extends HttpServlet {
    private final static EntityManager entityManager = SETFile.getEntityManager();
    static List<String> strings = new ArrayList<>();
    static List<DBTable> files = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        if (fileName.equals("ALL")) {
            files = selectAllFiles();
            strings = allNameFile(files);
        }

        if (fileName.equals("ALL")) {
            List<DBTable> dbTables = files;
            req.setAttribute("ordersList", dbTables);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/results.jsp");
            dispatcher.forward(req, resp);
        }
    }

    public List<String> allNameFile(List<DBTable> files) {
        for (int i = 0; files.size() > i; i++) {
            strings.add(files.get(i).getName());
        }
        return strings;
    }

    public List<DBTable> selectAllFiles() {
        TypedQuery<DBTable> query = entityManager.createQuery("SELECT c FROM DBTable c", DBTable.class);
        return query.getResultList();
    }

public static List<DBTable> getFiles(){
        return files;
}
    public static List<String> getStrings() {
        return strings;
    }

}

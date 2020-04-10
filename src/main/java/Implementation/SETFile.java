package Implementation;

import Entity.DBTable;

import javax.persistence.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@WebServlet("/input")
@MultipartConfig
public class SETFile extends HttpServlet {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPATest");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Part> fileParts = req.getParts().stream().filter(part -> "file".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">

        for (Part filePart : fileParts) {
            String fileName = getSubmittedFileName(filePart);
            byte[] saveF = saveIO(filePart.getInputStream());
            DBTable file = new DBTable(fileName.toString(), filePart.getSize(), zipFile(fileName.toString(), saveF));
            transactionCommit(file);
            System.out.println("Ваш файл " + fileName + " Успешно добавлен");
        }
        resp.setStatus(200);
        resp.sendRedirect("input.jsp");
    }

    public static byte[] zipFile(String name, byte [] io) {
        try {
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.putNextEntry(new ZipEntry(name));
            zos.write(io);
            zos.closeEntry();
            zos.close();
            return fos.toByteArray();
        } catch (FileNotFoundException ex) {
            System.err.format("The file %s does not exist");
            return null;
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex);
            return null;
        }
    }
    public static void transactionCommit(DBTable c) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(c);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction.isActive())
                transaction.rollback();
            ex.printStackTrace();
        }
    }
    private byte[] saveIO(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int read = is.read(buf);
        for (; read != -1; ) {
            buffer.write(buf, 0, read);
            read = is.read(buf);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;

    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

}

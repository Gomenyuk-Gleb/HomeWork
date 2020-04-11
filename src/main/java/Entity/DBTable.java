package Entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "DBTable")
public class DBTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "size")
    private Long size;

    @Lob
    @Column(name = "fileLink", columnDefinition = "LONGBLOB")
    private byte[] fileLink;

    public DBTable(String fileName, Long size, byte[] fileLink) {
        this.fileName = fileName;
        this.size = size;
        this.fileLink = fileLink;
    }


    public void setId(java.lang.Long id) { this.id = id; }
    public void setNameFile(String nameFile) { this.fileName = nameFile; }
    public void setLong(Long aLong) { size = aLong;}
    public byte[] getFileLink() { return fileLink; }
    public void setFileLink(byte[] fileLink) { this.fileLink = fileLink; }
    public java.lang.Long getId() { return id; }
    public String getName() { return fileName; }
    public Long getLnk() { return size; }

    @Override
    public String toString() {
        return "DBTable{" +
                "id=" + id +
                ", nameFile='" + fileName + '\'' +
                ", Long='" + size + '\'' +
                ", fileLink=" + Arrays.toString(fileLink) +
                '}';
    }
}

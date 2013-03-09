package search;

import java.io.File;
import java.io.Serializable;

public class Document implements Comparable<Document>, Serializable {
    private File file;

    public Document(File file) {
        this.file = file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public int compareTo(Document o) {
        return file.getName().compareTo(o.file.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (file != null ? !file.equals(document.file) : document.file != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }
}

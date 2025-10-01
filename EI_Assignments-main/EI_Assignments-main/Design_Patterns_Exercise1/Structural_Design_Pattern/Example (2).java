import java.util.*;

interface FileComponent {
    void showDetails();
}

class FileLeaf implements FileComponent {
    private String name;
    public FileLeaf(String name) { this.name = name; }

    @Override
    public void showDetails() { System.out.println("File: " + name); }
}

class FolderComposite implements FileComponent {
    private String name;
    private List<FileComponent> components = new ArrayList<>();

    public FolderComposite(String name) { this.name = name; }

    public void addComponent(FileComponent component) { components.add(component); }

    @Override
    public void showDetails() {
        System.out.println("Folder: " + name);
        for (FileComponent c : components) c.showDetails();
    }
}

// Demo
public class CompositeDemo {
    public static void main(String[] args) {
        FolderComposite root = new FolderComposite("Root");
        FileLeaf file1 = new FileLeaf("file1.txt");
        FileLeaf file2 = new FileLeaf("file2.txt");

        FolderComposite subFolder = new FolderComposite("SubFolder");
        subFolder.addComponent(new FileLeaf("file3.txt"));

        root.addComponent(file1);
        root.addComponent(file2);
        root.addComponent(subFolder);

        root.showDetails();
    }
}

package gui.images.repository;
import javax.swing.ImageIcon;

public class RepositoryImages
{

    private final static String repositoryPath = "";//Images.opmPath;
    // ********** Repository Images ***************

    public final static ImageIcon OBJECT = loadImage(repositoryPath+"objectRM.gif");
    public final static ImageIcon PROCESS = loadImage(repositoryPath+"processRM.gif");
    public final static ImageIcon OPD = loadImage(repositoryPath+"opdRM.gif");
//    public final static ImageIcon PROJECT = loadImage(repositoryPath+"ProjectRM.gif");
//    public final static ImageIcon OPCAT_PROJECTS = loadImage(repositoryPath+"opcatProjectsRM.gif");
    public final static ImageIcon TREE = loadImage(repositoryPath+"tree.gif");
    public final static ImageIcon BOOK = loadImage(repositoryPath+"book.gif");

    protected static ImageIcon loadImage(String file)
    {
        try
        {
            return new ImageIcon(RepositoryImages.class.getResource(file));
        }
        catch (Exception e)
        {
            System.err.println("cannot load image "+file);

            return null;
        }
    }

}

package gui.opx;

import gui.opdProject.OpdProject;

import javax.swing.*;
import java.io.OutputStream;

public interface SaverI {
    public void save(OpdProject project, OutputStream os,
                     JProgressBar progressBar) throws Exception;

    public String getVersion();
}
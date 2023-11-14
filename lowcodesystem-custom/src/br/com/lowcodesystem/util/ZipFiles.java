/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 *
 * @author Maykon
 */
public class ZipFiles {

    public void unzip(File zip, File extractTo) throws IOException {
        ZipFile archive = new ZipFile(zip, "UTF-8");
        Enumeration e = archive.getEntries();
        while (e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            File file = new File(extractTo, entry.getName());
            if (entry.isDirectory() && !file.exists()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                System.out.println("extract file:" + file.getAbsolutePath());
                InputStream in = archive.getInputStream(entry);
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));

                byte[] buffer = new byte[8192];
                int read;

                while (-1 != (read = in.read(buffer))) {
                    out.write(buffer, 0, read);
                }
                in.close();
                out.close();
            }
        }
    }
}

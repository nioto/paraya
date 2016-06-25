package org.nioto.payara.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WarFile {

  List<Class<?>> classes = new ArrayList<>();

  public WarFile() {
    super();
  }

  public WarFile addClass(Class<?> clazz) {
    this.classes.add(clazz);
    return this;
  }

  public File generateFile() throws IOException {
    File out = File.createTempFile("app", ".war");

    FileOutputStream fos = new FileOutputStream( out );
    ZipOutputStream zip = new ZipOutputStream(fos);
    for (Class<?> class1 : classes) {
      add(zip, class1);
      zip.flush();
    }
    addWebXML(zip);
    zip.flush();
    zip.close();
    return out;
  }

  private void add(ZipOutputStream zos, Class<?> clazz) throws FileNotFoundException, IOException {
    String path = clazz.getPackage().getName().replace('.', '/') + '/' + clazz.getSimpleName() + ".class";
    addFile(zos, "target/classes/" + path, "WEB-INF/classes/" + path);
  }

  private void addWebXML(ZipOutputStream zos) throws FileNotFoundException, IOException {
    addFile(zos, "src/main/resources/web.xml", "WEB-INF/web.xml");
  }

  private void addFile(ZipOutputStream zos, String path, String warPath) throws FileNotFoundException, IOException {
    File file = new File(path);
    FileInputStream fis = new FileInputStream(file);
    ZipEntry zipEntry = new ZipEntry(warPath);
    zos.putNextEntry(zipEntry);
    byte[] bytes = new byte[1024];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zos.write(bytes, 0, length);
    }
    zos.closeEntry();
    fis.close();
    zos.flush();
  }
}
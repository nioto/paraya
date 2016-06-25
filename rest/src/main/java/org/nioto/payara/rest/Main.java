/**
 * 
 */
package org.nioto.payara.rest;

import java.io.File;
import java.io.IOException;

import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

/**
 * @author tonio
 *
 */
public class Main {

  /**
   * @param args
   */
  public static void main(String[] args) throws GlassFishException, IOException  {
    GlassFishRuntime runtime = GlassFishRuntime.bootstrap();
    GlassFishProperties gfproperties = new GlassFishProperties();
    gfproperties.setPort("http-listener", 8080);
    GlassFish gf = runtime.newGlassFish(gfproperties);
    gf.start();
    gf.getDeployer().deploy( getWarFile(), "--contextroot=myapp");
    //gf.getDeployer().deploy( );
    //gf.getDeployer().deploy( new FileInputStream( new File("c:/temp/app.war") ) ,"--contextroot=myapp", "--name=rest");

  }
  
  private static File getWarFile() throws IOException {
    WarFile file = new WarFile();
    file.addClass( ApplicationConfig.class)
        .addClass( MyResource.class );
    return file.generateFile();
  }
  

}

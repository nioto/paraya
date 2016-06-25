package org.nioto.payara.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("demo")
public class MyResource {
  @GET
  @Produces("application/xml")
  public String getXml() {
    return "<test>hello world</test>";
  }
}

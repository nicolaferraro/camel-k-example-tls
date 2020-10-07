// camel-k: language=java property-file=tls.properties secret=trust

import org.apache.camel.builder.RouteBuilder;

public class Tls extends RouteBuilder {
  @Override
  public void configure() throws Exception {

      // Write your routes here, for example:
      from("timer:java?period=10000")
        .to("https://self-signed.badssl.com/")
        .convertBodyTo(String.class)
        .to("log:info");

  }
}

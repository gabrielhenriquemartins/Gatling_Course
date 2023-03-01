package acetoys;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";
  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7");

  private ScenarioBuilder scn = scenario("AceToysSimulation")
    .exec(
      http("Load Home Page")
        .get("/")
              .check(status().is(200))  // Automatically Check
              .check(status().not(404), status().not(405))
              .check(substring("<title>Ace Toys Online Shop</title>"))
              .check(css("#_csrf", "content").saveAs("csrfToken"))
    )
    .pause(2)
    .exec(
      http("Load Our Story Page")
        .get("/our-story")
              .check(regex("was founded online in \\d{4}"))
    )
    .pause(2)
    .exec(
      http("Load Get in Touch Page")
        .get("/get-in-touch")
    )
    .pause(2)
    .exec(
      http("Load Products List Page - Category: All Products")
        .get("/category/all")
    )
    .pause(2)
    .exec(
      http("Load Next Page of Products - Page 1")
        .get("/category/all?page=1")
    )
    .pause(2)
    .exec(
      http("Load Next Page of Products - Page 2")
        .get("/category/all?page=2")
    )
    .pause(2)
    .exec(
      http("Load Products Details Page - Product Darts Board")
        .get("/product/darts-board")
    )
    .pause(2)
    .exec(
      http("Add Product to Cart: Product ID: 19")
        .get("/cart/add/19")
    )
    .pause(2)
    .exec(
      http("Load Products List Page: Babies Toys")
        .get("/category/babies-toys")
    )
    .pause(2)
    .exec(
      http("Add Product to Cart: Product ID: 11")
        .get("/cart/add/11")
    )
    .pause(2)
    .exec(
      http("Add Product to Cart: Product ID: 04")
        .get("/cart/add/4")
    )
    .pause(2)
    .exec(
      http("View Cart")
        .get("/cart/view")
    )
    .pause(2)
    .exec(
      http("Login User")
        .post("/login")
        .formParam("_csrf", "#{csrfToken}")
        .formParam("username", "user1")
        .formParam("password", "pass")
              .check(css("#_csrf", "content").saveAs("csrfTokenLoggedIn"))
    )

          .exec(
                  session -> {
                    System.out.println(session);
                    System.out.println("csrfTokenLoggedIn is: " + session.getString("csrfTokenLoggedIn"));
                    return session;
                  }
          )
    .pause(2)
    .exec(
      http("Increase Product Quantity in Cart - Product Id: 19")
        .get("/cart/add/19?cartPage=true")
    )
    .pause(2)
    .exec(
      http("Subtract Product Quantity in Cart - Product Id: 19")
        .get("/cart/subtract/19")
    )
    .pause(2)
    .exec(
      http("Remove Product - Product Id: 11")
        .get("/cart/remove/11")
    )
    .pause(2)
    .exec(
      http("Checkout")
        .get("/cart/checkout")
              .check(substring("Your products are on their way to you now!!"))
    )
    .pause(2)
    .exec(
      http("Logout")
        .post("/logout")
        .formParam("_csrf", "#{csrfTokenLoggedIn}")
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}

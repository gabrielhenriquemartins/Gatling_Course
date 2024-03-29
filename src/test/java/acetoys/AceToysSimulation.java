package acetoys;

import acetoys.pageObjects.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";
  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7");

  private ScenarioBuilder scn = scenario("AceToysSimulation")
    .exec(StaticPages.homepage)
    .pause(2)
    .exec(StaticPages.ourStory)
    .pause(2)
    .exec(StaticPages.getInTouch)
    .pause(2)
    .exec(Category.productListByCategory)
    .pause(2)
    .exec(Category.loadSecondPageOfProducts)
    .pause(2)
    .exec(Category.loadThirdPageOfProducts)
    .pause(2)
    .exec(Product.loadProductDetailsPage)
    .pause(2)
    .exec(Product.addProductToCart)
    .pause(2)
    .exec(Category.productListByCategory)
    .pause(2)
    .exec(Product.addProductToCart)
    .pause(2)
    .exec(Product.addProductToCart)
    .pause(2)
    .exec(Cart.viewCart)
    .pause(2)
    .exec(Customer.login)
    .pause(2)
    .exec(Cart.increaseQuantityInCart)
    .pause(2)
    .exec(Cart.decreaseQuantityInCart)
    .pause(2)
    .exec(Cart.checkout)
    .pause(2)
    .exec(Customer.logout);

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}

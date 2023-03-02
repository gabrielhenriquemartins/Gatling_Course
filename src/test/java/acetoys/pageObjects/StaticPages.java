package acetoys.pageObjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StaticPages {

    public static ChainBuilder homepage =
            exec(
                    http("Load Home Page")
                            .get("/")
                            .check(status().is(200))  // Automatically Check
                            .check(status().not(404), status().not(405))
                            .check(substring("<title>Ace Toys Online Shop</title>"))
                            .check(css("#_csrf", "content").saveAs("csrfToken"))
            );

    public static ChainBuilder ourStory =
            exec(
                    http("Load Our Story Page")
                            .get("/our-story")
                            .check(regex("was founded online in \\d{4}"))
            );

    public static ChainBuilder getInTouch =
            exec(
                    http("Load Get in Touch Page")
                            .get("/get-in-touch")
                            .check(substring("as we are not actually a real store!"))
            );
}

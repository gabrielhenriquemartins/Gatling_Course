package acetoys.pageObjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Product {

    public static ChainBuilder loadProductDetailsPage_DartBoards =
            exec(
                    http("Load Products Details Page - Product Darts Board")
                            .get("/product/darts-board")
                            .check(css("#ProductDescription").is("Get all your mates round for a few drinks and throw sharp objects at this darts board"))
            );

    public static ChainBuilder addProductToCart_Product19 =
            exec(
                    http("Add Product to Cart: Product ID: 19")
                            .get("/cart/add/19")
                            .check(substring("You have <span>1</span> products in your Basket"))
            );

    public static ChainBuilder addProductToCart_Product11 =
            exec(
                    http("Add Product to Cart: Product ID: 11")
                            .get("/cart/add/11")
                            .check(substring("You have <span>2</span> products in your Basket"))
            );
    public static ChainBuilder addProductToCart_Product4 =
            exec(
                    http("Add Product to Cart: Product ID: 4")
                            .get("/cart/add/4")
                            .check(substring("You have <span>3</span> products in your Basket"))
            );
}

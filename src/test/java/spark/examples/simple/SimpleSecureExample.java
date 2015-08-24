/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spark.examples.simple;

import spark.Spark;

/**
 * A simple example just showing some basic functionality You'll need to provide
 * a JKS keystore as arg 0 and its password as arg 1.
 *
 * @author Peter Nicholls, based on (practically identical to in fact)
 *         {@link spark.examples.simple.SimpleExample} by Per Wendel
 */
public class SimpleSecureExample {

    public static void main(String[] args) {

        Spark spark = new Spark();
        // port(5678); <- Uncomment this if you want spark to listen on a
        // port different than 4567.

        spark.secure(args[0], args[1], null, null);

        spark.get("/hello", (request, response) -> "Hello Secure World!");

        spark.post("/hello", (request, response) -> "Hello Secure World: " + request.body());

        spark.get("/private", (request, response) -> {
            response.status(401);
            return "Go Away!!!";
        });

        spark.get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));

        spark.get("/news/:section", (request, response) -> {
            response.type("text/xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>"
                    + request.params("section") + "</news>";
        });

        spark.get("/protected", (request, response) -> {
            spark.halt(403, "I don't think so!!!");
            return null;
        });

        spark.get("/redirect", (request, response) -> {
            response.redirect("/news/world");
            return null;
        });

        spark.get("/", (request, response) -> "root");
    }
}

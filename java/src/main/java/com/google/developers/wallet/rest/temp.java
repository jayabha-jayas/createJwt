package com.google.developers.wallet.rest;// Online Java Compiler
// Use this editor to write, compile and run your Java code online

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.api.services.walletobjects.model.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;


class HelloWorld {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
//        DemoGeneric demoGeneric = DemoGeneric( );
        final String jwt = createJWTNewObjects("3388000000022327001", "plastic", "plastic");
        System.out.println(jwt);


    }

    /**
     * Generate a signed JWT that creates a new pass class and object.
     *
     * <p>When the user opens the "Add to Google Wallet" URL and saves the pass to their wallet, the
     * pass class and object defined in the JWT are created. This allows you to create multiple pass
     * classes and objects in one API call when the user saves the pass to their wallet.
     *
     * @param issuerId     The issuer ID being used for this request.
     * @param classSuffix  Developer-defined unique ID for this pass class.
     * @param objectSuffix Developer-defined unique ID for the pass object.
     * @return An "Add to Google Wallet" link.
     */
    public static String createJWTNewObjects(String issuerId, String classSuffix, String objectSuffix) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        // See link below for more information on required properties
        // https://developers.google.com/wallet/generic/rest/v1/genericclass
        GenericClass newClass = new GenericClass().setId(String.format("%s.%s", issuerId, classSuffix));

        // See link below for more information on required properties
        // https://developers.google.com/wallet/generic/rest/v1/genericobject
        GenericObject newObject =
                new GenericObject()
                        .setId(String.format("%s.%s", issuerId, objectSuffix))
                        .setClassId(String.format("%s.%s", issuerId, classSuffix))
                        .setState("ACTIVE")
                        .setHeroImage(
                                new Image()
                                        .setSourceUri(
                                                new ImageUri()
                                                        .setUri(
                                                                "https://farm4.staticflickr.com/3723/11177041115_6e6a3b6f49_o.jpg"))
                                        .setContentDescription(
                                                new LocalizedString()
                                                        .setDefaultValue(
                                                                new TranslatedString()
                                                                        .setLanguage("en-US")
                                                                        .setValue("Hero image description"))))
                        .setTextModulesData(
                                List.of(
                                        new TextModuleData()
                                                .setHeader("Text module header")
                                                .setBody("Text module body")
                                                .setId("TEXT_MODULE_ID")))
                        .setLinksModuleData(
                                new LinksModuleData()
                                        .setUris(
                                                Arrays.asList(
                                                        new Uri()
                                                                .setUri("http://maps.google.com/")
                                                                .setDescription("Link module URI description")
                                                                .setId("LINK_MODULE_URI_ID"),
                                                        new Uri()
                                                                .setUri("tel:6505555555")
                                                                .setDescription("Link module tel description")
                                                                .setId("LINK_MODULE_TEL_ID"))))
                        .setImageModulesData(
                                List.of(
                                        new ImageModuleData()
                                                .setMainImage(
                                                        new Image()
                                                                .setSourceUri(
                                                                        new ImageUri()
                                                                                .setUri(
                                                                                        "http://farm4.staticflickr.com/3738/12440799783_3dc3c20606_b.jpg"))
                                                                .setContentDescription(
                                                                        new LocalizedString()
                                                                                .setDefaultValue(
                                                                                        new TranslatedString()
                                                                                                .setLanguage("en-US")
                                                                                                .setValue("Image module description"))))
                                                .setId("IMAGE_MODULE_ID")))
                        .setBarcode(new Barcode().setType("QR_CODE").setValue("QR code value"))
                        .setCardTitle(
                                new LocalizedString()
                                        .setDefaultValue(
                                                new TranslatedString().setLanguage("en-US").setValue("Generic card title")))
                        .setHeader(
                                new LocalizedString()
                                        .setDefaultValue(
                                                new TranslatedString().setLanguage("en-US").setValue("Generic header")))
                        .setHexBackgroundColor("#4285f4")
                        .setLogo(
                                new Image()
                                        .setSourceUri(
                                                new ImageUri()
                                                        .setUri(
                                                                "https://storage.googleapis.com/wallet-lab-tools-codelab-artifacts-public/pass_google_logo.jpg"))
                                        .setContentDescription(
                                                new LocalizedString()
                                                        .setDefaultValue(
                                                                new TranslatedString()
                                                                        .setLanguage("en-US")
                                                                        .setValue("Generic card logo"))));


        // Creating the HashMap representing the JSON structure
        Map<String, Object> jwt = new HashMap<>();
        jwt.put("aud", "google");

        List<String> origins = new ArrayList<>();
        origins.add("https://localhost:8080");
        jwt.put("origins", origins);

        jwt.put("iss", "abcd-654@abcd-416109.iam.gserviceaccount.com");
        jwt.put("iat", 1709460402);
        jwt.put("typ", "savetowallet");

        Map<String, Object> payload = getPayload();

        // Print the resulting HashMap
        System.out.println(payload);

        jwt.put("payload", payload);

        // Print the HashMap
        System.out.println(jwt);

        final String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC24ha25s/Jc33T\n" +
                "zJUkQzkXFiS0C5vh1kDwdWSjkNresqXngaH6uqckfKa1MF0CxelJwp7KVp7hASoJ\n" +
                "N9T0ZGLsRq+AjA4Otk3B7QEzj10Q1gramo0Egf6y8FCS819Kz8mzuUQk/um3GbNn\n" +
                "PizFOIKYBnZ8RJo7DZ6Dy9p4g88vFj54JmqJzitQf7m5VfvTl6N7fA7V3Ffq7Ki+\n" +
                "Uol1m10/QwjEYFtD/QFd20FBMW5/L/JM7yF63BAdt/djaEWQUNZAV/1HXZQv9oMP\n" +
                "LMSB8p4UEAicSBY9bjtecY/2AKc8Ubtghb0tnrtLn+VmzYHtj5h9EcA0drivFLLO\n" +
                "58QBhVRPAgMBAAECggEABg/1gTNNHPP5dtI9G2goZOu47PL5kQFlqQYeZbkfE/w/\n" +
                "H8iePRrDzV9/6Ix/I3p2CTOTHjf3ILZwTOMgUB2lpl2z4rW5Sl7kj8XdS7sejA4g\n" +
                "nh1L9858bDTjBL875CsKwtdo5b6n5aGYiZbeX1QU9gewSBGdTiahwfTTQCkyId/d\n" +
                "8mF6sA5J1IOc4+AW7l+rBOwttFA5Fdkyn19DdEZ1TNcXqHAXr4QOztRpxMCCB58s\n" +
                "B5ctPUPU9GaMGBaHjnYzJDnP//Xj7oytgtouo/DSMtY2sl4dLez8Yb/xa1IIZyOh\n" +
                "8Dv4VtFGRRVRr1tKuR+1hPNBPC0OGXaxzek70quMGQKBgQDaUAMWJfN/J3j3cFFX\n" +
                "Znc/w2oe58Zdv/GfltTBZotAUEfMFkgQTtRvyTqtzW4JdDGH2U2MVUCJnbvGu+Z7\n" +
                "MeJWXWmPI3OOg9UKqqFLcEnUYAz8NwSNzcjxZmVdhXs9n/ANZ0LtY+33Tf3g2snN\n" +
                "IeC7ZKzqjS1hHCw3l8gYcTQQrQKBgQDWdFRJkH6DQx5diekDKTBYfnqREzKiuzJ5\n" +
                "ayTdiUWNPNWMcPlZf3fX94fMd25JPcoxD5nxcuCnqehp2VOHnk4iBW6YmsVjGQsx\n" +
                "EcmmNc4OOSr42C21KbNsuyJQpVSGyHj2kelqLF/nBnDN6HsnTyPJImO+jbJz8XZd\n" +
                "z1/SQetMawKBgETfBwo+sMGFb1kIOvEKp0YbXOghEGhU/GHvUHEu63E/olbVFKjo\n" +
                "ZYZagkOAUl1zJYkI2bkS0AhaFXoNnxlwVEoQsBNzCKqGFniF+dp1syTnpFEQ/kPK\n" +
                "DsFxHztQt99qDG3+DJQdeV4SYaxE+XLYZDBaA17/aLiBDcYAASUM2XMFAoGAf9xH\n" +
                "zLyL0XMZDagF9059dqWiKhEKJZ/6zg2lozgO8dCNefh5knS/XVJF8xBZ/u0KOgoV\n" +
                "PLHDY/OXkFR0yu0FpJ8ce3v7p8PsukrisWhu/Gf1SPyK8/Jy+abxJKgOJ3YrOMdy\n" +
                "JU1juHhMlVfA5YT3zxnj4l7VQO98JR5ZASEwSxUCgYBvr0PBcsImhTgSJtOcoXr3\n" +
                "jEnQ9kx9DhWJp9tNGGBjXozcsiHtPu211fgqyQGnnCf0WssvrXg+YntjKDzUkZkK\n" +
                "hwjwwo0TuJZ3qidzekgdeTzQT4/nI1gds0v6WSUiSHodJkBoOeuwZnGtwz4/IEPo\n" +
                "fqtelugALXdqY4Iw79s2Qg==\n" +
                "-----END PRIVATE KEY-----";

// Read in the key into a String
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(PRIVATE_KEY));
        String line;
        while ((line = rdr.readLine()) != null) {
            pkcs8Lines.append(line);
        }

        // Remove the "BEGIN" and "END" lines, as well as any whitespace

        String pkcs8Pem = pkcs8Lines.toString();
        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");

        // Base64 decode the result

//        byte [] pkcs8EncodedBytes = Base64.decode(pkcs8Pem, Base64.DEFAULT);
        byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(pkcs8Pem);
        // extract the private key

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        System.out.println(privKey);
//RSAPrivateKey rsaPrivateKey1 = RSAPrivateKey()

        // The service account credentials are used to sign the JWT
        Algorithm algorithm =
                Algorithm.RSA256(
                        null, (RSAPrivateKey) privKey);
        String token = JWT.create().withPayload(jwt).sign(algorithm);


        System.out.println("Add to Google Wallet link");
        System.out.printf("https://pay.google.com/gp/v/save/%s%n", token);

        return String.format("https://pay.google.com/gp/v/save/%s", token);
    }

    @NotNull
    private static Map<String, Object> getPayload() {
        Map<String, Object> payload = new HashMap<>();
        List<Map<String, Object>> genericObjects = new ArrayList<>();

        // Create a generic object
        Map<String, Object> genericObject = new HashMap<>();
        genericObject.put("id", "3388000000022327001.3d7eb940-e5a1-482c-8de1-259d73f5cd7c");
        genericObject.put("classId", "3388000000022327001.plastic");

        Map<String, Object> logo = new HashMap<>();
        Map<String, Object> sourceUriLogo = new HashMap<>();
        sourceUriLogo.put("uri", "https://raw.githubusercontent.com/droidbg/assets/main/sea_horse.png");
        logo.put("sourceUri", sourceUriLogo);
        genericObject.put("logo", logo);

        Map<String, Object> cardTitle = new HashMap<>();
        Map<String, Object> defaultValueCardTitle = new HashMap<>();
        defaultValueCardTitle.put("language", "en-US");
        defaultValueCardTitle.put("value", "OCEAN CLEANUP");
        cardTitle.put("defaultValue", defaultValueCardTitle);
        genericObject.put("cardTitle", cardTitle);

        Map<String, Object> subheader = new HashMap<>();
        Map<String, Object> defaultValueSubheader = new HashMap<>();
        defaultValueSubheader.put("language", "en-US");
        defaultValueSubheader.put("value", "Sea horse");
        subheader.put("defaultValue", defaultValueSubheader);
        genericObject.put("subheader", subheader);

        Map<String, Object> header = new HashMap<>();
        Map<String, Object> defaultValueHeader = new HashMap<>();
        defaultValueHeader.put("language", "en-US");
        defaultValueHeader.put("value", "Plastic Slayer");
        header.put("defaultValue", defaultValueHeader);
        genericObject.put("header", header);

        Map<String, Object> barcode = new HashMap<>();
        barcode.put("alternateText", "12345");
        barcode.put("type", "qrCode");
        barcode.put("value", "28343E3");
        genericObject.put("barcode", barcode);

        genericObject.put("hexBackgroundColor", "#4285f4");

        Map<String, Object> heroImage = new HashMap<>();
        Map<String, Object> sourceUriHeroImage = new HashMap<>();
        sourceUriHeroImage.put("uri", "https://raw.githubusercontent.com/droidbg/assets/main/sea_horse.png");
        heroImage.put("sourceUri", sourceUriHeroImage);
        genericObject.put("heroImage", heroImage);

        // Add genericObject to the list
        genericObjects.add(genericObject);

        // Add the list of generic objects to the payload
        payload.put("genericObjects", genericObjects);
        return payload;
    }


}
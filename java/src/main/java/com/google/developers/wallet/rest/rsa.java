package com.google.developers.wallet.rest;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class rsa {

    private final static String PRIVATE_KEY =
            "-----BEGIN PRIVATE KEY-----\n" +
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

    public static void main(String[] args) throws Exception {
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
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+","");

        // Base64 decode the result

//        byte [] pkcs8EncodedBytes = Base64.decode(pkcs8Pem, Base64.DEFAULT);
        byte [] pkcs8EncodedBytes = Base64.getDecoder().decode(pkcs8Pem);
        // extract the private key

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        System.out.println(privKey);
    }

}
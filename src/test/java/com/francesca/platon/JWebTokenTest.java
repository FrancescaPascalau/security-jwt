package com.francesca.platon;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.junit.Assert.fail;

public class JWebTokenTest {

    private JSONObject payload;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(90);
        payload = new JSONObject(Map.of(1, "{\"sub\":\"1234\",\"aud\":[\"admin\"],"
                + "\"exp\":" + ldt.toEpochSecond(ZoneOffset.UTC) + "}"));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of HMACSHA256 method, of class JWebToken.
     */
    @Test
    public void testWithData() {
        //generate JWT
        long exp = LocalDateTime.now().plusDays(90).toEpochSecond(ZoneOffset.UTC);
        String token = new JWebToken("1234", new JSONArray("['admin']"), exp).toString();
        //verify and use
        JWebToken incomingToken;
        System.out.println(token);
        try {
            incomingToken = new JWebToken(token);
            if (incomingToken.isValid()) {
                Assert.assertEquals("1234", incomingToken.getSubject());
                Assert.assertEquals("admin", incomingToken.getAudience().get(0));
            }
        } catch (NoSuchAlgorithmException ex) {
            fail("Invalid Token" + ex.getMessage());
        }
    }

    @Test
    public void testWithJson() {
        String token = new JWebToken(payload).toString();
        //verify and use
        JWebToken incomingToken;
        try {
            incomingToken = new JWebToken(token);
            if (incomingToken.isValid()) {
                Assert.assertEquals("1234", incomingToken.getSubject());
                Assert.assertEquals("admin", incomingToken.getAudience().get(0));
            }
        } catch (NoSuchAlgorithmException ex) {
            fail("Invalid Token" + ex.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadHeaderFormat() {
        String token = new JWebToken(payload).toString();
        token = token.replaceAll("\\.", "X");
        //verify and use
        JWebToken incomingToken;
        try {
            incomingToken = new JWebToken(token);
            if (incomingToken.isValid()) {
                Assert.assertEquals("1234", incomingToken.getSubject());
                Assert.assertEquals("admin", incomingToken.getAudience().get(0));
            }
        } catch (NoSuchAlgorithmException ex) {
            fail("Invalid Token" + ex.getMessage());
        }
    }

    @Test(expected = NoSuchAlgorithmException.class)
    public void testIncorrectHeader() throws NoSuchAlgorithmException {
        String token = new JWebToken(payload).toString();
        token = token.replaceAll("[^.]", "X");
        //verify and use
        JWebToken incomingToken;

        incomingToken = new JWebToken(token);
        if (incomingToken.isValid()) {
            Assert.assertEquals("1234", incomingToken.getSubject());
            Assert.assertEquals("admin", incomingToken.getAudience().get(0));
        }
    }
}
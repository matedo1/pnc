package org.jboss.pnc.common.test.security;

import org.jboss.pnc.common.security.Sha256;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:matejonnet@gmail.com">Matej Lazar</a>
 */
public class Sha256Test {

    @Test
    public void calculateSha256() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String encoded = Sha256.digest("The quick brown fox jumps over the lazy dog.");
        String expected = "ef537f25c895bfa782526529a9b63d97aa631564d5d789c2b765448c8635fb6c";

        Assert.assertEquals("Sha 256 should be 64 chars long.", encoded.length(), 64);
        Assert.assertEquals(expected, encoded);
    }
}

package com.fhhwz.courseplanner.entities.user;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * A registered user of the application.
 *
 * <p>The user never stores its plain-text password, only a salted
 * PBKDF2-HMAC-SHA256 hash in the form
 * {@code iterations:base64(salt):base64(hash)}. Create new users with
 * {@link #withPassword(String, String)} and check logins with
 * {@link #checkPassword(String)}.</p>
 */
public class User {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 100_000;
    private static final int SALT_BYTES = 16;
    private static final int HASH_BITS = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final String username;
    private final String passwordHash;

    /**
     * Recreates a user from stored data. Use {@link #withPassword(String, String)}
     * for a brand-new account.
     *
     * @param username     the unique name the user logs in with
     * @param passwordHash a hash previously produced by this class
     */
    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /**
     * Creates a new user, hashing the given password.
     *
     * @param username the unique name the user logs in with
     * @param password the plain-text password; it is not kept
     * @return the new user
     */
    public static User withPassword(String username, String password) {
        final byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        final byte[] hash = derive(password, salt, ITERATIONS, HASH_BITS);
        final Base64.Encoder encoder = Base64.getEncoder();
        return new User(username,
                ITERATIONS + ":" + encoder.encodeToString(salt) + ":" + encoder.encodeToString(hash));
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Checks whether the given password is this user's password.
     *
     * @param password the plain-text password to check
     * @return true if it matches
     */
    public boolean checkPassword(String password) {
        if (password == null || passwordHash == null) {
            return false;
        }
        final String[] parts = passwordHash.split(":");
        if (parts.length != 3) {
            return false;
        }
        try {
            final int iterations = Integer.parseInt(parts[0]);
            final byte[] salt = Base64.getDecoder().decode(parts[1]);
            final byte[] expected = Base64.getDecoder().decode(parts[2]);
            final byte[] actual = derive(password, salt, iterations, expected.length * 8);
            // Constant-time comparison, so timing doesn't leak how much matched.
            return MessageDigest.isEqual(expected, actual);
        }
        catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private static byte[] derive(String password, byte[] salt, int iterations, int bits) {
        final PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, bits);
        try {
            return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();
        }
        catch (GeneralSecurityException ex) {
            throw new IllegalStateException("PBKDF2 is not available on this JVM", ex);
        }
        finally {
            spec.clearPassword();
        }
    }
}

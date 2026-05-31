/**
 * Copyright 2026/6/1 ThierrySquirrel
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io.github.thierrysquirrel.codec.factory.utils;


import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: KeyFactoryUtils
 * Description:
 * date: 2026/6/1
 *
 * @author ThierrySquirrel
 * @since JDK 25
 */
public class KeyFactoryUtils {

    private static final Logger logger = Logger.getLogger(KeyFactoryUtils.class.getName());

    private KeyFactoryUtils() {
    }

    public static PublicKey getPublicKey(String algorithm, String publicKey) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        byte[] encodedKey = getEncodedKey(publicKey);

        try {
            return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (InvalidKeySpecException e) {
            String loeMsg = "getPublicKey Error";
            logger.log(Level.WARNING, loeMsg, e);
            return null;
        }
    }

    public static PrivateKey getPrivateKey(String algorithm, String privateKey) {
        KeyFactory keyFactory = getKeyFactory(algorithm);
        byte[] encodedKey = getEncodedKey(privateKey);

        try {
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (InvalidKeySpecException e) {
            String loeMsg = "getPrivateKey Error";
            logger.log(Level.WARNING, loeMsg, e);
            return null;
        }
    }

    private static KeyFactory getKeyFactory(String algorithm) {

        try {
            return KeyFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            String loeMsg = "getKeyFactory Error";
            logger.log(Level.WARNING, loeMsg, e);
            return null;
        }
    }

    private static byte[] getEncodedKey(String key) {
        byte[] encodedKey = key.getBytes();
        return Base64.getDecoder().decode(encodedKey);
    }
}

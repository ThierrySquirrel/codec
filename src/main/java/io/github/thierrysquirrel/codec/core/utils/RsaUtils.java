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

package io.github.thierrysquirrel.codec.core.utils;

import io.github.thierrysquirrel.codec.core.constants.CodecConstants;
import io.github.thierrysquirrel.codec.core.constants.CodecSizeConstants;
import io.github.thierrysquirrel.codec.factory.utils.KeyFactoryUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: RsaUtils
 * Description:
 * date: 2026/6/1
 *
 * @author ThierrySquirrel
 * @since JDK 25
 */
public class RsaUtils {

    private static final Logger logger = Logger.getLogger(RsaUtils.class.getName());

    private RsaUtils() {
    }

    /**
     * public RSA2 or RSA
     *
     * @param content   content
     * @param publicKey publicKey
     * @return String
     */
    public static String rsaEncrypt(String content, String publicKey) {
        publicKey = publicKey.replaceAll(CodecConstants.BASE_64_REGULAR_EXPRESSION.getValue(), "");
        byte[] bytes = null;
        try {
            PublicKey pubKey = KeyFactoryUtils.getPublicKey(CodecConstants.SIGN_TYPE_RSA.getValue(), publicKey);
            Cipher cipher = Cipher.getInstance(CodecConstants.SIGN_TYPE_RSA.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            byte[] data = content.getBytes(Charset.defaultCharset());
            byte[] encryptedData = getBytes(cipher, data, CodecSizeConstants.MAX_ENCRYPT_BLOCK.getValue());
            bytes = Base64.getEncoder().encode(encryptedData);
        } catch (Exception e) {
            String loeMsg = "EncryptContent = " + content + ",Charset.defaultCharset() = " + Charset.defaultCharset();
            logger.log(Level.WARNING, loeMsg, e);
        }
        return new String(bytes, Charset.defaultCharset());
    }

    /**
     * private RSA2 Or RSA
     *
     * @param content    content
     * @param privateKey privateKey
     * @return String
     */
    public static String rsaDecrypt(String content, String privateKey) {
        privateKey = privateKey.replaceAll(CodecConstants.BASE_64_REGULAR_EXPRESSION.getValue(), "");
        byte[] decryptedData = null;
        try {
            PrivateKey priKey = KeyFactoryUtils.getPrivateKey(CodecConstants.SIGN_TYPE_RSA.getValue(), privateKey);
            Cipher cipher = Cipher.getInstance(CodecConstants.SIGN_TYPE_RSA.getValue());
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] decodeData = Base64.getDecoder().decode(content.getBytes(Charset.defaultCharset()));
            decryptedData = getBytes(cipher, decodeData, CodecSizeConstants.MAX_DECRYPT_BLOCK.getValue());

        } catch (Exception e) {
            String loeMsg = "EncodeContent = " + content + ",Charset.defaultCharset() = " + Charset.defaultCharset();
            logger.log(Level.WARNING, loeMsg, e);
        }
        return new String(decryptedData, Charset.defaultCharset());
    }

    private static byte[] getBytes(Cipher cipher, byte[] data, Integer operatingLength) throws IllegalBlockSizeException, BadPaddingException, IOException {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        while (inputLen - offSet > 0) {
            if (inputLen - offSet > operatingLength) {
                cache = cipher.doFinal(data, offSet, operatingLength);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * operatingLength;
        }
        byte[] operatingData = out.toByteArray();
        out.close();
        return operatingData;
    }
}

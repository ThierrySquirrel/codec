/**
 * Copyright 2019 the original author or authors.
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
 */

package com.github.thierrysquirrel.codec.core.utils;

import com.github.thierrysquirrel.codec.core.constants.CodecConstants;
import com.github.thierrysquirrel.codec.core.constants.CodecSizeConstants;
import com.github.thierrysquirrel.codec.core.error.CodecException;
import com.github.thierrysquirrel.codec.factory.utils.KeyFactoryUtils;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * ClassName: RsaUtils
 * Description:
 * date: 2019/7/15 11:27
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class RsaUtils {
    private RsaUtils() {
    }

    /**
     * 公钥加密 RSA2兼容RSA
     *
     * @param content   待加密内容
     * @param publicKey 公钥
     * @return 密文内容
     * @throws CodecException CodecException
     */
    public static String rsaEncrypt(String content, String publicKey) throws CodecException {
        try {
            PublicKey pubKey = KeyFactoryUtils.getPublicKey (CodecConstants.SIGN_TYPE_RSA.getValue (), publicKey);
            Cipher cipher = Cipher.getInstance (CodecConstants.SIGN_TYPE_RSA.getValue ());
            cipher.init (Cipher.ENCRYPT_MODE, pubKey);

            byte[] data = content.getBytes (Charset.defaultCharset ());
            byte[] encryptedData = getBytes (cipher, data, CodecSizeConstants.MAX_ENCRYPT_BLOCK.getValue ());
            byte[] bytes = Base64.encodeBase64 (encryptedData);
            return new String (bytes, Charset.defaultCharset ());
        } catch (Exception e) {
            throw new CodecException ("EncryptContent = " + content + ",Charset.defaultCharset() = " + Charset.defaultCharset (),
                    e);
        }
    }

    /**
     * 私钥解密 RSA2兼容RSA
     *
     * @param content    待解密内容
     * @param privateKey 私钥
     * @return String
     * @throws CodecException CodecException
     */
    public static String rsaDecrypt(String content, String privateKey) throws CodecException {
        try {
            PrivateKey priKey = KeyFactoryUtils.getPrivateKey (CodecConstants.SIGN_TYPE_RSA.getValue (), privateKey);
            Cipher cipher = Cipher.getInstance (CodecConstants.SIGN_TYPE_RSA.getValue ());
            cipher.init (Cipher.DECRYPT_MODE, priKey);
            byte[] decodeData = Base64.decodeBase64 (content.getBytes (Charset.defaultCharset ()));
            byte[] decryptedData = getBytes (cipher, decodeData, CodecSizeConstants.MAX_DECRYPT_BLOCK.getValue ());

            return new String (decryptedData, Charset.defaultCharset ());
        } catch (Exception e) {
            throw new CodecException ("EncodeContent = " + content + ",Charset.defaultCharset() = " + Charset.defaultCharset (), e);
        }
    }

    private static byte[] getBytes(Cipher cipher, byte[] data, Integer operatingLength) throws IllegalBlockSizeException, BadPaddingException, IOException {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream ();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        while (inputLen - offSet > 0) {
            if (inputLen - offSet > operatingLength) {
                cache = cipher.doFinal (data, offSet, operatingLength);
            } else {
                cache = cipher.doFinal (data, offSet, inputLen - offSet);
            }
            out.write (cache, 0, cache.length);
            i++;
            offSet = i * operatingLength;
        }
        byte[] operatingData = out.toByteArray ();
        out.close ();
        return operatingData;
    }
}

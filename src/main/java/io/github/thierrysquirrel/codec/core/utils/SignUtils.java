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
import io.github.thierrysquirrel.codec.factory.utils.KeyFactoryUtils;

import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: SignUtils
 * Description:
 * date: 2026/6/1
 *
 * @author ThierrySquirrel
 * @since JDK 25
 */
public class SignUtils {

    private static final Logger logger = Logger.getLogger(SignUtils.class.getName());

    private SignUtils() {
    }

    /**
     * check
     *
     * @param content   content
     * @param sign      sign
     * @param publicKey publicKey
     * @param signType  signType
     * @return Boolean boolean
     */
    private static Boolean check(String signType, String content, String publicKey, String sign) {
        publicKey = publicKey.replaceAll(CodecConstants.BASE_64_REGULAR_EXPRESSION.getValue(), "");
        try {
            Signature signature = null;
            PublicKey pubKey = KeyFactoryUtils.getPublicKey(CodecConstants.SIGN_TYPE_RSA.getValue(), publicKey);
            if (CodecConstants.SIGN_TYPE_RSA.getValue().equals(signType)) {
                signature = Signature.getInstance(CodecConstants.SIGN_ALGORITHMS.getValue());
            } else if (CodecConstants.SIGN_TYPE_RSA2.getValue().equals(signType)) {
                signature = Signature.getInstance(CodecConstants.SIGN_SHA256RSA_ALGORITHMS.getValue());
            } else {
                return Boolean.FALSE;
            }
            signature.initVerify(pubKey);

            signature.update(content.getBytes(Charset.defaultCharset()));

            return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
        } catch (Exception e) {
            String loeMsg = "content = " + content + ",Charset.defaultCharset() = " + Charset.defaultCharset();
            logger.log(Level.WARNING, loeMsg, e);
            return Boolean.FALSE;
        }
    }

    public static Boolean rsaCheck(String content, String publicKey, String sign) {
        return check(CodecConstants.SIGN_TYPE_RSA.getValue(), content, publicKey, sign);
    }

    public static Boolean rsa2Check(String content, String publicKey, String sign) {
        return check(CodecConstants.SIGN_TYPE_RSA2.getValue(), content, publicKey, sign);
    }

    /**
     * sign
     *
     * @param signType   signType
     * @param content    content
     * @param privateKey privateKey
     * @return String
     */
    private static String sign(String signType, String content, String privateKey) {
        privateKey = privateKey.replaceAll(CodecConstants.BASE_64_REGULAR_EXPRESSION.getValue(), "");
        byte[] signed = null;
        try {
            PrivateKey priKey = KeyFactoryUtils.getPrivateKey(CodecConstants.SIGN_TYPE_RSA.getValue(), privateKey);
            Signature signature;
            if (CodecConstants.SIGN_TYPE_RSA.getValue().equals(signType)) {
                signature = Signature.getInstance(CodecConstants.SIGN_ALGORITHMS.getValue());
            } else if (CodecConstants.SIGN_TYPE_RSA2.getValue().equals(signType)) {
                signature = Signature.getInstance(CodecConstants.SIGN_SHA256RSA_ALGORITHMS.getValue());
            } else {
                return "sign Error";
            }

            signature.initSign(priKey);

            signature.update(content.getBytes(Charset.defaultCharset()));

            signed = signature.sign();


        } catch (Exception e) {
            String loeMsg = "content = " + content + ",Charset.defaultCharset() = " + Charset.defaultCharset();
            logger.log(Level.WARNING, loeMsg, e);
        }
        return new String(Base64.getEncoder().encode(signed));

    }

    public static String rsaSign(String content, String privateKey) {
        return sign(CodecConstants.SIGN_TYPE_RSA.getValue(), content, privateKey);
    }

    public static String rsa2Sign(String content, String privateKey) {
        return sign(CodecConstants.SIGN_TYPE_RSA2.getValue(), content, privateKey);
    }
}

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
import com.github.thierrysquirrel.codec.core.error.CodecException;
import com.github.thierrysquirrel.codec.factory.utils.KeyFactoryUtils;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * ClassName: SignUtils
 * Description:
 * date: 2019/7/15 11:57
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class SignUtils {
    private SignUtils() {
    }

    /**
     * 验签方法
     *
     * @param content   参数
     * @param sign      签名
     * @param publicKey 公匙
     * @param signType  签名类型
     * @return Boolean boolean
     */
    private static Boolean check(String signType, String content, String publicKey, String sign) throws CodecException {
        try {
            Signature signature = null;
            PublicKey pubKey = KeyFactoryUtils.getPublicKey (CodecConstants.SIGN_TYPE_RSA.getValue (), publicKey);
            if (CodecConstants.SIGN_TYPE_RSA.getValue ().equals (signType)) {
                signature = Signature.getInstance (CodecConstants.SIGN_ALGORITHMS.getValue ());
            } else if (CodecConstants.SIGN_TYPE_RSA2.getValue ().equals (signType)) {
                signature = Signature.getInstance (CodecConstants.SIGN_SHA256RSA_ALGORITHMS.getValue ());
            } else {
                throw new CodecException ("不是支持的签名类型");
            }
            signature.initVerify (pubKey);

            signature.update (content.getBytes (Charset.defaultCharset ()));

            return signature.verify (Base64.decodeBase64 (sign.getBytes ()));
        } catch (Exception e) {
            throw new CodecException ("content = " + content + ",Charset.defaultCharset() = " + Charset.defaultCharset (),
                    e);
        }
    }

    public static Boolean rsaCheck(String content, String publicKey, String sign) throws CodecException {
        return check (CodecConstants.SIGN_TYPE_RSA.getValue (), content, publicKey, sign);
    }

    public static Boolean rsa2Check(String content, String publicKey, String sign) throws CodecException {
        return check (CodecConstants.SIGN_TYPE_RSA2.getValue (), content, publicKey, sign);
    }

    /**
     * 签名
     *
     * @param signType   签名类型
     * @param content    参数
     * @param privateKey 私匙
     * @return String
     * @throws CodecException CodecException
     */
    private static String sign(String signType, String content, String privateKey) throws CodecException {
        try {
            PrivateKey priKey = KeyFactoryUtils.getPrivateKey (CodecConstants.SIGN_TYPE_RSA.getValue (), privateKey);
            Signature signature;
            if (CodecConstants.SIGN_TYPE_RSA.getValue ().equals (signType)) {
                signature = Signature.getInstance (CodecConstants.SIGN_ALGORITHMS.getValue ());
            } else if (CodecConstants.SIGN_TYPE_RSA2.getValue ().equals (signType)) {
                signature = Signature.getInstance (CodecConstants.SIGN_SHA256RSA_ALGORITHMS.getValue ());
            } else {
                throw new CodecException ("不是支持的签名类型 : : signType=" + signType);
            }

            signature.initSign (priKey);

            signature.update (content.getBytes (Charset.defaultCharset ()));

            byte[] signed = signature.sign ();

            return new String (Base64.encodeBase64 (signed));
        } catch (Exception e) {
            throw new CodecException ("content = " + content + ",Charset.defaultCharset() = " + Charset.defaultCharset (),
                    e);
        }

    }

    public static String rsaSign(String content, String privateKey) throws CodecException {
        return sign (CodecConstants.SIGN_TYPE_RSA.getValue (), content, privateKey);
    }

    public static String rsa2Sign(String content, String privateKey) throws CodecException {
        return sign (CodecConstants.SIGN_TYPE_RSA2.getValue (), content, privateKey);
    }
}

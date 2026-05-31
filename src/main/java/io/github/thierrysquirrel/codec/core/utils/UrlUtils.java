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

import io.github.thierrysquirrel.codec.core.recursion.UrlRecursion;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: UrlUtils
 * Description:
 * date: 2026/6/1
 *
 * @author ThierrySquirrel
 * @since JDK 25
 */
public class UrlUtils {

    private static final Logger logger = Logger.getLogger(UrlUtils.class.getName());

    private UrlUtils() {
    }

    public static String encode(String url) {
        try {
            return new UrlRecursion(url).encode();
        } catch (Exception e) {
            String loeMsg = "encode error";
            logger.log(Level.WARNING, loeMsg, e);
            return loeMsg;
        }
    }

    public static String decode(String url) {
        try {
            return new UrlRecursion(url).decode();
        } catch (Exception e) {
            String loeMsg = "decode error";
            logger.log(Level.WARNING, loeMsg, e);
            return loeMsg;
        }
    }
}

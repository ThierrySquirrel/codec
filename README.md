#   codec

Encryption and Decryption Toolkit

[中文](./README_zh_CN.md)  

Support function：
- [x] RSA and RSA2 Encryption and Decryption
- [x] Signature and Verification of RSA and RSA2
- [x] URL Coding and Decoding

```xml
<!--Adding dependencies to pom. XML-->
        <dependency>
            <artifactId>codec</artifactId>
            <groupId>com.github.thierrysquirrel</groupId>
            <version>1.2.0.1-RELEASE</version>
        </dependency>
```

##  RSA and RSA2 Encryption and Decryption

```java
public class RsaAndRsa2 {
	public static void main(String[] args) {
		try {
			String encrypt = RsaUtils.rsaEncrypt("String to be encrypted", "Your publicKey RSA or RSA2");
			String rsaDecrypt = RsaUtils.rsaDecrypt(encrypt, "Your privateKey RSA or RSA2");
		} catch (CodecException e) {
			e.printStackTrace();
			System.out.println("rsaEncrypt or rsaDecrypt fail");
		}
	}
}
```

##  Signature and Verification of RSA and RSA2

```java
public class Sign {
	public static void main(String[] args) {
		try {
			String rsaSign = SignUtils.rsaSign("Strings that need to be signed", "Your privateKey RSA");
			Boolean resultRsa = SignUtils.rsaCheck("String before signature", "Your publicKey RSA", rsaSign);
			String rsa2Sign = SignUtils.rsa2Sign("Strings that need to be signed", "Your privateKey RSA2");
			Boolean resultRsa2 = SignUtils.rsa2Check("String before signature", "Your publicKey RSA2", rsa2Sign);
		} catch (CodecException e) {
			e.printStackTrace();
			System.out.println("Sign or Check fail");
		}
	}
}
```

##  URL Coding and Decoding

```java
public class TextC {
	public static void main(String[] args) {
		try {
			String encode = UrlUtils.encode("https://xxx.xxx.xxx?hello=world&this=encode");
			String decode = UrlUtils.decode(encode);
		} catch (CodecException e) {
			e.printStackTrace();
			System.out.println("encode or decode fail");
		}
	}
}
```

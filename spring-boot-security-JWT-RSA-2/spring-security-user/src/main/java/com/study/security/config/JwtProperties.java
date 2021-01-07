package com.study.security.config;

import com.study.security.utils.RsaUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 11:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties implements Serializable {

    private static final long serialVersionUID = -1841221870245499277L;

    private String secret;
    private String pubKeyPath;
    private String priKeyPath;
    private Integer expire;
    private String cookieName;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void readKey() {
        try {
            File pubKeyFile = new File(pubKeyPath);
            File priKeyFile = new File(priKeyPath);
            if (!priKeyFile.exists() || !pubKeyFile.exists()) {
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
            privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("公钥和私钥读取失败");
        }
    }

}

package com.study.security.config;

import com.study.security.utils.RsaUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class JwtProperties implements Serializable {
    private static final long serialVersionUID = 7986191925759196318L;

    private String secret;
    private String pubKeyPath;
    private String priKeyPath;
    private Integer expire;
    private String cookieName;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void readFile(){
        try {
            File pubKeyFile = new File(pubKeyPath);
            File priKeyFile = new File(priKeyPath);
            if (!pubKeyFile.exists() || !priKeyFile.exists()){
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败。", e);
            throw new RuntimeException("初始化公钥和私钥失败。");
        }
    }

}

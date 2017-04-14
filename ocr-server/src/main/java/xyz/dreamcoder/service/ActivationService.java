package xyz.dreamcoder.service;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.dreamcoder.config.ActivationProperties;

import java.io.*;
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;

@Service
public class ActivationService {

    private final ActivationProperties properties;

    @Autowired
    public ActivationService(ActivationProperties properties) {
        this.properties = properties;
    }

    public void verify() {

        // get mac address as machine identifier.
        String macAddress = getMacAddress();
        if (Strings.isNullOrEmpty(macAddress)) {
            throw new RuntimeException("Can't get mac address of this machine.");
        }

        try {
            verifyActivation(macAddress);
        } catch (Exception e) {

            String message = "*************************************************\n" +
                             "                                                 \n" +
                             "             VERIFICATION ERROR!!!               \n" +
                             "                                                 \n" +
                             "     MACHINE IDENTIFIER: %s                      \n" +
                             "                                                 \n" +
                             "*************************************************\n";
            System.out.println(String.format(message, macAddress));
            throw new RuntimeException(e);
        }
    }

    private void verifyActivation(String macAddress) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        // get activation code
        Path codePath = Paths.get(properties.getCodePath());
        System.out.println(codePath);
        System.out.println(Files.exists(codePath));
        if (!Files.exists(codePath)) {
            throw new RuntimeException("Activation code is not exist.");
        }
        String activationCode = CharStreams.toString(new InputStreamReader(new FileInputStream(codePath.toFile())));

        // get text and signature
        String[] textAndSignature = activationCode.split(":");
        if (textAndSignature.length != 2) {
            throw new RuntimeException("Activation code is not valid.");
        }

        String text = textAndSignature[0];
        String signature = textAndSignature[1];

        // decode mac and expire
        String[] macAndExpire = new String(Base64.getDecoder().decode(text), "utf-8").split(":");
        if (macAndExpire.length != 2) {
            throw new RuntimeException("Activation code is not valid.");
        }

        // compare mac address and expire time
        String mac = macAndExpire[0];
        long expire = Long.parseLong(macAndExpire[1]) * 1000;

        if (!mac.equals(macAddress)) {
            throw new RuntimeException("Machine identifier does not match.");
        }

        if (new Date(expire).before(new Date())) {
            throw new RuntimeException("Activation code is expired.");
        }

        PublicKey publicKey = getPublicKey();
        Signature sign = Signature.getInstance("SHA256WithRSA");
        sign.initVerify(publicKey);
        sign.update(text.getBytes("UTF-8"));

        if (!sign.verify(Base64.getDecoder().decode(signature))) {
            throw new RuntimeException("Signature is invalid.");
        }
    }

    private String getMacAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces.hasMoreElements()) {

                byte[] mac = interfaces.nextElement().getHardwareAddress();
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < mac.length; i++) {
                    builder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }

                return builder.toString();
            }

            return null;

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey getPublicKey() {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("public.der");
            byte[] bytes = ByteStreams.toByteArray(in);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(spec);

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
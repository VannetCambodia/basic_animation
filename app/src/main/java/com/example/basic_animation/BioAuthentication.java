package com.example.basic_animation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.Toast;

import com.example.basic_animation.databinding.ActivityBioAuthenticationBinding;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@RequiresApi(api = Build.VERSION_CODES.M)
public class BioAuthentication extends AppCompatActivity {

    private ActivityBioAuthenticationBinding bioAuthenticationBinding;
    private static final String KEY_NAME = "BIO_AUTHENTICATION";
    private KeyStore keyStore;
    private Cipher cipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bioAuthenticationBinding = ActivityBioAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(bioAuthenticationBinding.getRoot());

        bioAuthenticationBinding.button2.setOnClickListener(v -> finish());

        // Initializing both Android Keyguard Manager and Fingerprint Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if(!fingerprintManager.isHardwareDetected()){
            Toast.makeText(getApplicationContext(),"Your device does't support finger print", Toast.LENGTH_SHORT).show();
        }else{
            // Checks whether fingerprint is set on manifest
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"Fingerprint Authentication permission not enabled", Toast.LENGTH_SHORT).show();
            }else{
                // Check whether at least one fingerprint is registered
                if(!fingerprintManager.hasEnrolledFingerprints()){
                    Toast.makeText(getApplicationContext(),"Register at least one fingerprint not enabled", Toast.LENGTH_SHORT).show();
                }else{
                    // check whether lock screen security is enabled or not
                    if(!keyguardManager.isKeyguardSecure()){
                        Toast.makeText(getApplicationContext(),"Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show();
                    }else {
                        generateKey();

                        if(cipherInit()){
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerPrintHandler fingerPrintHandler = new FingerPrintHandler(this);
                            fingerPrintHandler.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }

    }

    private boolean cipherInit(){
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }catch (NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try{
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        }catch (KeyPermanentlyInvalidatedException e){
            return false;
        }catch (KeyStoreException | CertificateException | UnrecoverableEntryException
                | IOException | NoSuchAlgorithmException | InvalidKeyException e){
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void generateKey(){
        try{
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        }catch (Exception e){
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        }catch (NoSuchAlgorithmException | NoSuchProviderException e){
            throw new RuntimeException("Failed to get KeyGenerator instance",e);
        }

        try{
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(
                    KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
            )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();
        }catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException |
                CertificateException | IOException e){
            throw new RuntimeException(e);
        }
    }
}
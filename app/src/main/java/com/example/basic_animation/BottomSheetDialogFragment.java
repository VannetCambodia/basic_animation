package com.example.basic_animation;

import android.app.KeyguardManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.basic_animation.databinding.FragmentBottomSheetDialogBinding;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BottomSheetDialogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    public static final String KEY_NAME = "BIO_AUTHENTICATION";

    private FragmentBottomSheetDialogBinding fBinding;
    private Button btnCancel;

    // BIO AUTHENTICATION
    private KeyStore keyStore;
    private Cipher cipher;

    public BottomSheetDialogFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BottomSheetDialogFragment newInstance() {
        BottomSheetDialogFragment fragment = new BottomSheetDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fBinding = FragmentBottomSheetDialogBinding.inflate(getLayoutInflater());

        btnCancel = fBinding.button2;

        btnCancel.setOnClickListener(v -> dismiss());

        return fBinding.getRoot();
    }
}
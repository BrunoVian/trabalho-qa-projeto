package br.com.securityprofit.ui.mensagem;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.securityprofit.R;

public class MsgAlteraSenhaUtil {

    public interface CustomDialogListener {
        void onPositiveButtonClick(String novaSenha, String repitaSenha);

        void onNegativeButtonClick();
    }

    public static void showCustomDialog(Context context, CustomDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_msg_altera_senha_util, null);

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        TextInputLayout textInputLayoutSenha = view.findViewById(R.id.textInputLayoutSenha);
        TextInputEditText edtSenha = view.findViewById(R.id.edtSenha);

        edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        textInputLayoutSenha.setEndIconDrawable(R.drawable.visivel_off);
        textInputLayoutSenha.setHintEnabled(false);

        textInputLayoutSenha.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputType = edtSenha.getInputType();

                if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    textInputLayoutSenha.setEndIconDrawable(R.drawable.baseline_remove_red_eye_24);
                } else {
                    edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    textInputLayoutSenha.setEndIconDrawable(R.drawable.visivel_off);
                }

                edtSenha.setSelection(edtSenha.getText().length());
            }
        });

        TextInputLayout textInputLayoutSenhaConfirm = view.findViewById(R.id.textInputLayoutSenhaConfirm);
        TextInputEditText edtSenhaConfirm = view.findViewById(R.id.edtSenhaConfirm);

        edtSenhaConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        textInputLayoutSenhaConfirm.setEndIconDrawable(R.drawable.visivel_off);
        textInputLayoutSenhaConfirm.setHintEnabled(false);

        textInputLayoutSenhaConfirm.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputType = edtSenhaConfirm.getInputType();

                if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    edtSenhaConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    textInputLayoutSenhaConfirm.setEndIconDrawable(R.drawable.baseline_remove_red_eye_24);
                } else {
                    edtSenhaConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    textInputLayoutSenhaConfirm.setEndIconDrawable(R.drawable.visivel_off);
                }

                edtSenhaConfirm.setSelection(edtSenhaConfirm.getText().length());
            }
        });

        Button buttonYes = view.findViewById(R.id.button_yes);
        Button buttonNo = view.findViewById(R.id.button_no);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novaSenha = edtSenha.getText().toString();
                String repitaSenha = edtSenhaConfirm.getText().toString();

                if (!novaSenha.isEmpty() && !repitaSenha.isEmpty() && novaSenha.equals(repitaSenha)) {
                    listener.onPositiveButtonClick(novaSenha, repitaSenha);
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                }


            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNegativeButtonClick();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}

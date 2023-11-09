package br.com.securityprofit.ui.mensagem;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import br.com.securityprofit.R;

public class MsgCancelarUtil {

    public interface CustomDialogListener {
        void onPositiveButtonClick();
        void onNegativeButtonClick();
    }

    public static void showCustomDialog(Context context, CustomDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_msg_cancelar, null);

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        Button buttonYes = view.findViewById(R.id.button_yes);
        Button buttonNo = view.findViewById(R.id.button_no);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onPositiveButtonClick();
                alertDialog.dismiss();

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

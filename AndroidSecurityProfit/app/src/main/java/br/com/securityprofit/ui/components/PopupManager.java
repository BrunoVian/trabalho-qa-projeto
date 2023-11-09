package br.com.securityprofit.ui.components;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;
import br.com.securityprofit.R;

public class PopupManager {

    public static void showPopup(Activity activity, String message) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_popup_sucesso, null);

        TextView popupMessage = popupView.findViewById(R.id.popup_message);
        popupMessage.setText(message);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        Animation slideDown = AnimationUtils.loadAnimation(activity, R.anim.slide_down);

        popupView.startAnimation(slideDown);

        int[] location = new int[2];
        popupView.getLocationOnScreen(location);

        int yOffset = 150;

        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, location[0], location[1] + yOffset);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, 3000);
    }
}


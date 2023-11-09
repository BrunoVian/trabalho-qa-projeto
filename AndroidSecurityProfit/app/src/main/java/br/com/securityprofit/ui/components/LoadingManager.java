package br.com.securityprofit.ui.components;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.securityprofit.R;

public class LoadingManager {

    private static LoadingManager instance;
    private View loadingView;
    private ViewGroup parentViewGroup;
    private boolean isLoadingVisible = false;

    private LoadingManager() {
    }

    public static LoadingManager getInstance() {
        if (instance == null) {
            instance = new LoadingManager();
        }
        return instance;
    }

    public void showLoading(Context context) {
        if (!isLoadingVisible) {
            LayoutInflater inflater = LayoutInflater.from(context);
            loadingView = inflater.inflate(R.layout.fragment_loading, null);
            parentViewGroup = ((Activity) context).findViewById(android.R.id.content);
            parentViewGroup.addView(loadingView);
            isLoadingVisible = true;
        }
    }

    public void hideLoading() {
        if (loadingView != null && parentViewGroup != null) {
            parentViewGroup.removeView(loadingView);
            isLoadingVisible = false;
        }
    }
}

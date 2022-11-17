package com.example.moneymanager.Model.Profile;

import android.widget.ImageView;

public class StatusCard {
    String statusTitle;
    String statusColor;
    int statusIcon;

    public StatusCard(String statusTitle, String statusColor, int statusIcon) {
        this.statusTitle = statusTitle;
        this.statusColor = statusColor;
        this.statusIcon = statusIcon;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    public int getStatusIcon() {
        return statusIcon;
    }

    public void setStatusIcon(int statusIcon) {
        this.statusIcon = statusIcon;
    }
}

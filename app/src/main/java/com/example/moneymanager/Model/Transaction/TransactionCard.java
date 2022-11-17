package com.example.moneymanager.Model.Transaction;

import android.widget.ImageView;
import android.widget.TextView;

public class TransactionCard {
    public ImageView cardCategoryBar;
    public ImageView cardCategoryIcon;
    public TextView cardAmount;
    public TextView cardDes;
    public TextView cardDate;

    public TransactionCard(ImageView cardCategoryBar, ImageView cardCategoryIcon, TextView cardAmount, TextView cardDes, TextView cardDate) {
        this.cardCategoryBar = cardCategoryBar;
        this.cardCategoryIcon = cardCategoryIcon;
        this.cardAmount = cardAmount;
        this.cardDes = cardDes;
        this.cardDate = cardDate;
    }

    public ImageView getCardCategoryBar() {
        return cardCategoryBar;
    }

    public void setCardCategoryBar(ImageView cardCategoryBar) {
        this.cardCategoryBar = cardCategoryBar;
    }

    public ImageView getCardCategoryIcon() {
        return cardCategoryIcon;
    }

    public void setCardCategoryIcon(ImageView cardCategoryIcon) {
        this.cardCategoryIcon = cardCategoryIcon;
    }

    public TextView getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(TextView cardAmount) {
        this.cardAmount = cardAmount;
    }

    public TextView getCardDes() {
        return cardDes;
    }

    public void setCardDes(TextView cardDes) {
        this.cardDes = cardDes;
    }

    public TextView getCardDate() {
        return cardDate;
    }

    public void setCardDate(TextView cardDate) {
        this.cardDate = cardDate;
    }
}

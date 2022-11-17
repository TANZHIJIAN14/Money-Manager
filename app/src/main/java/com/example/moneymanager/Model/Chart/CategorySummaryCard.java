package com.example.moneymanager.Model.Chart;

public class CategorySummaryCard {
    private String category;
    private String categoryPercentageIncome;
    private String categoryPercentageExpense;
    private String categoryTotalAmountIncome;
    private String categoryTotalAmountExpense;
    private String typeCashFlow;

    public CategorySummaryCard(String category, String categoryPercentageIncome, String categoryPercentageExpense, String categoryTotalAmountIncome, String categoryTotalAmountExpense, String typeCashFlow) {
        this.category = category;
        this.categoryPercentageIncome = categoryPercentageIncome;
        this.categoryPercentageExpense = categoryPercentageExpense;
        this.categoryTotalAmountIncome = categoryTotalAmountIncome;
        this.categoryTotalAmountExpense = categoryTotalAmountExpense;
        this.typeCashFlow = typeCashFlow;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryPercentageIncome() {
        return categoryPercentageIncome;
    }

    public void setCategoryPercentageIncome(String categoryPercentageIncome) {
        this.categoryPercentageIncome = categoryPercentageIncome;
    }

    public String getCategoryPercentageExpense() {
        return categoryPercentageExpense;
    }

    public void setCategoryPercentageExpense(String categoryPercentageExpense) {
        this.categoryPercentageExpense = categoryPercentageExpense;
    }

    public String getCategoryTotalAmountIncome() {
        return categoryTotalAmountIncome;
    }

    public void setCategoryTotalAmountIncome(String categoryTotalAmountIncome) {
        this.categoryTotalAmountIncome = categoryTotalAmountIncome;
    }

    public String getCategoryTotalAmountExpense() {
        return categoryTotalAmountExpense;
    }

    public void setCategoryTotalAmountExpense(String categoryTotalAmountExpense) {
        this.categoryTotalAmountExpense = categoryTotalAmountExpense;
    }

    public String getTypeCashFlow() {
        return typeCashFlow;
    }

    public void setTypeCashFlow(String typeCashFlow) {
        this.typeCashFlow = typeCashFlow;
    }
}

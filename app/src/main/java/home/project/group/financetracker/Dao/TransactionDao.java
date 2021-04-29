package home.project.group.financetracker.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;

@Dao
public interface TransactionDao {

    @Insert
    void insertAllExpenseData(ExpenseTransactionModel model);

    @Insert
    void insertAllRevenueData(RevenueTransactionModel model);

    @Insert
    void insertAllCategoriesData(CategoriesModel model);

    //Select All Expense Data
    @Query("SELECT * FROM EXPENSE")
    List<ExpenseTransactionModel> getAllExpenseData();

    //Select All Revenue Data
    @Query("SELECT * FROM REVENUE")
    List<RevenueTransactionModel> getAllRevenueData();

    //Select All Categories by Type
    @Query("SELECT * FROM CATEGORIES WHERE `type` = :type")
    List<CategoriesModel> getAllTypeCategories(String type);

    //Select All Categories by key (primary key)
    @Query("SELECT * FROM CATEGORIES WHERE `key` = :key")
    CategoriesModel getCategoryByKey(int key);

    //Check for duplicate categories
    @Query("SELECT * FROM CATEGORIES WHERE `name` = :name")
    CategoriesModel checkForDuplicateCategory(String name);

    //Update Expense Data
    @Query("UPDATE EXPENSE SET `expenseName` = :expenseName, `amount` = :amount, `note` = :note WHERE `key` = :key")
    void updateExpenseData(int key, String expenseName, double amount, String note);

    //Update Revenue Data
    @Query("UPDATE REVENUE SET `revenueName` = :revenueName, `amount` = :amount, `note` = :note WHERE `key` = :key")
    void updateRevenueData(int key, String revenueName, double amount, String note);

    //Delete Expense Data
    @Query("DELETE FROM EXPENSE WHERE `key`= :id")
    void deleteExpenseData(int id);

    //Delete Revenue Data
    @Query("DELETE FROM REVENUE WHERE `key`= :id")
    void deleteRevenueData(int id);

    //Search Expense Data
    @Query("SELECT * FROM EXPENSE WHERE `expenseName` LIKE :transactionName")
    List<ExpenseTransactionModel> searchExpenseTransaction(String transactionName);

    //Search Revenue Data
    @Query("SELECT * FROM REVENUE WHERE `revenueName` LIKE :transactionName")
    List<ExpenseTransactionModel> searchRevenueTransaction(String transactionName);
}

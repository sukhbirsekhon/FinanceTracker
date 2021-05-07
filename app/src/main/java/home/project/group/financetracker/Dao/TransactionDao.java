package home.project.group.financetracker.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.EntityClass.TransactionModel;

@Dao
public interface TransactionDao {

    @Insert
    void insertAllExpenseData(ExpenseTransactionModel model);

    @Insert
    void insertAllRevenueData(RevenueTransactionModel model);

    @Insert
    void insertAllTransactionData(TransactionModel model);

    @Insert
    void insertAllCategoriesData(CategoriesModel model);

    //Select All Expense Data
    @Query("SELECT * FROM EXPENSE")
    List<ExpenseTransactionModel> getAllExpenseData();

    //Select All Revenue Data
    @Query("SELECT * FROM REVENUE")
    List<RevenueTransactionModel> getAllRevenueData();

    //Select Transaction Data
    @Query("SELECT * FROM TRANSACTIONS")
    List<TransactionModel> getAllTransactionData();

    //Select All Categories by Type
    @Query("SELECT * FROM CATEGORIES WHERE `type` = :type")
    List<CategoriesModel> getAllTypeCategories(String type);

    //Select All Categories by key (primary key)
    @Query("SELECT * FROM CATEGORIES WHERE `key` = :key")
    CategoriesModel getCategoryByKey(int key);

    //Check for duplicate categories
    @Query("SELECT * FROM CATEGORIES WHERE `name` = :name")
    CategoriesModel checkForDuplicateCategory(String name);

    //Delete Expense Data
    @Query("DELETE FROM EXPENSE WHERE `key`= :id")
    void deleteExpenseData(int id);

    //Delete Revenue Data
    @Query("DELETE FROM REVENUE WHERE `key`= :id")
    void deleteRevenueData(int id);

    //Delete Transaction Data
    @Query("DELETE FROM TRANSACTIONS WHERE `key`= :id")
    void deleteTransactionData(int id);

    //Search Expense Data
    @Query("SELECT * FROM EXPENSE WHERE `expenseName` LIKE :transactionName")
    List<ExpenseTransactionModel> searchExpenseTransaction(String transactionName);

    //Search Revenue Data
    @Query("SELECT * FROM REVENUE WHERE `revenueName` LIKE :transactionName")
    List<ExpenseTransactionModel> searchRevenueTransaction(String transactionName);

    //Search Transaction Data
    @Query("SELECT * FROM TRANSACTIONS WHERE `name` LIKE :transactionName")
    List<TransactionModel> searchTransaction(String transactionName);

    //Get Monthly Expense data
    @Query("SELECT * FROM EXPENSE ORDER BY DATE")
    List<ExpenseTransactionModel> monthlyExpense();

    //Get Monthly Revenue data
    @Query("SELECT * FROM REVENUE ORDER BY DATE")
    List<RevenueTransactionModel> monthlyRevenue();

    //Get Monthly Transaction data
    @Query("SELECT * FROM TRANSACTIONS ORDER BY DATE")
    List<TransactionModel> monthlyTransaction();

    //Get unique categories and added amount
    @Query("SELECT `key`, name, type, SUM(amount) as amount, category, note, date FROM TRANSACTIONS GROUP BY category")
    List<TransactionModel> getGroupedCategories();
}

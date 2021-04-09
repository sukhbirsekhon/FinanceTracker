package home.project.group.financetracker.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;

@Dao
public interface TransactionDao {

    @Insert
    void insertAllExpenseData(ExpenseTransactionModel model);

    @Insert
    void insertAllRevenueData(RevenueTransactionModel model);

    //Select All Expense Data
    @Query("SELECT * FROM EXPENSE")
    List<ExpenseTransactionModel> getAllExpenseData();

    //Select All Revenue Data
    @Query("SELECT * FROM REVENUE")
    List<RevenueTransactionModel> getAllRevenueData();

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

package home.project.group.financetracker.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.util.List;

import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.Converters;
import home.project.group.financetracker.EntityClass.TransactionModel;

@Dao
public interface TransactionDao {

    @Insert
    void insertAllTransactionData(TransactionModel model);

    @Insert
    void insertAllCategoriesData(CategoriesModel model);

    //Select Transaction Data
    @Query("SELECT * FROM TRANSACTIONS")
    List<TransactionModel> getAllTransactionData();

    //Update Transaction Data
    @TypeConverters({Converters.class})
    @Query("UPDATE TRANSACTIONS SET `type` = :type, `name` = :name, `amount` = :amount, `category` = :category, `date` = :date, `note` = :note WHERE `key` = :key")
    void updateTransactionData(int key, String type, String name, double amount, String category, Date date, String note);

    //Delete Transaction Data
    @Query("DELETE FROM TRANSACTIONS WHERE `key`= :id")
    void deleteTransactionData(int id);

    //Delete All Categories
    @Query("DELETE FROM CATEGORIES")
    void deleteCategories();

    //Search Transaction Data
    @Query("SELECT * FROM TRANSACTIONS WHERE `name` LIKE :transactionName")
    List<TransactionModel> searchTransaction(String transactionName);

    //Get Monthly Transaction data
    @Query("SELECT * FROM TRANSACTIONS ORDER BY DATE")
    List<TransactionModel> monthlyTransaction();

    //Get unique categories and added amount
    @Query("SELECT `key`, name, type, SUM(amount) as amount, category, note, date FROM TRANSACTIONS GROUP BY category")
    List<TransactionModel> getGroupedCategories();

    //Select All Categories by Type
    @Query("SELECT * FROM CATEGORIES WHERE `type` = :type")
    List<CategoriesModel> getAllTypeCategories(String type);

    //Select All Categories by key (primary key)
    @Query("SELECT * FROM CATEGORIES WHERE `key` = :key")
    CategoriesModel getCategoryByKey(int key);

    //Check for duplicate categories
    @Query("SELECT * FROM CATEGORIES WHERE `name` = :name")
    CategoriesModel checkForDuplicateCategory(String name);
}

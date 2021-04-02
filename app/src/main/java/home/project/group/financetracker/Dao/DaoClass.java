package home.project.group.financetracker.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;

@Dao
public interface DaoClass {

    @Insert
    void insertAllData(ExpenseTransactionModel model);

    //Select All Data
    @Query("SELECT * FROM EXPENSE")
    List<ExpenseTransactionModel> getAllData();
}

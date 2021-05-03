package home.project.group.financetracker.ui.calendar;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import home.project.group.financetracker.Dao.TransactionDao;
import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;
import home.project.group.financetracker.EntityClass.RevenueTransactionModel;
import home.project.group.financetracker.EntityClass.TransactionModel;

@Database(entities = {ExpenseTransactionModel.class, RevenueTransactionModel.class, CategoriesModel.class, TransactionModel.class}, version = 7)
public abstract class DatabaseClass extends RoomDatabase {

    private static DatabaseClass instance;

    static DatabaseClass getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DatabaseClass.class) {
                instance = Room.databaseBuilder(context, DatabaseClass.class, "DATABASE").fallbackToDestructiveMigration().allowMainThreadQueries().build();
            }
        }
        return instance;
    }

    public abstract TransactionDao getDao();
}

package home.project.group.financetracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import home.project.group.financetracker.Dao.DaoClass;
import home.project.group.financetracker.EntityClass.ExpenseTransactionModel;

@Database(entities = {ExpenseTransactionModel.class}, version = 1)
public abstract class DatabaseClass extends RoomDatabase {
    public abstract DaoClass getDao();

    private static DatabaseClass instance;

    static DatabaseClass getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DatabaseClass.class) {
                instance = Room.databaseBuilder(context, DatabaseClass.class, "DATABASE").build();
            }
        }
        return instance;
    }
}

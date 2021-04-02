package home.project.group.financetracker.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class ExpenseTransactionModel {
    //Primary Key
    @PrimaryKey(autoGenerate = true)
    private int key;

    @ColumnInfo(name = "expenseName")
    private String expenseName;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "note")
    private String note;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

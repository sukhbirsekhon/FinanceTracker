package home.project.group.financetracker.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;
import home.project.group.financetracker.ui.transaction.DatabaseClass;
import home.project.group.financetracker.ui.transaction.TransactionFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    Button btnSaveHomePage;
    Button addIcon;
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private String today;
    private int dd, mm, yy;
    LinearLayout homePageView, popUpTransactionView;
    RelativeLayout spinnerAndButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_home, container, false);

        addIcon = (Button) root.findViewById(R.id.btn_add_icon);

        btnSaveHomePage = (Button) root.findViewById(R.id.btnSaveHomePage);
        dateTimeDisplay = root.findViewById(R.id.txt_Today_Date);
        final Calendar cal = Calendar.getInstance();
        dd = cal.get(Calendar.DAY_OF_MONTH);
        mm = cal.get(Calendar.MONTH);
        dateFormat = new SimpleDateFormat("MMMM" + " dd");
        /*  If today is April, 21st
            date = 04/21 */
        date = dateFormat.format(cal.getTime());
        /*  date = April 16 */
        today = "Today is " + date;
        dateTimeDisplay.setText(today);
        /*------------------------------*/

        /* Code for extracting the username and creating a welcome message */

        /* I can't add with a name until we complete the registration UI and development. For now, I just have a message using the design code. - Nick */

        /*------------------------------*/

        homePageView = root.findViewById(R.id.homePageView);
        popUpTransactionView = root.findViewById(R.id.popUpTransactionView);
        addIcon.setOnClickListener(this);
        btnSaveHomePage.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_icon:
                homePageView.setVisibility(View.GONE);
                popUpTransactionView.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSaveHomePage:
                homePageView.setVisibility(View.VISIBLE);
                popUpTransactionView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}



  /*

          View myView = view.findViewById(R.id.navigation_transaction);
                myView.performClick();

   /*   MenuItem menuItem = (MenuItem) view.findViewById(R.id.navigation_transaction);
                menuItem.setActionView(R.id.navigation_transaction);

    MenuItem actionRestart = (MenuItem) view.findViewById(R.id.navigation_transaction);
                actionRestart.getActionView();*/



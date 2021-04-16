package home.project.group.financetracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import home.project.group.financetracker.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private String today;
    private int dd, mm, yy;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /* Code for getting today's date*/
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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
        return root;
    }


}
package uz.codic.ahmadtea.ui.visit.zakaz.shippingDate;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Calendar;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.paymentTypes.PaymentFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;
import uz.codic.ahmadtea.utils.Consts;

import static uz.codic.ahmadtea.utils.Consts.paymentTag;
import static uz.codic.ahmadtea.utils.Consts.shippingTag;
import static uz.codic.ahmadtea.utils.Consts.visitTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingDateFragment extends Fragment {


    OnFragmentInteractionListener listener;
    TextView tvDate;
    ImageView calendarShipping;
    String date;
    EditText notesOrder;
    StateProgressBar progressBar;
    private int mYear, mMonth, mDay;

    public ShippingDateFragment() {
        // Required empty public constructor
    }

    public static ShippingDateFragment newInstance() {
        ShippingDateFragment shippingDateFragment = new ShippingDateFragment();
        return shippingDateFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipping_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = getActivity().findViewById(R.id.progress_state);
        progressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);

        getActivity().findViewById(R.id.btn_filter).setVisibility(View.GONE);
        getActivity().findViewById(R.id.btn_search).setVisibility(View.GONE);

        init(view);
        if (getActivity() != null) {
            getActivity().findViewById(R.id.btn_back).setOnClickListener(v -> {
                listener.transactionFragments(PaymentFragment.newInstance(), paymentTag);
                //listener.onBackClick(paymentTag);
            });

            getActivity().findViewById(R.id.btn_forward).setOnClickListener(v -> {
                getActivity().findViewById(R.id.lnr_buttons).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.progress_state).setVisibility(View.GONE);
                getActivity().findViewById(R.id.btn_forward).setVisibility(View.GONE);
                completeOrder();
                getFragmentManager().popBackStack(visitTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                listener.transactionFragments(VisitFragment.newInstance(), shippingTag);
            });
        }


    }

    private void completeOrder() {
        // order Object
        listener.getCompleteApi().getOrderObject().setVisitId(listener.getCompleteApi().getVisitObject().getId()); // Foreign key

        //paymentType //ALready set
        listener.getCompleteApi().getOrderObject().setId_merchant(listener.getCompleteObject().getMerchant().getId());//id_merchant
        listener.getCompleteApi().getOrderObject().setId_mmd(null);
        listener.getCompleteApi().getOrderObject().setTotal_cost_with_mmd(null);
        listener.getCompleteApi().getOrderObject().setNotes(notesOrder.getText().toString());
        listener.getCompleteApi().getOrderObject().setId_filial(listener.getCompleteObject().getWorkspace().getFilial_id());

        listener.getCompleteApi().getOrderObject().setId_workspace(listener.getCompleteObject().getWorkspace().getId());
        listener.getCompleteApi().getOrderObject().setId_warehouse(listener.getCompleteObject().getWorkspace().getWarehouse_id());
        listener.getCompleteApi().getOrderObject().setOrderComplete(true);
    }

    private void init(View view) {

        notesOrder = view.findViewById(R.id.notes_on_order);
        tvDate = view.findViewById(R.id.tvDateShipping);
        calendarShipping = view.findViewById(R.id.celenderShipping);
        try {
            notesOrder.setText(listener.getCompleteApi().getOrderObject().getNotes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listener.getCompleteApi().getOrderObject().getDelivery_date() != null) {
            date = listener.getCompleteApi().getOrderObject().getDelivery_date();
        } else date = getNaxtDay();
        tvDate.setText(date);
        listener.getCompleteApi().getOrderObject().setDelivery_date(date);

        calendarShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });

    }

    private void openCalendar() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (dayOfMonth < 10) {
                    date = year + "-0" + (month + 1) + "-0" + dayOfMonth;
                } else {
                    date = year + "-0" + (month + 1) + "-" + dayOfMonth;
                }
                tvDate.setText(date);
                listener.getCompleteApi().getOrderObject().setDelivery_date(date);
                allCompleted();
            }
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void allCompleted() {
        if (listener.getCompleteApi().getOrderObject().getId_price() != null &&
                listener.getCompleteApi().getOrderObject().getId_payment_type() != null &&
                listener.getCompleteApi().getOrderBasketList().size() > 0) {
            progressBar.setAllStatesCompleted(true);
        } else {
            //progressBar.set
        }
    }

    private String getNaxtDay() {
        Calendar calendar = Calendar.getInstance();
        String year, month = null, day = null;
        year = String.valueOf(calendar.get(Calendar.YEAR));
        switch (calendar.get(Calendar.MONTH)) {
            case 0:
                // yanvar
                if (calendar.get(Calendar.DAY_OF_MONTH) == 31) {
                    month = "02";
                    day = "01";
                } else {
                    month = "01";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 1:
                // fevral
                if (calendar.get(Calendar.DAY_OF_MONTH) == 28) {
                    month = "03";
                    day = "01";
                } else {
                    month = "02";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 2:
                //mart
                if (calendar.get(Calendar.DAY_OF_MONTH) == 31) {
                    month = "04";
                    day = "01";
                } else {
                    month = "03";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 3:
                //aprel
                if (calendar.get(Calendar.DAY_OF_MONTH) == 30) {
                    month = "05";
                    day = "01";
                } else {
                    month = "04";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 4:
                //may
                if (calendar.get(Calendar.DAY_OF_MONTH) == 31) {
                    month = "06";
                    day = "01";
                } else {
                    month = "05";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 5:
                //iyun
                if (calendar.get(Calendar.DAY_OF_MONTH) == 30) {
                    month = "07";
                    day = "01";
                } else {
                    month = "06";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 6:
                //iyul
                if (calendar.get(Calendar.DAY_OF_MONTH) == 31) {
                    month = "08";
                    day = "01";
                } else {
                    month = "07";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 7:
                //avgust
                if (calendar.get(Calendar.DAY_OF_MONTH) == 31) {
                    month = "09";
                    day = "01";
                } else {
                    month = "08";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 8:
                //sentyabr
                if (calendar.get(Calendar.DAY_OF_MONTH) == 30) {
                    month = "10";
                    day = "01";
                } else {
                    month = "09";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 9:
                //oktyabr
                if (calendar.get(Calendar.DAY_OF_MONTH) == 31) {
                    month = "11";
                    day = "01";
                } else {
                    month = "10";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 10:
                //noyabr
                if (calendar.get(Calendar.DAY_OF_MONTH) == 30) {
                    month = "12";
                    day = "01";
                } else {
                    month = "11";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
            case 11:
                //dekabr
                if (calendar.get(Calendar.DAY_OF_MONTH) == 31) {
                    year = String.valueOf((calendar.get(Calendar.YEAR) + 1));
                    month = "01";
                    day = "01";
                } else {
                    month = "12";
                    day = String.valueOf((calendar.get(Calendar.DAY_OF_MONTH) + 1));
                }
                ;
                break;
        }
        if (Integer.parseInt(day) < 10) day = "0" + day;
        return year + "-" + month + "-" + day;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {//if parentActivity implements listener,
            listener = (OnFragmentInteractionListener) context; // then we can get reference
            Log.d(Consts.TEST_TAG, "onAttach: ");
        } else {
            throw new RuntimeException(context.toString() + "must implement listener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}

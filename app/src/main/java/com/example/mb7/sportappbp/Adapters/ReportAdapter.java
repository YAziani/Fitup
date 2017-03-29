package com.example.mb7.sportappbp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mb7.sportappbp.Activity.ActivityDiaryEntry;
import com.example.mb7.sportappbp.Activity.ActivityFitnessFragebogen;
import com.example.mb7.sportappbp.Activity.ActivityFragebogen;
import com.example.mb7.sportappbp.Activity.ActivityMotivationMessage;
import com.example.mb7.sportappbp.Activity.ActivityStimmungsAbgabe;
import com.example.mb7.sportappbp.Activity.Activity_Differenz_rpt;
import com.example.mb7.sportappbp.Activity.Activity_EnergieIndex_rpt;
import com.example.mb7.sportappbp.Activity.Activity_FitnessFragebogen_rpt;
import com.example.mb7.sportappbp.Activity.Activity_Stimmungsbarometer_rpt;
import com.example.mb7.sportappbp.Activity.Activity_Trainings_rpt;
import com.example.mb7.sportappbp.Activity.Activity_bsa_rpt;
import com.example.mb7.sportappbp.BusinessLayer.Notification;
import com.example.mb7.sportappbp.BusinessLayer.Report;
import com.example.mb7.sportappbp.DataAccessLayer.DAL_Utilities;
import com.example.mb7.sportappbp.Fragments.TabFragment;
import com.example.mb7.sportappbp.MotivationMethods.MotivationMethod;
import com.example.mb7.sportappbp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by M.Braei on 27.03.2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {
    List<Report> reports;
    TabFragment context;

    public class ReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView txtTitle;
        TextView txtSubText;
        ImageView imageView;

        ReportHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitleReport);
            txtSubText = (TextView) itemView.findViewById(R.id.txtSubtextReport);
            cv = (CardView) itemView.findViewById(R.id.card_view_Reports);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewReports);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (reports.get(getAdapterPosition()).getTitle().equals(context.getString(R.string
                    .stimmungsbarometer_dgr_ttl))) {
                Intent open = new Intent(context.getActivity(), Activity_Stimmungsbarometer_rpt.class);
                context.startActivity(open);
            }
            if (reports.get(getAdapterPosition()).getTitle().equals(context.getString(R.string.energieindex_dgr_ttl))) {
                Intent open = new Intent(context.getActivity(), Activity_EnergieIndex_rpt.class);
                context.startActivity(open);
            }
            if (reports.get(getAdapterPosition()).getTitle().equals(context.getString(R.string.differenzwert_dgr_ttl)
            )) {
                Intent open = new Intent(context.getActivity(), Activity_Differenz_rpt.class);
                context.startActivity(open);
            }
            if (reports.get(getAdapterPosition()).getTitle().equals(context.getString(R.string
                    .punkteproWoche_dgr_ttl))) {
                Intent open = new Intent(context.getActivity(), Activity_Trainings_rpt.class);
                context.startActivity(open);
            }
            if (reports.get(getAdapterPosition()).getTitle().equals(context.getString(R.string.bsa_dgr_ttl))) {
                Intent open = new Intent(context.getActivity(), Activity_bsa_rpt.class);
                context.startActivity(open);
            }
            if (reports.get(getAdapterPosition()).getTitle().equals(context.getString(R.string.fitnessfragen_dgr_ttl)
            )) {
                Intent open = new Intent(context.getActivity(), Activity_FitnessFragebogen_rpt.class);
                context.startActivity(open);
            }
            /*
            if (notifications.get(getAdapterPosition()).getTitle().equals(context.getString(R.string.tagebucheintrag)
            )) {
                Intent open = new Intent(context.getActivity(), ActivityDiaryEntry.class);
                // insert the date of the notificatino in the extra which is the unique field to delete the
                notification from the database
                String NotificationDate = DAL_Utilities.ConvertDateTimeToFirebaseString(notifications.get
                (getAdapterPosition()).getDate());
                open.putExtra("NotificationDate",NotificationDate);
                context.startActivity(open);
            } else if (notifications.get(getAdapterPosition()).getTitle().equals(context.getString(R.string
            .stimmungsabgabe))) {
                Intent open = new Intent(context.getActivity(), ActivityStimmungsAbgabe.class);
                // insert the date of the notificatino in the extra which is the unique field to delete the
                notification from the database
                String NotificationDate = DAL_Utilities.ConvertDateTimeToFirebaseString(notifications.get
                (getAdapterPosition()).getDate());
                if (notifications.get(getAdapterPosition()).getSubText().equals(context.getString( R.string
                .ntf_stimmungsabgabe)))
                    open.putExtra("Vor","1");
                else
                    open.putExtra("Vor","0");
                open.putExtra("NotificationDate",NotificationDate);
                context.startActivity(open);
            } else if (notifications.get(getAdapterPosition()).getTitle().equals(context.getString(R.string
            .fitnessfragebogen))) {
                Intent open = new Intent(context.getActivity(), ActivityFitnessFragebogen.class);
                // insert the date of the notificatino in the extra which is the unique field to delete the
                notification from the database
                String NotificationDate = DAL_Utilities.ConvertDateTimeToFirebaseString(notifications.get
                (getAdapterPosition()).getDate());
                open.putExtra("NotificationDate",NotificationDate);
                context.startActivity(open);
            } else if (notifications.get(getAdapterPosition()).getTitle().equals(context.getString(R.string
            .aktivitaetsfragebogen) )) {
                Intent open = new Intent(context.getActivity(), ActivityFragebogen.class);
                // insert the date of the notificatino in the extra which is the unique field to delete the
                notification from the database
                String NotificationDate = DAL_Utilities.ConvertDateTimeToFirebaseString(notifications.get
                (getAdapterPosition()).getDate());
                open.putExtra("NotificationDate",NotificationDate);
                context.startActivity(open);
            } else if (notifications.get(getAdapterPosition()).getTitle().equals(context.getString(R.string
            .bewegen_sie_sich))) {
                Intent open = new Intent(context.getActivity(), ActivityMotivationMessage.class);
                // insert the date of the notificatino in the extra which is the unique field to delete the
                notification from the database
                String NotificationDate = DAL_Utilities.ConvertDateTimeToFirebaseString(notifications.get
                (getAdapterPosition()).getDate());
                open.putExtra("NotificationDate",NotificationDate);
                context.startActivity(open);
            } else if (notifications.get(getAdapterPosition()).getTitle().equals(context.getString(R.string
            .trNotiTitle))) {
                notifications.remove(notifications.get(getAdapterPosition()));
                String nextTrainingTime = PreferenceManager
                        .getDefaultSharedPreferences(context.getContext()).getString("nextTrainingTime","");
                if(!nextTrainingTime.equals("")) {
                    Toast.makeText(
                            context.getContext(),
                            context.getString(R.string.ihr_train_begin_in)
                                    +" "+ MotivationMethod.timeTillTraining(nextTrainingTime)
                                    +" "+ context.getString(R.string.minuten) + ".",
                            Toast.LENGTH_SHORT
                    ).show();
                    PreferenceManager
                            .getDefaultSharedPreferences(context.getActivity())
                            .edit()
                            .remove("nextTrainingTime").apply();
                }

            }*/

            // remove the notification that has been read
/*            notifications.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(),notifications.size());*/
        }
    }

    public ReportAdapter(List<Report> reports, TabFragment context) {
        this.reports = reports;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    @Override
    public ReportAdapter.ReportHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_reports, viewGroup, false);
        ReportAdapter.ReportHolder bvh = new ReportAdapter.ReportHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(final ReportAdapter.ReportHolder holder, final int position) {
        ReportAdapter.ReportHolder notificationHolder = (ReportAdapter.ReportHolder) holder;
        notificationHolder.txtTitle.setText(reports.get(position).getTitle());
        notificationHolder.txtSubText.setText(reports.get(position).getSubText());
        notificationHolder.imageView.setImageResource(reports.get(position).getImage());


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

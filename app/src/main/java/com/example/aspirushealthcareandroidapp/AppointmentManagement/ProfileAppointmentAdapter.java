package com.example.aspirushealthcareandroidapp.AppointmentManagement;

        import android.annotation.SuppressLint;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import com.example.aspirushealthcareandroidapp.R;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.firebase.database.core.Context;


public class ProfileAppointmentAdapter extends FirebaseRecyclerAdapter<AppointmentModel, ProfileAppointmentAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context context;
    public ProfileAppointmentAdapter(@NonNull FirebaseRecyclerOptions<AppointmentModel> options) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull AppointmentModel model) {
        holder.doctorName.setText(model.getDoctorName());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointment_list_profile,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName,date,time;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorName=(TextView) itemView.findViewById(R.id.appointment_doc_name);
            date=(TextView) itemView.findViewById(R.id.appointment_date);
            time=(TextView) itemView.findViewById(R.id.appointment_time);

        }
    }
}

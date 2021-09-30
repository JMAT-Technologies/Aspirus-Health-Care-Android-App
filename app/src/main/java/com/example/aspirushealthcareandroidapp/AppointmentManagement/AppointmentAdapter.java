package com.example.aspirushealthcareandroidapp.AppointmentManagement;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspirushealthcareandroidapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class AppointmentAdapter extends FirebaseRecyclerAdapter<AppointmentModel, AppointmentAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context context;
    public AppointmentAdapter(@NonNull FirebaseRecyclerOptions<AppointmentModel> options) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull AppointmentModel model) {
        holder.doctorName.setText(model.getDoctorName());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        //Edit
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.btnEdit.getContext()).setContentHolder(new ViewHolder(R.layout.dialog_appointment_update)).setExpanded(true,600).create();

                View myView = dialogPlus.getHolderView();
                TextView tv_doctorName = myView.findViewById(R.id.tv_doctorName);
                EditText time = myView.findViewById(R.id.et_appointmentUpdateTime);
                EditText date = myView.findViewById(R.id.et_appointmentUpdateDate);

                Button btn_appointmmentUpdate = myView.findViewById(R.id.btn_appointmmentUpdate);

                tv_doctorName.setText(model.getDoctorName());
                time.setText(model.getTime());
                date.setText(model.getDate());

                dialogPlus.show();

                //Update
                btn_appointmmentUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("time",time.getText().toString());
                        map.put("date",date.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Appointments")
                            .child(getRef(position).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(holder.doctorName.getContext(), "Appointment Updated", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure( Exception e) {
                                    dialogPlus.dismiss();
                                }
                            });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder buildDialog = new AlertDialog.Builder(holder.doctorName.getContext());
                buildDialog.setTitle("Are you Sure ?");
                buildDialog.setMessage("Cancelled Appointment can't be undo");

                buildDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Appointments")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.doctorName.getContext(), "Appointment Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                buildDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.doctorName.getContext(), "Cancellation failed", Toast.LENGTH_SHORT).show();
                    }
                });
                buildDialog.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName,date,time;
        Button btnDelete;
        Button btnEdit;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorName=(TextView) itemView.findViewById(R.id.appointment_doc_name);
            date=(TextView) itemView.findViewById(R.id.appointment_date);
            time=(TextView) itemView.findViewById(R.id.appointment_time);

            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}

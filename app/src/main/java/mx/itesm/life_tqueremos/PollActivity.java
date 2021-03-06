package mx.itesm.life_tqueremos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.tv.TvContract;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Debug;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.ConnectException;

public class PollActivity extends AppCompatActivity implements PollFragment.OnPollAnsweredListener,
                                                                Encuesta.OnPollReadyListener  {

    private String sNombreEncuesta;
    Encuesta encuesta;

    // Recibe resultado de encuesta y lo guarda en base de datos
    @Override
    public void onPollAnswered(int points) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .child("active").child(sNombreEncuesta).setValue(points);
        finish();
    }



    //    Esta funcion se llama cuando la encuesta se termina de cargar de la base de datos.
    //    Sirve para cargar la primera pregunta cuando este lista.
    @Override
    public void onPollReady() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(sNombreEncuesta.equals("Financiero")) {
            Log.d("Nombre", "Debi entrar a financiero");
            FinancieroFragment financieroFragment = FinancieroFragment.newInstance(encuesta);
            fragmentTransaction.replace(R.id.fragment_container, financieroFragment);
        }
        else {
            Log.d("Nombre", "No entre a financiero");
            PollFragment pollFragment = PollFragment.newInstance(encuesta);
            fragmentTransaction.replace(R.id.fragment_container, pollFragment);
        }

        fragmentTransaction.commit();


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        if(!isNetworkAvailable()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, ConnectionlessFragment.newInstance());
            fragmentTransaction.commit();

        }
        else if(isNetworkAvailable()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, LoadingFragment.newInstance());
            fragmentTransaction.commit();

//        Bundle bundle = getIntent().getExtras();
            Intent intent = getIntent();
            sNombreEncuesta = intent.getStringExtra("encuesta");
            encuesta = new Encuesta(this, sNombreEncuesta);
//        System.out.println("nombre encuesta: "+sNombreEncuesta);

            Log.d("Nombre encuesta", sNombreEncuesta);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

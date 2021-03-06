package mx.itesm.life_tqueremos;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class FragmentoRecomendaciones extends Fragment {

    TextView tvNombre;
    TextView tvRecomendaciones;
    String sNombreEncuesta;
    private PieChart mChart;
    int iResultado=30;

    public FragmentoRecomendaciones() {
        // Required empty public constructor
    }

    public static FragmentoRecomendaciones newInstance(String dim, int val) {
        FragmentoRecomendaciones fragment = new FragmentoRecomendaciones();
        Bundle args = new Bundle();
        args.putString("dim", dim);
        args.putInt("val", val);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_recomendaciones, container, false);
        tvNombre = (TextView)view.findViewById(R.id.Name);
        tvRecomendaciones = (TextView)view.findViewById(R.id.Recomendaciones);
        Bundle b = getArguments();
        iResultado = b.getInt("val");
        sNombreEncuesta = b.getString("dim");

        tvNombre.setText(sNombreEncuesta);

        mChart=(PieChart)view.findViewById(R.id.Piechart);
        //mChart = (PieChart) findViewById(R.id.Piechart);
        //findViewById(R.id.Piechart);
        mChart.setBackgroundColor(Color.WHITE);

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);

        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setMaxAngle(360f); // HALF CHART
        mChart.setRotationAngle(180f);
        mChart.setCenterTextOffset(0, -20);

        setData(3, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);

        switch (sNombreEncuesta) {
            case "Ocupacional":
                tvRecomendaciones.setText("Centro vida y carrera (CVC).\nParticipar en grupos estudiantiles.\nContacto directo con los directores de carrera.");
                break;

            case "Social":
                tvRecomendaciones.setText("Participar en alguna actividad co-curricular.\nAsistir a curso de comunicación efectiva.\nParticipar en Eventos Tec.");

                break;

            case "Emocional":
                tvRecomendaciones.setText("Actividades en Punto Blanco.\nen Punto Blanco.\nAcercarse a una creencia religiosa o una filosofía de vida.");
                break;

            case "Financiero":
                tvRecomendaciones.setText("Taller de finanzas para no financieros.\nEmpleos en el TEC (Campus JOBS).\nCurso de administración del tiempo.");
                break;

            case "Espiritual":
                tvRecomendaciones.setText("Talleres de desarrollo personal.\nConsejería individual.\nMindfulness.\nActividades de Punto Blanco.\nLínea de apoyo emocional.\n");
                break;

            case "Intelectual":
                tvRecomendaciones.setText("Cátedras académicas.\nMAE´s.\nPasión por la lectura.\nCentro de escritura.");
                break;

            case "Físico":
                tvRecomendaciones.setText("Participar en actividades deportivas (intramuros, clubs).\nParticipar en actividades de arte y cultura (clases, concursos).\nParticipar en campañas de vacunación y/o promoción de salud.");
                break;
        }

        return view;


    }

    private void setData(int count, float range) {

        ArrayList<PieEntry> values = new ArrayList<PieEntry>();

        if(iResultado<34 && iResultado>=0)
        {
            values.add(new PieEntry(0));
            values.add(new PieEntry(0));
            values.add(new PieEntry(iResultado));
            values.add(new PieEntry(100-iResultado));

        }
        else if(iResultado<67 && iResultado>=34)
        {
            values.add(new PieEntry(0));
            values.add(new PieEntry(iResultado));
            values.add(new PieEntry(0));
            values.add(new PieEntry(100-iResultado));
        }
        else
        {
            values.add(new PieEntry(iResultado));
            values.add(new PieEntry(0));
            values.add(new PieEntry(0));
            values.add(new PieEntry(100-iResultado));
        }



        PieDataSet dataSet = new PieDataSet(values, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.parseColor("#2ecc71"),
                Color.parseColor("#f1c40f"),
                Color.parseColor("#e74c3c"),
                Color.WHITE);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        // data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(1f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        mChart.getLegend().setEnabled(false);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        String sResultado=Integer.toString(iResultado);
        SpannableString s = new SpannableString("\n "+sResultado);
        s.setSpan(new RelativeSizeSpan(2.7f), 0, s.length(), 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        // s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        //s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

}
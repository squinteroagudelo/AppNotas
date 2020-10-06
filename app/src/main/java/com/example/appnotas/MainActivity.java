package com.example.appnotas;

/* Implementar una aplicación que permita calcular la nota definitiva de n estudiantes, sabiendo que
   la primera nota vale un 20%, la segunda un 30%, la tercera 15% y la cuarta un 35%, además el
   programa deberá indicar cuantos estudiantes perdieron la materia. Tenga en cuenta que la debe
   estar entre uno (1) y cinco(5). */


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText etnumberstudents, etgrade1, etgrade2, etgrade3, etgrade4;
    TextView tvfinalgrade, tvfinal;
    int numberstudents, student = 1, pass_s = 0, fail_s = 0;
    float finalgrade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Notas Definitivas");

        etnumberstudents = findViewById(R.id.etnumberstudents);
        etgrade1 = findViewById(R.id.etgrade1);
        etgrade2 = findViewById(R.id.etgrade2);
        etgrade3 = findViewById(R.id.etgrade3);
        etgrade4 = findViewById(R.id.etgrade4);
        tvfinalgrade = findViewById(R.id.tvfinalgrade);
        tvfinal = findViewById(R.id.tvfinal);

        etgrade1.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvfinal.setText("");
                return false;
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void CalcualarDef(View v) {
        if (!etnumberstudents.isEnabled()) {
            if (isValid(etgrade1) && isValid(etgrade2) && isValid(etgrade3) && isValid(etgrade4)) {
                float grade1 = Float.parseFloat(etgrade1.getText().toString());
                double grade2 = Float.parseFloat(etgrade2.getText().toString());
                double grade3 = Float.parseFloat(etgrade3.getText().toString());
                double grade4 = Float.parseFloat(etgrade4.getText().toString());
                finalgrade = (float) (grade1 * 0.2 + grade2 * 0.3 + grade3 * 0.15 + grade4 * 0.35);
                tvfinal.setText(String.format("%.1f", finalgrade));

                if (finalgrade >= 3.0)
                    pass_s++;
                else
                    fail_s++;

                if (student == numberstudents) {
                    setToast("Ha cargado todas las notas");
                    setToast("Ganan: " + pass_s);
                    setToast("Pierden: " + fail_s);
                    etgrade1.setEnabled(false);
                    etgrade2.setEnabled(false);
                    etgrade3.setEnabled(false);
                    etgrade4.setEnabled(false);
                    limpiar();
                    tvfinal.setText("");
                    etnumberstudents.setEnabled(true);
                    etnumberstudents.setText("");
                    etnumberstudents.requestFocus();

                    showDialogResult(pass_s, fail_s);

                } else {
                    student++;
                    limpiar();
                }

            } else {
                if (!isValid(etgrade1)) {
                    etgrade1.requestFocus();
                    etgrade1.setText("");
                } else if (!isValid(etgrade2)) {
                    etgrade2.requestFocus();
                    etgrade2.setText("");
                } else if (!isValid(etgrade3)) {
                    etgrade3.requestFocus();
                    etgrade3.setText("");
                } else {
                    etgrade4.requestFocus();
                    etgrade4.setText("");
                }
                setToast("Ingrese notas válidas");
            }
        } else {
            setToast("Ingrese la cantidad de estudiantes");
            etnumberstudents.requestFocus();
        }
    }

    public void validateNumber(View v) {
        numberstudents = 0;
        if (!etnumberstudents.getText().toString().isEmpty()) {
            numberstudents = Integer.parseInt(etnumberstudents.getText().toString());
            if (numberstudents > 0) {
                pass_s = 0;
                fail_s = 0;
                etgrade1.setEnabled(true);
                etgrade1.requestFocus();
                etgrade2.setEnabled(true);
                etgrade3.setEnabled(true);
                etgrade4.setEnabled(true);
                etgrade1.setText("");
                etgrade2.setText("");
                etgrade3.setText("");
                etgrade4.setText("");
                tvfinal.setText("");
                etnumberstudents.setEnabled(false);
            } else {
                setToast("Ingrese una cantidad válida");
                etnumberstudents.setText("");
            }
        } else {
            setToast("Ingrese la cantidad de estudiantes");
        }
    }

    public boolean isValid(EditText etgrade) {
        if (!etgrade.getText().toString().isEmpty()) {
            float grade = Float.parseFloat(etgrade.getText().toString());
            if (grade >= 0 && grade <= 5)
                return true;
        }
        return false;
    }

    public void showDialogResult(int pass_s, int fail_s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_result, null);

        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView tvpass = view.findViewById(R.id.tvpass);
        tvpass.setText(String.valueOf(pass_s));

        TextView tvfail = view.findViewById(R.id.tvfail);
        tvfail.setText(String.valueOf(fail_s));

        Button btnfinish = view.findViewById(R.id.btnfinish);
        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void limpiar() {
        etgrade1.setText("");
        etgrade2.setText("");
        etgrade3.setText("");
        etgrade4.setText("");
        etgrade1.requestFocus();
    }

    public void setToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 120);
        toast.show();
    }
}
package dam.pablo.u2p4conversor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    int conversion = 1;
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_RATING = "AppRating";
    private static final String KEY_HAS_RATED = "HasRated";
    private boolean hasRated;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Guardar el texto del campo de error y del campo de resultado
        TextView etResultado = findViewById(R.id.et_Resultado);
        TextView textError = findViewById(R.id.textError);

        outState.putString("resultado", etResultado.getText().toString());
        outState.putString("mensajeError", textError.getText().toString());
        outState.putInt("modoConversion", conversion); // Guardar el estado del modo de conversión
        Log.e("Almacena", "Datos, errores y conversiones almacenados");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Detecta la orientación del dispositivo
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_tumbado);
            Log.d("Orientation", "El dispositivo está en orientación horizontal (landscape)");
        } else {
            setContentView(R.layout.activity_main);
            Log.d("Orientation", "El dispositivo está en orientación vertical (portrait)");
        }

        // Cargar la valoración guardada
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        hasRated = preferences.getBoolean(KEY_HAS_RATED, false);

        // Si la instancia ya ha sido generada previamente
        if (savedInstanceState != null) {
            TextView etResultado = findViewById(R.id.et_Resultado);
            TextView textError = findViewById(R.id.textError);
            TextView textView = findViewById(R.id.textView);

            etResultado.setText(savedInstanceState.getString("resultado"));
            textError.setText(savedInstanceState.getString("mensajeError"));
            conversion = savedInstanceState.getInt("modoConversion");

            // Cambiar el texto del TextView según el modo restaurado
            switch (conversion) {
                case 1:
                    textView.setText(getString(R.string.textViewInfo));
                    break;
                case 2:
                    textView.setText(getString(R.string.textViewInfo2));
                    break;
                case 3:
                    textView.setText(getString(R.string.textViewInfo3));
                    break;
                case 4:
                    textView.setText(getString(R.string.textViewInfo4));
                    break;
                case 5:
                    textView.setText(getString(R.string.textViewInfo5));
                    break;
                case 6:
                    textView.setText(getString(R.string.textViewInfo6));
                    break;
            }
            Log.e("GuardadoEjecutado", "Se ha ejecutado la imagen guardada");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Para comprobar el metodo de valorar por si ya lo he valorado previamente dejar de comentar resetRating
        //resetRating();
        setUI();
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        EditText etPulgada = findViewById(R.id.et_Pulgada);
        TextView etResultado = findViewById(R.id.et_Resultado);
        TextView textView = findViewById(R.id.textView);
        Button buttonConvertir = findViewById(R.id.button_Convertir);
        TextView textError = findViewById(R.id.textError);
        Button buttonCambiar = findViewById(R.id.button_Cambio);
        Button buttonCambiar2 = findViewById(R.id.button_Cambio2);
        Button buttonCambiar3 = findViewById(R.id.button_Cambio3);
        ImageButton buttonInterrogante = findViewById(R.id.button_interrogante);
        Button buttonValorar = findViewById(R.id.button_valorar); // Agregado para la valoración

        // Muestra un mensaje de agradecimiento si ya ha valorado
        if (hasRated) {
            buttonValorar.setEnabled(false); // Deshabilitar el botón si ya ha valorado
            Toast.makeText(this, "Gracias por valorar la aplicación!", Toast.LENGTH_SHORT).show();
        }

        buttonConvertir.setOnClickListener(view -> {
            String pulgadaText = etPulgada.getText().toString();
            try {
                double pulgadaValue = Double.parseDouble(pulgadaText);
                if (pulgadaValue < 1) {
                    textError.setText("Error! Debe ser un número >= 1");
                    etResultado.setText(""); // Limpiar resultado en caso de error
                    Log.e("Error", "x<1"); // Muestra el error en el log
                } else {
                    textError.setText(""); // Elimina el texto de error
                    Log.e("Action", "Calcular número"); // En el log indica que ha calculado el número
                    etResultado.setText(convertir(pulgadaText));
                }
            } catch (NumberFormatException e) {
                textError.setText("Error! Debe introducir un número");
                etResultado.setText(""); // Limpiar resultado en caso de error
                Log.e("Error", "Debe introducir un número válido: " + e.getMessage()); // Muestra el error en el log
            }
        });

        // Cambiar entre pulgadas y centímetros
        buttonCambiar.setOnClickListener(view -> {
            if (conversion == 1) {
                conversion = 2; // Cambiar a modo Centímetros a Pulgadas
                textView.setText(getString(R.string.textViewInfo2)); // Cambiar texto del TextView a la descripción del modo 2
            } else if (conversion == 2) {
                conversion = 1; // Cambiar a modo Pulgadas a Centímetros
                textView.setText(getString(R.string.textViewInfo)); // Cambiar texto del TextView a la descripción del modo 1
            } else {
                conversion = 1;
                textView.setText(getString(R.string.textViewInfo)); // Cambiar texto del TextView a la descripción del modo 1
            }

            etResultado.setText(""); // Limpiar el resultado
            textError.setText(""); // Limpiar el texto de error
        });

        // Cambiar entre Kilogramos y Libras
        buttonCambiar2.setOnClickListener(view -> {
            if (conversion == 3) {
                conversion = 4;
                textView.setText(getString(R.string.textViewInfo4)); // Cambiar texto del TextView a la descripción del modo 4
            } else if (conversion == 4) {
                conversion = 3;
                textView.setText(getString(R.string.textViewInfo3)); // Cambiar texto del TextView a la descripción del modo 3
            } else {
                conversion = 3;
                textView.setText(getString(R.string.textViewInfo3)); // Cambiar texto del TextView a la descripción del modo 3
            }

            etResultado.setText("");
            textError.setText("");
        });

        // Cambiar entre Pies y metros
        buttonCambiar3.setOnClickListener(view -> {
            if (conversion == 5) {
                conversion = 6;
                textView.setText(getString(R.string.textViewInfo6)); // Cambiar texto del TextView a la descripción del modo 6
            } else if (conversion == 6) {
                conversion = 5;
                textView.setText(getString(R.string.textViewInfo5)); // Cambiar texto del TextView a la descripción del modo 5
            } else {
                conversion = 5;
                textView.setText(getString(R.string.textViewInfo5)); // Cambiar texto del TextView a la descripción del modo 5
            }

            etResultado.setText("");
            textError.setText("");
        });

        // Mostrar ayuda
        buttonInterrogante.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ayudaActivity.class);
            startActivity(intent); // Iniciar la nueva actividad
        });

        // Agregar la funcionalidad de valoración
        buttonValorar.setOnClickListener(view -> {
            if (!hasRated) {
                showRatingDialog(); // Mostrar diálogo de valoración
                buttonValorar.setVisibility(View.GONE); // Hacer desaparecer el botón después de pulsarlo
            } else {
                Toast.makeText(this, "Ya has valorado la aplicación!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRatingDialog() {
        // Crear un diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Valora la aplicación");

        // Configurar el RatingBar
        final RatingBar ratingBar = new RatingBar(this);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);
        ratingBar.setRating(0); // Valor inicial

        // Añadir el RatingBar al diálogo
        builder.setView(ratingBar);

        // Botón de enviar
        builder.setPositiveButton("Enviar", (dialog, which) -> {
            int rating = (int) ratingBar.getRating();
            if (rating > 0) {
                // Guardar la valoración en SharedPreferences
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(KEY_RATING, rating);
                editor.putBoolean(KEY_HAS_RATED, true);
                editor.apply();
                hasRated = true; // Actualizar la variable local

                Toast.makeText(MainActivity.this, "¡Gracias por valorar la aplicación con " + rating + " estrellas!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Debes seleccionar una valoración antes de enviar.", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón de cancelar
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Mostrar el diálogo
        builder.show();
    }

    private String convertir(String pulgadaText) {
        double pulgadaValue = 0;

        switch (conversion) {
            case 1:
                pulgadaValue = Double.parseDouble(pulgadaText) * 2.54;
                break;
            case 2:
                pulgadaValue = Double.parseDouble(pulgadaText) / 2.54;
                break;
            case 3:
                pulgadaValue = Double.parseDouble(pulgadaText) * 2.205;
                break;
            case 4:
                pulgadaValue = Double.parseDouble(pulgadaText) / 2.205;
                break;
            case 5:
                pulgadaValue = Double.parseDouble(pulgadaText) * 3.281;
                break;
            case 6:
                pulgadaValue = Double.parseDouble(pulgadaText) / 3.281;
                break;
        }

        return String.valueOf(pulgadaValue);
    }

    private void resetRating() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_RATING); // Eliminar la valoración
        editor.putBoolean(KEY_HAS_RATED, false); // Restablecer el estado de valoración
        editor.apply();

        hasRated = false; // Actualizar la variable local
        Button buttonValorar = findViewById(R.id.button_valorar);
        buttonValorar.setVisibility(View.VISIBLE); // Mostrar el botón de valoración nuevamente

        Toast.makeText(this, "El estado de valoración ha sido reiniciado. Puedes valorar la aplicación nuevamente.", Toast.LENGTH_SHORT).show();
    }

}

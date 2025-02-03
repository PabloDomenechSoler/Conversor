package dam.pablo.u2p4conversor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ayudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ayuda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setUI();
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        ImageButton buttonAyuda = findViewById(R.id.button_ayuda);

        // Configura el OnClickListener
        buttonAyuda.setOnClickListener(view -> {
            // Crear un Intent para volver a MainActivity
            Intent intent = new Intent(ayudaActivity.this, MainActivity.class);
            startActivity(intent); // Iniciar la actividad MainActivity
            finish(); // Cierra AyudaActivity si no necesitas volver a ella
        });
    }
}
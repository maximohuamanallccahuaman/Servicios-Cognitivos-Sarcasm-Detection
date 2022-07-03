package controlador;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import modelo.SarcasmDetection;
import servicios.Sarcasmo;

@Named(value = "sarcasmoC")
@SessionScoped
public class SarcarsmoC implements Serializable {

    private SarcasmDetection modelo;
    private Sarcasmo sarcas;

    public SarcarsmoC() {
        modelo = new SarcasmDetection();
        sarcas = new Sarcasmo();
    }

    public void generarSarcasmo() {
        try {
            Sarcasmo.metodo(modelo);
        } catch (Exception e) {
            System.out.println("Error en generar SarcasmoC: " + e.getMessage());

        }

    }

    public SarcasmDetection getModelo() {
        return modelo;
    }

    public void setModelo(SarcasmDetection modelo) {
        this.modelo = modelo;
    }

    public Sarcasmo getSarcas() {
        return sarcas;
    }

    public void setSarcas(Sarcasmo sarcas) {
        this.sarcas = sarcas;
    }

}

package com.carpool.android.negocio;

import com.carpool.android.dominio.Carona;
import com.carpool.android.dominio.Itinerario;

public class CaronaOferecerNegocio {

    private static Itinerario itinerarioOferecido;

    public void validarItinerario(Itinerario itinerario){
        if (true){
            this.itinerarioOferecido = itinerario;
        }
    }

    public Itinerario getItinerario(){
        return this.itinerarioOferecido;
    }

    public void oferecerCarona(Carona carona){
        this.validarCarona(carona);
        // chama persistencia para cadastrar no banco e trata erros retornados, se não tiver nenhum, finaliza, confirmando a carona
    }

    public boolean validarCarona(Carona carona){
        return true;
    }

    public void aceitarCaroneiro(){

    }

}

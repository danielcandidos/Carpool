package com.carpool.android.negocio;

import com.carpool.android.dominio.Carona;
import com.carpool.android.dominio.Itinerario;
import com.carpool.android.service.CaronaService;

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

    public void oferecerCarona(Carona carona) throws Exception {
        try {
            String retornoValidacao = this.validarCarona(carona);
            if(retornoValidacao.equals("")){
                CaronaService.oferecerCarona(carona);
            } else {
                throw new Exception(retornoValidacao);
            }
        } catch (Exception excecao){
            throw new Exception(excecao);
        }
        // chama persistencia para cadastrar no banco e trata erros retornados, se não tiver nenhum, finaliza, confirmando a carona
    }

    /**
     * Validacao dos campos da carona
     *
     * @param carona
     * @return
     */
    public String validarCarona(Carona carona){
        StringBuilder builder = new StringBuilder();
        builder.append("");

        if(carona.getNomeCarona().equals("")){
            builder.append(" nomeCarona;");
        } else if(carona.getHorarioSaida().equals("hh:mm")){
            builder.append(" horarioSaida;");
        } else if(carona.getCarro().equals(null)){
            builder.append(" carro;");
        }

        return builder.toString();
    }

    public void aceitarCaroneiro(){

    }

}

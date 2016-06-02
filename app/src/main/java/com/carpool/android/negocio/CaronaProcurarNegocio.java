package com.carpool.android.negocio;

import com.carpool.android.dominio.Carona;
import com.carpool.android.dominio.FiltroCarona;
import com.carpool.android.service.CaronaService;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CaronaProcurarNegocio {

    //private static ArrayList<LatLng> listaDePontos = new ArrayList<>();
    private static Carona caronaPedida;

    public static Carona getCaronaPedida() {
        return caronaPedida;
    }

    public static void setCaronaPedida(Carona caronaPedidaNova) {
        caronaPedida = caronaPedidaNova;
    }

    public ArrayList<Carona> procurarCaronas(FiltroCarona filtroCarona) throws Exception {

        // lista temporaria de todas as caronas que retornarão da consulta
        ArrayList<Carona> listaCaronasTemp = new ArrayList<>();

        try {
            String retornoValidacao = validarFiltroCarona(filtroCarona);
            if(retornoValidacao.equals("")){
                listaCaronasTemp = CaronaService.procurarCaronas(filtroCarona);
            } else {
                throw new Exception(retornoValidacao);
            }
        } catch (Exception excecao){
            throw new Exception(excecao);
        }

        // lista de caronas para o retorno após o filtro de horário
        ArrayList<Carona> listaCaronasRetorno = new ArrayList<>();

        for (Carona carona : listaCaronasTemp) {
            if (this.verificarIntervalo(
                    filtroCarona.getHorarioInicio(),
                    filtroCarona.getHorarioFim(),
                    carona.getHorarioSaida())) {

                listaCaronasRetorno.add(carona);
            }
        }

        return listaCaronasRetorno;
    }

    /**
     * Verifica se as caronas retornadas estão dentro do intervalo de tempo pesquisado
     *
     * @param hInicio
     * @param hFim
     * @param hPartida
     * @return
     */
    private boolean verificarIntervalo (String hInicio, String hFim, String hPartida){
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        Date horarioInicio = null;
        Date horarioFim = null;
        Date horarioPartida = null;
        try {
            horarioInicio = formatador.parse(hInicio);
            horarioFim = formatador.parse(hFim);
            horarioPartida = formatador.parse(hPartida);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((horarioPartida.getTime() > horarioInicio.getTime()) && (horarioPartida.getTime() < horarioFim.getTime()) ){
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Verifica se todos os campos estão preenchidos
     *
     * @param filtroCarona
     * @return
     * @throws Exception
     */
    public String validarFiltroCarona(FiltroCarona filtroCarona) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("");

        if(filtroCarona.getHorarioInicio().equals("")){
            builder.append(" horario inicio;");
        } else if(filtroCarona.getHorarioFim().equals("")) {
            builder.append(" horario fim;");
        }

        return builder.toString();
    }

    public void pedirCarona(Carona caronaPedida) throws Exception {
        try {
            CaronaService.pedirCarona(caronaPedida);
        } catch (Exception excecao){
            throw new Exception(excecao);
        }
    }

    /*public ArrayList<LatLng> procurarCarona(LatLng localizacaoAtual, double raio){

        ArrayList<LatLng> pontosNoRaio = new ArrayList<LatLng>();

        for (LatLng pontoReferenciaMotorista : listaDePontos) {
            if ((6371
                    * Math.acos(
                    Math.cos(Math.toRadians(localizacaoAtual.latitude)) *
                            Math.cos(Math.toRadians(pontoReferenciaMotorista.latitude)) *
                            Math.cos(Math.toRadians(localizacaoAtual.longitude) - Math.toRadians(pontoReferenciaMotorista.longitude)) +
                            Math.sin(Math.toRadians(localizacaoAtual.latitude)) *
                                    Math.sin(Math.toRadians(pontoReferenciaMotorista.latitude)))
            ) <= (raio / 1000)) {

                pontosNoRaio.add(pontoReferenciaMotorista);

            }
        }
        return pontosNoRaio;
    }*/

}

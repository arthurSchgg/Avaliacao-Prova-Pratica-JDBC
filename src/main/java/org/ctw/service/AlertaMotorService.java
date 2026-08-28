package org.ctw.service;

import org.ctw.dao.AlertaMotorDAO;
import org.ctw.exception.EntidadeNaoEncontradaException;
import org.ctw.model.AlertaMotor;

import java.util.List;

public class AlertaMotorService {

    private static final List<String> CRITICIDADES_VALIDAS = List.of("Baixa", "Média", "Alta", "Crítica");

    private final AlertaMotorDAO alertaDAO;
    private final MotorService motorService;

    public AlertaMotorService(AlertaMotorDAO alertaDAO, MotorService motorService) {
        this.alertaDAO = alertaDAO;
        this.motorService = motorService;
    }

    public AlertaMotor cadastrar(AlertaMotor alerta) {
        validarAlerta(alerta);

        alerta.setId(null);

        return alertaDAO.inserir(alerta);
    }

    public List<AlertaMotor> listarTodos() {

        return alertaDAO.listarTodos();
    }

    public List<AlertaMotor> listarNaoResolvidos() {

        return alertaDAO.listarNaoResolvidos();
    }

    public List<AlertaMotor> buscarPorCriticidade(String criticidade) {
        if(criticidade == null || criticidade.isBlank()){
            throw new IllegalArgumentException("Insira uma criticidade para continuar!");
        }
        validarCriticidade(criticidade);

        return alertaDAO.buscarPorCriticidade(criticidade);
    }

    public void marcarComoResolvido(Integer alertaId) {
        validarId(alertaId);

        alertaDAO.marcarComoResolvido(alertaId);
    }

    private void validarAlerta(AlertaMotor alerta) {
        if(alerta == null){
            throw new IllegalArgumentException("Os dados do alerta são obrigatórios!");
        }

        if(alerta.getMotor_id() == null || alerta.getMotor_id() <= 0) {
            throw new IllegalArgumentException("O motor é obrigatório");
        }

        if(alerta.getTipo_anomalia() == null || alerta.getTipo_anomalia().isBlank()){
            throw new IllegalArgumentException("O tipo da anomalia é obrigatório!");
        }

        if(alerta.getCriticidade() == null || alerta.getCriticidade().isBlank()){
            throw new IllegalArgumentException("A criticidade é obrigatório!");
        }

        if(alerta.getDescricao() == null || alerta.getDescricao().isBlank()){
            throw new IllegalArgumentException("A descrição é obrigatória!");
        }
    }

    private void validarCriticidade(String criticidade) {
        if(!CRITICIDADES_VALIDAS.contains(criticidade)){
            throw new IllegalArgumentException("Criticidade inválida. Valores permitidos: " + CRITICIDADES_VALIDAS);
        }
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID deve ser positivo.");
        }
    }
}

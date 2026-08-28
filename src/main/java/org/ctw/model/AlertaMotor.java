package org.ctw.model;

import java.time.LocalDate;

public class AlertaMotor {

    private Integer id;
    private Integer motor_id;
    private LocalDate data_alerta;
    private String tipo_anomalia;
    private String criticidade;
    private String descricao;
    private boolean resolvido;

    public AlertaMotor() {
    }

    public AlertaMotor(Integer id, Integer motor_id, LocalDate data_alerta, String tipo_anomalia, String criticidade, String descricao, boolean resolvido) {
        this.id = id;
        this.motor_id = motor_id;
        this.data_alerta = data_alerta;
        this.tipo_anomalia = tipo_anomalia;
        this.criticidade = criticidade;
        this.descricao = descricao;
        this.resolvido = resolvido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMotor_id() {
        return motor_id;
    }

    public void setMotor_id(Integer motor_id) {
        this.motor_id = motor_id;
    }

    public LocalDate getData_alerta() {
        return data_alerta;
    }

    public void setData_alerta(LocalDate data_alerta) {
        this.data_alerta = data_alerta;
    }

    public String getTipo_anomalia() {
        return tipo_anomalia;
    }

    public void setTipo_anomalia(String tipo_anomalia) {
        this.tipo_anomalia = tipo_anomalia;
    }

    public String getCriticidade() {
        return criticidade;
    }

    public void setCriticidade(String criticidade) {
        this.criticidade = criticidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isResolvido() {
        return resolvido;
    }

    public void setResolvido(boolean resolvido) {
        this.resolvido = resolvido;
    }

    @Override
    public String toString() {
        return "AlertaMotor{" +
                "id=" + id +
                ", motor_id=" + motor_id +
                ", data_alerta=" + data_alerta +
                ", tipo_anomalia='" + tipo_anomalia + '\'' +
                ", criticidade='" + criticidade + '\'' +
                ", descricao='" + descricao + '\'' +
                ", resolvido=" + resolvido +
                '}';
    }
}

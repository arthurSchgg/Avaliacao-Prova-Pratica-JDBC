package org.ctw.dao;

import org.ctw.config.ConnectionFactory;
import org.ctw.exception.DatabaseException;
import org.ctw.exception.EntidadeNaoEncontradaException;
import org.ctw.model.AlertaMotor;
import org.ctw.model.Motor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlertaMotorDAO {

    public AlertaMotor inserir(AlertaMotor alerta) {
        String sql = """
                INSERT INTO alertas_motores (
                    motor_id,
                    data_alerta,
                    tipo_anomalia,
                    criticidade,
                    descricao,
                    resolvido
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preencherParametros(stmt, alerta);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new DatabaseException("Nenhum alerta foi criado", null);
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    alerta.setId(keys.getInt(1));
                }
            }



        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir o alerta!", e);
        }
        return alerta;
    }

    public List<AlertaMotor> listarTodos() {
        String sql = """
                SELECT id,
                       motor_id,
                       data_alerta,
                       tipo_anomalia,
                       criticidade,
                       descricao,
                       resolvido
                FROM alertas_motores
                ORDER BY data_alerta
                """;

        List<AlertaMotor> listaAlertas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaAlertas.add(mapearAlerta(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao tentar listar os alertas!", e);
        }

        return listaAlertas;
    }

    public Optional<AlertaMotor> buscarPorId(Integer id) {
        String sql = """
                SELECT id,
                       motor_id,
                       data_alerta,
                       tipo_anomalia,
                       criticidade,
                       descricao,
                       resolvido
                FROM alertas_motores
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearAlerta(rs));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar o id do alerta!", e);
        }
    }

    public List<AlertaMotor> listarNaoResolvidos() {
        String sql = """
                SELECT id,
                       motor_id,
                       data_alerta,
                       tipo_anomalia,
                       criticidade,
                       descricao,
                       resolvido
                FROM alertas_motores
                WHERE resolvido = 0
                """;

        List<AlertaMotor> alertasNaoResolvidos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                alertasNaoResolvidos.add(mapearAlerta(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao achar os alertas não resolvidos!", e);
        }
        return alertasNaoResolvidos;
    }

    public List<AlertaMotor> buscarPorCriticidade(String criticidade) {
        String sql = """
                SELECT id,
                       motor_id,
                       data_alerta,
                       tipo_anomalia,
                       criticidade,
                       descricao,
                       resolvido
                FROM alertas_motores
                WHERE criticidade = ?
                """;

        List<AlertaMotor> listaCriticidade = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, criticidade);

            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    listaCriticidade.add(mapearAlerta(rs));
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCriticidade;
    }

    public boolean marcarComoResolvido(Integer id) {

        String sql = """
                UPDATE alertas_motores
                SET resolvido = 1
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao marcar como resolvido!", e);
        }
    }

    public boolean excluir(Integer id) {
        String sql = """
                DELETE FROM alertas_motores
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar o alerta!", e);
        }
    }

    private AlertaMotor mapearAlerta(ResultSet resultSet) throws SQLException {

        AlertaMotor alertaMotor = new AlertaMotor();

        alertaMotor.setId(resultSet.getInt("id"));

        int motor_id = resultSet.getInt("motor_id");

        if (!resultSet.wasNull()) {
            alertaMotor.setMotor_id(motor_id);
        }

        Date data = resultSet.getDate("data_alerta");

        if (data != null) {
            alertaMotor.setData_alerta(data.toLocalDate());
        }

        alertaMotor.setTipo_anomalia(resultSet.getString("tipo_anomalia"));
        alertaMotor.setCriticidade(resultSet.getString("criticidade"));
        alertaMotor.setDescricao(resultSet.getString("descricao"));

        boolean resolvido = resultSet.getBoolean("resolvido");

        if (resolvido) {
            alertaMotor.setResolvido(true);
        }

        return alertaMotor;
    }

    private void preencherParametros(PreparedStatement stmt, AlertaMotor alertaMotor) throws SQLException {

        if (alertaMotor.getMotor_id() == null) {
            stmt.setNull(1, Types.INTEGER);
        } else {
            stmt.setObject(1, alertaMotor.getMotor_id());
        }

        if (alertaMotor.getData_alerta() == null) {
            stmt.setNull(2, Types.DATE);
        } else {
            stmt.setDate(2, Date.valueOf(alertaMotor.getData_alerta()));
        }

        stmt.setString(3, alertaMotor.getTipo_anomalia());
        stmt.setString(4, alertaMotor.getCriticidade());
        stmt.setString(5, alertaMotor.getDescricao());
        stmt.setBoolean(6, alertaMotor.isResolvido());
    }

}

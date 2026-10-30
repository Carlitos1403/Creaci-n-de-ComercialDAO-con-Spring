package org.iesvdm.comercial.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.comercial.modelo.Comercial;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ComercialDAOImpl implements ComercialDAO {
	private final JdbcTemplate jdbcTemplate;

	public ComercialDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Comercial comercial) {
		String sql = "INSERT INTO comercial (nombre, apellido1, apellido2, `comisión`) VALUES (?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, new String[] { "id" });
			statement.setString(1, comercial.getNombre());
			statement.setString(2, comercial.getApellido1());
			statement.setString(3, comercial.getApellido2());
			statement.setObject(4, comercial.getComision());
			return statement;
		}, keyHolder);
		if (keyHolder.getKey() != null) {
			comercial.setId(keyHolder.getKey().intValue());
		}
	}

	@Override
	public List<Comercial> getAll() {
		return jdbcTemplate.query("SELECT id, nombre, apellido1, apellido2, `comisión` FROM comercial ORDER BY id",
				(rs, rowNum) -> new Comercial(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"),
						rs.getString("apellido2"), rs.getObject("comisión", Float.class)));
	}

	@Override
	public Optional<Comercial> find(int id) {
		List<Comercial> result = jdbcTemplate.query(
				"SELECT id, nombre, apellido1, apellido2, `comisión` FROM comercial WHERE id = ?",
				(rs, rowNum) -> new Comercial(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"),
						rs.getString("apellido2"), rs.getObject("comisión", Float.class)), id);
		return result.stream().findFirst();
	}

	@Override
	public void update(Comercial comercial) {
		jdbcTemplate.update("UPDATE comercial SET nombre = ?, apellido1 = ?, apellido2 = ?, `comisión` = ? WHERE id = ?",
				comercial.getNombre(), comercial.getApellido1(), comercial.getApellido2(), comercial.getComision(),
				comercial.getId());
	}

	@Override
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);
	}
}
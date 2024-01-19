package sql;

import entidade.Vencedor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class Query {

    public Vencedor consultaVencedor(long deslocamentoDeDias) {

        if (deslocamentoDeDias < 1) {
            throw new IllegalArgumentException("É necessário que haja pelo menos 1 dia de deslocamento.");
        }

        LocalDate diaInicial = LocalDate.now().minusDays(deslocamentoDeDias);
        LocalDate diaFinal = LocalDate.now().minusDays(1);

        String databaseUrl = System.getenv("DATABASE_URL");
        String databaseUser = System.getenv("DATABASE_USER");
        String databasePassword = System.getenv("DATABASE_PASSWORD");

        Vencedor vencedor = null;
        try (Connection connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword)) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM lance WHERE hora_do_lance BETWEEN '" + diaInicial + " 00:00:00' AND '" + diaFinal + " 23:59:29'\n" +
                    "    AND valor NOT IN\n" +
                    "            (SELECT valor FROM lance WHERE hora_do_lance between '" + diaInicial + " 00:00:00' AND '" + diaFinal + " 23:59:59'\n" +
                    "                    GROUP BY valor HAVING COUNT(valor) > 1)\n" +
                    "    ORDER BY valor\n" +
                    "    LIMIT 1";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                double valor = resultSet.getDouble("valor");

                vencedor = new Vencedor(email, valor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vencedor;
    }
}
package de.demmer.dennis.shorturl.model;

import de.demmer.dennis.shorturl.repository.PruneLinkRepository;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public class ShortUrlGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        PruneLink toGenerate = (PruneLink) object;
        if (toGenerate.getId() != null && !toGenerate.getId().isEmpty()) {
            log.info("Alias was given by user: " + toGenerate.getId());
            return toGenerate.getId();
        }
        Connection connection = session.connection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(id) as Id from prune_link");

            resultSet.next();
            int index = resultSet.getInt(1);
            while (true) {
                log.info("Index: " + index);
                String id = generateIdFromIndex(index);
                log.info("Gen id: " + id);
                if (!connection.createStatement().executeQuery("select * from prune_link where Id='" + id + "'").next()) {
                    return id;
                }
                log.info("Id already in use");
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String generateIdFromIndex(int index) {
        return Integer.toString(index, 36);
    }


}
package com.markyang.framework.auth.social.core;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户链接
 * @author yangchangliang
 */
public class JdbcUsersConnectionRepository implements UsersConnectionRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private ConnectionSignUp connectionSignUp;

    private String tablePrefix = "";

    public JdbcUsersConnectionRepository(DataSource dataSource, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    /**
     * The command to execute to create a new local user profile in the event no user id could be mapped to a connection.
     * Allows for implicitly creating a user profile from connection data during a provider sign-in attempt.
     * Defaults to null, indicating explicit sign-up will be required to complete the provider sign-in attempt.
     * @param connectionSignUp a {@link ConnectionSignUp} object
     * @see #findUserIdsWithConnection(Connection)
     */
    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

    /**
     * Sets a table name prefix. This will be prefixed to all the table names before queries are executed. Defaults to "".
     * This is can be used to qualify the table name with a schema or to distinguish Spring Social tables from other application tables.
     * @param tablePrefix the tablePrefix to set
     */
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        List<String> localUserIds = jdbcTemplate.queryForList("select `user_id` from `" + tablePrefix + "social_user_connection` where `provider_id` = ? and `provider_user_id` = ?", String.class, key.getProviderId(), key.getProviderUserId());
        if (localUserIds.size() == 0 && connectionSignUp != null) {
            String newUserId = connectionSignUp.execute(connection);
            if (newUserId != null)
            {
                createConnectionRepository(newUserId).addConnection(connection);
                return Collections.singletonList(newUserId);
            }
        }
        return localUserIds;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("providerId", providerId);
        parameters.addValue("providerUserIds", providerUserIds);
        final Set<String> localUserIds = new HashSet<String>();
        return new NamedParameterJdbcTemplate(jdbcTemplate).query("select `user_id` from `" + tablePrefix + "social_user_connection` where `provider_id` = :providerId and `provider_user_id` in (:providerUserIds)", parameters,
            rs -> {
                while (rs.next()) {
                    localUserIds.add(rs.getString("user_id"));
                }
                return localUserIds;
            });
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new JdbcConnectionRepository(userId, jdbcTemplate, connectionFactoryLocator, textEncryptor, tablePrefix);
    }
}
